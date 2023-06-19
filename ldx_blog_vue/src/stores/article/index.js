import {
  publishArticleApi,
  getMarkdownTextApi,
  getArticleDetailsApi,
  getArticleCategoriesApi
} from '@/apis/article'
import { defineStore } from 'pinia'
import { getArticleTagsApi } from '@/apis/article'
// useStore 可以是 useUser、useCart 之类的任何东西
// 第一个参数是应用程序中 store 的唯一 id
export const useArticleStore = defineStore('article', {
  // other options...
  state: () => ({
    author: {},
    titles: [],
    article: {},
    tagsIpage: {},
    categoriesIpage: {},
  }),
  getters: {
    getTitles: (state) => state.titles || [],
    tags: (state) => state.tagsIpage.records || [],
    totalTags: (state) => state.tagsIpage.total || 0,
    categories: (state) => state.categoriesIpage.records || [],
    totalCategories: (state) => state.categoriesIpage.total || 0,
  },
  actions: {
    setTitles(titles) {
      this.titles = titles
    },
    randomizeCounter() {
      this.counter = Math.round(100 * Math.random())
    },
    async publishArticle(article) {
      const { code } = await publishArticleApi(article)
      console.log(code);
      if (code === 200) {
        return true
      } else {
        return false
      }
    },
    async getArticleDetails(articleId) {
      const { code, data } = await getArticleDetailsApi(articleId)
      if (code === 200) {
        this.author = data.author
        this.article = data.article
        this.article.imgUrl = `${process.env.VUE_APP_WEBSITE_CDN}${this.article.imgUrl}`
        this.article.mdUrl = `${process.env.VUE_APP_WEBSITE_CDN}${this.article.mdUrl}`
        const { text } = await getMarkdownTextApi(this.article.mdUrl)
        this.article.articleContent = text
        this.author.avatarImgUrl = `${process.env.VUE_APP_WEBSITE_CDN}${this.author.avatarImgUrl}`
        return true
      } else {
        return false
      }
    },
    async getArticleTags(params) {
      const { data, code } = await getArticleTagsApi(params)
      if (code === 200) {
        this.tagsIpage = data
      }
    },
    async getArticleCategoires(params) {
      const { data, code } = await getArticleCategoriesApi(params)
      if (code === 200) {
        this.categoriesIpage = data
      }
    }
  }
})
