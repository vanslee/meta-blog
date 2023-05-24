import { defineStore } from 'pinia'

// useStore 可以是 useUser、useCart 之类的任何东西
// 第一个参数是应用程序中 store 的唯一 id
export const useArticleStore = defineStore('article', {
  // other options...
  state: () => ({
    local: '',
    titles: []
  }),
  getters: {
    getTitles: state => state.titles || []
  },
  actions: {
    setTitles() {},
    randomizeCounter() {
      this.counter = Math.round(100 * Math.random())
    }
  }
})
