/**
 * @author https://vue-admin-beautiful.com （不想保留author可删除）
 * @description 异常捕获的状态拦截，请勿修改
 */
import Vue from 'vue'
import {
  updateArticleApi,
  getArticleByIdApi,
  getMarkdownTextApi,
  getArticlesByUidApi,
  deleteArticleByIdApi,
  deleteBatchArticleApi,
} from '@/api/article.js'
const state = () => ({
  params: {
    size: 10,
    total: 0,
    current: 0,
    keyword: '',
    uid: '1190797313',
  },
  articles: [],
  article: {},
})
const getters = {
  errorLogs: (state) => state.errorLogs,
}
const mutations = {
  addArticles(state, data) {
    const { current, records, total } = data
    if (records instanceof Array) {
      state.articles = records
      state.params.total = total
      state.params.current = current
    }
  },
  setArticle(state, article) {
    state.article = article
  },
  setArticleContent(state, content) {
    state.article.articleContent = content
  },
  rmArticle(state, id) {
    state.articles.forEach((article, index) => {
      if (article.id === id) {
        state.articles.splice(index, 1)
      }
    })
  },
  setCurrent(state, current) {
    state.params.current = current
  },
  setSize(state, size) {
    state.params.size = size
  },
  setKeyword(state, keyword) {
    state.params.keyword = keyword
  },
  setTags(state, name) {
    let isExist = false
    state.article.tags.forEach((tag) => {
      if (tag.name === name) {
        isExist = true
      }
    })
    if (!isExist) {
      const tag = {}
      tag.name = name
      state.article.tags.push(tag)
    }
  },
  removeTag(state, name) {
    state.article.tags.forEach((tag, index) => {
      if (tag.name === name) {
        state.article.tags.splice(index, 1)
      }
    })
  },
  setCategories(state, name) {
    let isExist = false
    state.article.categories.forEach((category) => {
      if (category.name === name) {
        isExist = true
      }
    })
    if (!isExist) {
      const category = {}
      category.name = name
      state.article.categories.push(category)
    }
  },
  removeCategory(state, name) {
    state.article.categories.forEach((category, index) => {
      if (category.name === name) {
        state.article.categories.splice(index, 1)
      }
    })
  },
}
const actions = {
  setSize({ commit }, size) {
    commit('setSize', size)
  },
  setCurrent({ commit }, current) {
    commit('setCurrent', current)
  },
  setKeyword({ commit }, keyword) {
    commit('setKeyword', keyword)
  },
  async getArticlesByUid({ commit }) {
    const { article } = this.state
    const { data } = await getArticlesByUidApi(article.params)
    commit('addArticles', data)
  },
  async deleteArticleById({ commit }, id) {
    const { code } = await deleteArticleByIdApi(id)
    if (code === 200) {
      Vue.prototype.$baseMessage('删除成功', 'success')
    }
    commit('rmArticle', id)
  },
  async deleteBatchArticle({ commit }, ids) {
    const { code } = await deleteBatchArticleApi(ids)
    if (code === 200) {
      Vue.prototype.$baseMessage('删除成功', 'success')
    }
    commit('rmArticle', id)
  },
  async getArticleById({ commit }, id) {
    const { data } = await getArticleByIdApi(id)
    const { text } = await getMarkdownTextApi(data.mdUrl)
    localStorage.setItem('content', text)
    commit('setArticle', data)
  },
  async updateArticle({ commit }, article) {
    await updateArticleApi(article)
    this.getArticlesByUid()
  },
}
export default { state, getters, mutations, actions }
