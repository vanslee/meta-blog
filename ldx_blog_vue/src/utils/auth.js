// const roles = {
//   admin: ['dashboard', 'settings'],
//   user: ['home', 'profile']
// }

export const setStorage = (key, value) => {
  if (!value) value = {}
  localStorage.setItem(key, JSON.stringify(value))
}
export const getStorage = key => {
  return localStorage.getItem(key) ? JSON.parse(localStorage.getItem(key)) : {}
}

export const setToken = value => {
  localStorage.setItem('LDX_TOKEN', JSON.stringify(value))
}
export const getToken = () => {
  return JSON.parse(localStorage.getItem('LDX_TOKEN'))
}
export const removeToken = () => {
  localStorage.removeItem('LDX_TOKEN')
}
export const isLogin = () => {
  return getStorage('LITUBAO_user')['isLogin']
}
export const getUserId = () => {
  return getStorage('LITUBAO_user')['isLogin']
}
