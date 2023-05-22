import axios from 'axios'
import { Message } from 'element-ui'
import { close, start } from '@/utils/nprogress'
import { getStorage, setStorage } from '@/utils/storage'
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API,
  timeout: 1800000
})

/* 请求拦截器 */
service.interceptors.request.use(
  config => {
    start()
    let token = getStorage('LITUBAO_AUTHENTICATION')
    console.log('token', token)
    if (token) {
      const { tokenName, tokenValue } = token
      config.headers = { [tokenName]: tokenValue }
    }
    console.log('config', config.headers)
    return config
  },
  error => {
    Message.error(error.msg)
    return Promise.reject(error)
  }
)

/* 响应拦截器 */
service.interceptors.response.use(
  response => {
    close()
    const result = response.data
    //响应正常
    if (result.code >= 500) {
      //处理业务错误。
      Message.error(result.msg)
    }
    return result
  },
  error => {
    // 处理 HTTP 网络错误
    let message = ''
    // HTTP 状态码
    const status = error.response?.status
    switch (status) {
      case 403:
        message = '拒绝访问'
        break
      case 404:
        message = '请求地址错误'
        break
      case 500:
        message = '服务器故障'
        break
      default:
        message = '网络连接故障'
    }
    Message.error(message)
    setStorage('LITUBAO_AUTHENTICATION', {})
    location.replace('/')
    return Promise.resolve(error)
  }
)

/* 导出封装的请求方法 */
const request = {
  get(url, config) {
    return service.get(url, config)
  },

  post(url, data, config) {
    return service.post(url, data, config)
  },

  put(url, data, config) {
    return service.put(url, data, config)
  },

  delete(url, config) {
    return service.delete(url, config)
  }
}
export default request
