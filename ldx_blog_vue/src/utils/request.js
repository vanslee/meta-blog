import axios from 'axios'
import { throttle } from 'lodash'
import { close, start } from '@/utils/nprogress'
import { Message } from 'element-ui'
import { getToken } from '@/utils/auth'
const http = {}
const service = axios.create({
  baseURL: '/apis',
  timeout: 18000000
})
service.interceptors.request.use(
  config => {
    start()
    if (getToken()) {
      // 请求添加Token
      config.headers['litubao_authentication'] = getToken()
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

service.interceptors.response.use(
  response => {
    close()
    return response.data
  },
  error => {
    const msg = error?.response?.data?.msg ?? '未知错误'
    throttled(msg)
    return Promise.reject(error)
  }
)
http.get = function (url, options) {
  return new Promise((resolve, reject) => {
    service
      .get(url, options)
      .then(response => {
        if (response.code === 200) {
          resolve(response)
        } else {
          throttled(response.message)
          reject(response.message)
        }
      })
      .catch(e => {
        console.log(e)
      })
  })
}
http.post = function (url, data, options) {
  return new Promise((resolve, reject) => {
    service
      .post(url, data, options)
      .then(response => {
        if (response.code === 200) {
          resolve(response)
        } else {
          throttled(response.message)
          reject(response.message)
        }
      })
      .catch(e => {
        console.log(e)
      })
  })
}
http.put = function (url, data, options) {
  return new Promise((resolve, reject) => {
    service
      .put(url, data, options)
      .then(response => {
        if (response.code === 200) {
          resolve(response)
        } else {
          throttled(response.message)
          reject(response.message)
        }
      })
      .catch(e => {
        console.log(e)
      })
  })
}
const tipError = message => {
  // console.log(res)
  Message.error({
    message: message,
    duration: 5 * 1000
  })
}
const throttled = throttle(tipError, 1000, {
  leading: true, // 在开始时立即执行函数
  trailing: false // 在结束时不执行函数
})
export default http
