import request from '@/utils/request'
export function loginApi(params) {
  return request.post('user/login', params)
}
export function userInfoApi() {
  console.log('asdas')
  return request.get('user/info')
}
export function logoutApi() {
  return request.get(`user/logout`)
}
