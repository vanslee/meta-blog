import request from '@/utils/request'
export function loginApi (params) {
  return request.post('user/login', params)
}
export function userInfoApi () {
  return request.get('user/info')
}
export function logoutApi () {
  return request.post('user/logout')
}
export function registryApi (params) {
  return request.post('user/registry', params)
}
