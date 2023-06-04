import { defineStore } from 'pinia'
import { loginApi, userInfoApi, logoutApi } from '@/apis/user'
import { setToken, removeToken, setUserInfo } from '@/utils/auth'
import { resetRouter } from '@/router'
// useStore 可以是 useUser、useCart 之类的任何东西
// 第一个参数是应用程序中 store 的唯一 id
export const useUserStore = defineStore('user', {
  // other options...
  state: () => ({
    local: {}
  }),
  getters: {},
  actions: {
    async getInfo() {
      const { data, code } = await userInfoApi()
      if (code === 200) {
        data['avatarImgUrl'] = process.env.VUE_APP_WEBSITE_CDN + data['avatarImgUrl']
        setUserInfo(data)
      }
    },
    async login(params) {
      const { username, password } = params
      const { data, code } = await loginApi({ username: username.trim(), password: password })
      console.log(data, code)
      if (code === 200) {
        setToken(data['tokenValue'])
        return { code: 200 }
      } else {
        return { code: 500 }
      }
    },
    async logout() {
      const { code } = await logoutApi()
      if (code === 200) {
        this.local = {}
        removeToken()
        resetRouter()
      }
    }

    // dynamically modify permissions
    // async changeRoles(role, userId) {
    //   const token = role + '-token'
    //   setToken(token)
    //   const { roles } = await this.getInfo(userId)

    //   resetRouter()

    //   // generate accessible routes map based on roles
    //   const accessRoutes = await dispatch('permission/generateRoutes', roles, { root: true })
    //   // dynamically add accessible routes
    //   router.addRoutes(accessRoutes)

    //   // reset visited views and cached views
    //   dispatch('tagsView/delAllViews', null, { root: true })
    // }
  }
})
