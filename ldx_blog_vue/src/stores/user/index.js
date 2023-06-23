import { defineStore } from 'pinia'
import { Notification } from 'element-ui'
import {
  getAccessToken,
  removeAccessToken,
  setAccessToken,
} from '@/utils/accessToken'
import { loginApi, userInfoApi, logoutApi } from '@/apis/user'
import { title, tokenName } from '@/config'
import { Message } from 'element-ui'
export const useUserStore = defineStore('user', {
  // 开启持久化
  persist: false,
  // 定义state
  state: () => ({
    user: {},
    accessToken: '',
    hasLogin: false,
    permissions: [],
    username: '',
    avatar: '',
  }),
  getters: {},
  actions: {
    // 获取用户信息
    async setToken(token) {
      this.hasLogin = true
      this.token = token
    },
    // 用户登录获取token
    async login(params) {
      const { data } = await loginApi(params)
      const accessToken = data['tokenValue']
      if (accessToken) {
        this.accessToken = accessToken
        setAccessToken(accessToken)
        const hour = new Date().getHours()
        const thisTime =
          hour < 8
            ? '早上好'
            : hour <= 11
              ? '上午好'
              : hour <= 13
                ? '中午好'
                : hour < 18
                  ? '下午好'
                  : '晚上好'
        Notification.success(`欢迎登录${title}`, `${thisTime}！`)
      } else {
        Notification.error(`登录接口异常，未正确返回${tokenName}...`)
      }
    },
    // 获取token成功后获取用户信息
    async getUserInfo() {
      const { data } = await userInfoApi(this.accessToken)
      if (!data) {
        Message.error('验证失败，请重新登录...')
        return false
      }
      let { permissions, username, avatarImgUrl } = data
      permissions = ['admin']
      if (permissions && username && Array.isArray(permissions)) {
        this.permissions = permissions
        this.username = username
        this.avatar = avatarImgUrl
        return permissions
      } else {
        Message.error('用户信息接口异常')
        return false
      }
      // const { data, code } = await userInfoApi()
      // if (code === 200) {
      //   // 获取用户信息
      //   data.avatarImgUrl =
      //     process.env.VUE_APP_WEBSITE_CDN + data.avatarImgUrl
      //   this.user = data
      //   return true
      // } else {
      //   return false
      // }
    },
    async logout() {
      const { code } = await logoutApi()
      if (code === 200) {
        this.$reset()
        return true
      }
      return false
    },
    setPermissions(permissions) {
      this.permissions = permissions
    }
  }
})
