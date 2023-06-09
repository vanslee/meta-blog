import { getArticleDetailsApi, publishArticleApi } from '@/apis/article'
import { defineStore } from 'pinia'

// useStore 可以是 useUser、useCart 之类的任何东西
// 第一个参数是应用程序中 store 的唯一 id
export const useArticleStore = defineStore('article', {
  // other options...
  state: () => ({
    article: {},
    author: {},
    titles: []
  }),
  getters: {
    getTitles: state => state.titles || []
  },
  actions: {
    setTitles(titles) {
      this.titles = titles
    },
    randomizeCounter() {
      this.counter = Math.round(100 * Math.random())
    },
    async publishArticle(article) {
      const { code } = publishArticleApi(article)
      if (code === 200) {
        return true
      } else {
        return false
      }
    },
    async getArticleDetails(articleId) {
      const { code, data } = await getArticleDetailsApi(articleId)
      if (code === 200) {
        this.author = data['author']
        this.article = data['article']
        this.article.imgUrl = `${process.env.VUE_APP_WEBSITE_CDN}${this.article.imgUrl}`
        this.author.avatarImgUrl = `${process.env.VUE_APP_WEBSITE_CDN}${this.author.avatarImgUrl}`
        return true
      } else {
        return false
      }
    }
  }
})
