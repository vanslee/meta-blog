// const roles = {
//   admin: ['dashboard', 'settings'],
//   user: ['home', 'profile']
// }
export const isLogin = () => {
  if (getToken()) {
    return true
  } else {
    return false
  }
}
export const getToken = () => {
  const temp = JSON.parse(localStorage.getItem('user'))
  if (temp) {
    return temp.token
  }
}
