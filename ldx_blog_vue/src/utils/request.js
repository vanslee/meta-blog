import axios from 'axios'
import { close, start } from '@/utils/nprogress'
import { MessageBox, Message } from 'element-ui'
import { throttle } from 'lodash'
// import { useUserStore } from '@/stores/user'
import { getToken } from '@/utils/auth'
import { removeToken } from '@/utils/auth'
// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 18000000 // request timeout
})

// request interceptor
service.interceptors.request.use(
  config => {
    start()
    config.headers['litubao_authentication'] = getToken()
    console.log('token', getToken())
    return config
  },
  error => {
    // do something with request error
    console.log(error) // for debug
    return { code: 500, data: {} }
    // return Promise.reject(error)
  }
)
const tipError = res => {
  // console.log(res)
  Message({
    message: res.msg || '未知错误',
    type: 'error',
    duration: 5 * 1000
  })
}
const throttled = throttle(tipError, 1000, {
  leading: true, // 在开始时立即执行函数
  trailing: false // 在结束时不执行函数
})
// response interceptor
service.interceptors.response.use(
  response => {
    close()
    const res = response.data
    if (res.code !== 200) {
      throttled(res)
      removeToken()
      // 50008: Illegal token; 50012: Other clients logged in; 50014: Token expired;
      if (res.code === 50008 || res.code === 50012 || res.code === 50014) {
        // to re-login
        MessageBox.confirm(
          'You have been logged out, you can cancel to stay on this page, or log in again',
          'Confirm logout',
          {
            confirmButtonText: 'Re-Login',
            cancelButtonText: 'Cancel',
            type: 'warning'
          }
        ).then(() => {
          //清空用户信息和缓存并刷新页面
          location.reload()
        })
      }
      // return Promise.reject(new Error(res.msg || '未知错误'))
    }
    return res
    // }
  },
  error => {
    // Message({
    //   message: error.msg,
    //   type: 'error',
    //   duration: 5 * 1000
    // })
    removeToken()
    console.log(error)
    // return { code: 500, data: {} }
    return Promise.reject()
  }
)

export default service
