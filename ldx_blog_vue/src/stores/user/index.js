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
    async getUserInfo() {
      const { code, data } = await userInfoApi()
      if (code === 200) {
        // 获取用户信息
        data['avatarImgUrl'] = process.env.VUE_APP_WEBSITE_CDN + data['avatarImgUrl']
        this.user = data
        return true
      } else {
        return false
      }
    },
    async setToken(token) {
      this.hasLogin = true
      this.token = token
    },
    // 用户登录获取token
    async login(params) {
      return mew Promise((resolve, reject) => {
        const { code, data } = await loginApi(params)
        if (code === 200) {
          this.token = data['tokenValue']
          this.hasLogin = true
          // 获取token成功后获取用户信息
          const { data, code } = await userInfoApi()
          if (code === 200) {
            // 获取用户信息
            data['avatarImgUrl'] = process.env.VUE_APP_WEBSITE_CDN + data['avatarImgUrl']
            this.user = data
            resolve({ success: true })
          } else {
            resolve({ success: false })
          }
        }
        resolve({ success: false })
      })
     
    },
    async logout() {
      console.log('this', this)
      const { code } = await logoutApi()
      if (code === 200) {
        this.$reset()
        return true
      }
      return false
    }
  }
})
