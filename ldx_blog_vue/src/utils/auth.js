// const roles = {
//   admin: ['dashboard', 'settings'],
//   user: ['home', 'profile']
// }

export const setStorage = (key, value) => {
  if (!value) value = ''
  localStorage.setItem(key, JSON.stringify(value))
}
export const getStorage = key => {
  return localStorage.getItem(key) ? JSON.parse(localStorage.getItem(key)) : ''
}
export const removeStorage = key => {
  return localStorage.removeItem(key)
}
export const setToken = value => {
  localStorage.setItem('LDX_TOKEN', value)
}
export const getToken = () => {
  return localStorage.getItem('LDX_TOKEN') || ''
}
export const removeToken = () => {
  localStorage.removeItem('LDX_TOKEN')
}
export const isLogin = () => {
  const token = getToken()
  return typeof token === 'string' && token.length > 0
}
export const getUserId = () => {
  return getStorage('LITUBAO_user')['isLogin']
}
export const getUserInfo = () => {
  return JSON.parse(localStorage.getItem('USER_INFO')) || {}
}
export const setUserInfo = value => {
  return localStorage.setItem('USER_INFO', JSON.stringify(value))
}
