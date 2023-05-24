import { defineStore } from 'pinia'
import { loginApi, userInfoApi, logoutApi } from '@/apis/user'
import { setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'
// useStore 可以是 useUser、useCart 之类的任何东西
// 第一个参数是应用程序中 store 的唯一 id
export const useUserStore = defineStore('user', {
  // other options...
  state: () => ({
    local: {
      isLogin: false,
      roles: [],
      name: '',
      avatar: '',
      introduction: ''
    }
  }),
  getters: {},
  actions: {
    async getInfo() {
      const { data, code } = await userInfoApi()
      if (code === 200) {
        console.log('data', data)
        this.local.roles = data['roles'] || []
        this.local.name = data['username'] || ''
        this.local.avatar =
          process.env.VUE_APP_WEBSITE_CDN + data['avatarImgUrl'] ||
          'https://th.bing.com/th/id/R.0dee2228031e4ef5b03d0c5734aef866?rik=BD%2bnjbFbllVmEQ&riu=http%3a%2f%2fimg.zcool.cn%2fcommunity%2f01cf02554336f10000019ae9df1dad.jpg%403000w_1l_2o_100sh.jpg&ehk=zvcYgjHlqK2U2x9ploUbmiBIk7BewUd6lyA0AIswegQ%3d&risl=&pid=ImgRaw&r=0'
        this.local.introduction = data['personalBrief']
        console.log('info', this.local)
      }
    },
    async login(params) {
      const { username, password } = params
      const { data, code } = await loginApi({ username: username.trim(), password: password })
      if (code === 200) {
        setToken(data['tokenValue'])
        this.local.isLogin = data['isLogin']
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
