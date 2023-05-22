import request from '@/apis/index'
export function loginApi(params) {
  return request.post('user/login', params)
}
export function userInfoApi(userId) {
  return request.get(`user/info/${userId}`)
}
