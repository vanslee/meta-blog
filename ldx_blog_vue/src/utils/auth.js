// const roles = {
//   admin: ['dashboard', 'settings'],
//   user: ['home', 'profile']
// }

export const hasPermission = (role, routeName) => {
  console.log('role', role)
  console.log('role', routeName)
  return true
  // return roles[role].includes(routeName)
}
