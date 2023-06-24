import {
  deleteAritcleApi,
  getArticleListApi,
  publishArticleApi,
  getMarkdownTextApi,
  getArticleDetailsApi,
  getArticleCategoriesApi,
} from '@/apis/article'
import { defineStore } from 'pinia'
import { getArticleTagsApi } from '@/apis/article'
import { Message } from 'element-ui'
// useStore 可以是 useUser、useCart 之类的任何东西
// 第一个参数是应用程序中 store 的唯一 id
export const useArticleStore = defineStore('article', {
  // other options...
  state: () => ({
    articles: [],
    author: {},
    titles: [],
    article: {},
    tagsIpage: {},
    categoriesIpage: {},
    params: {
      uid: '',
      size: 10,
      total: 0,
      current: 1,
    }
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
        // this.article.imgUrl = `${process.env.VUE_APP_WEBSITE_CDN}${this.article.imgUrl}`
        // this.article.mdUrl = `${process.env.VUE_APP_WEBSITE_CDN}${this.article.mdUrl}`
        const { text } = await getMarkdownTextApi(this.article.mdUrl)
        this.article.articleContent = text
        // this.author.avatarImgUrl = `${process.env.VUE_APP_WEBSITE_CDN}${this.author.avatarImgUrl}`
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
    },
    async getArticleList() {
      // this.params.uid = uid
      const { data } = await getArticleListApi(this.params)
      this.articles = data.records
      this.params.total = data.total
      this.params.current = data.current
      this.params.size = data.size
    },
    async handleCurrentChange(current) {
      this.params.current = current
      this.getArticleList()
    },
    async handleSizeChange(size) {
      this.params.size = size
      this.getArticleList()
    },
    async deleteArticle(id) {
      const { code, msg } = await deleteAritcleApi(id)
      if (code === 200) {
        Message.success(msg)
        this.getArticleList()
      }
    }
  }
})
