import { defineStore } from 'pinia'
import { userInfoApi } from '@/apis/user'
// useStore 可以是 useUser、useCart 之类的任何东西
// 第一个参数是应用程序中 store 的唯一 id
export const useUserStore = defineStore('user', {
  // other options...
  state: () => ({
    local: {}
  }),
  getters: {},
  actions: {
    async fetchUserInfo(userId) {
      console.log(userId)
      if (userId) {
        const { data, code } = await userInfoApi(userId)
        if (code === 200) {
          this.local = data
        }
      }
    }
  }
})
