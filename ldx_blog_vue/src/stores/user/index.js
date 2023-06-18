import { defineStore } from 'pinia'
import { loginApi, userInfoApi, logoutApi } from '@/apis/user'
export const useUserStore = defineStore('user', {
  // 开启持久化
  persist: true,
  // 定义state
  state: () => ({
    user: {},
    token: '',
    hasLogin: false
  }),
  getters: {},
  actions: {
    // 获取用户信息
    async setToken (token) {
      this.hasLogin = true
      this.token = token
    },
    // 用户登录获取token
    async login (params) {
      const { data, code } = await loginApi(params)
      if (code === 200) {
        this.token = data.tokenValue
        this.hasLogin = true
        const success = await this.getUserInfo()
        return success
      }
    },
    // 获取token成功后获取用户信息
    async getUserInfo () {
      const { data, code } = await userInfoApi()
      if (code === 200) {
        // 获取用户信息
        data.avatarImgUrl =
          process.env.VUE_APP_WEBSITE_CDN + data.avatarImgUrl
        this.user = data
        return true
      } else {
        return false
      }
    },
    async logout () {
      const { code } = await logoutApi()
      if (code === 200) {
        this.$reset()
        return true
      }
      return false
    }
  }
})
