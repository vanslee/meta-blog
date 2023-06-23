<template>
  <div>
    <comprehensive-table
      :table-data="tableData"
      :params="params"
      @handle-edit="handleEdit"
      @search-keyword="handleSearch"
      @size-change="handleSizeChange"
      @delete-row="deleteArticleById"
      @batch-delete="handleBatchDelete"
      @current-change="handleCurrentChange"
    />
  </div>
</template>
<script>
  import { mapActions, mapState } from 'vuex'
  import ComprehensiveTable from '@/views/vab/table/index'
  export default {
    components: {
      ComprehensiveTable,
    },
    data() {
      return {}
    },
    computed: mapState({
      params: ({ article }) => article.params,
      tableData: ({ article }) => article.articles,
    }),

    created() {
      this.fetchData(this.params)
    },
    methods: {
      ...mapActions({
        setSize: 'article/setSize',
        setCurrent: 'article/setCurrent',
        setKeyword: 'article/setKeyword',
        getArticleById: 'article/getArticleById',
        getArticlesByUid: 'article/getArticlesByUid',
        deleteArticleById: 'article/deleteArticleById',
        deleteArticleById: 'article/deleteArticleById',
        deleteBatchArticle: 'article/deleteBatchArticle',
      }),
      // async fetchData() {
      //    getArticlesByUidApi(this.params)
      //   this.tableData = data.records
      //   this.params.total = data.total
      //   // console.log('data', data)
      //   // console.log('code', code)
      // },
      fetchData() {
        this.getArticlesByUid()
          .then(() => {
            console.log('查询成功')
          })
          .catch(() => {
            console.log('查询失败')
          })
      },
      handleCurrentChange(current) {
        this.setCurrent(current)
        this.fetchData()
      },
      handleSizeChange(size) {
        this.setSize(size)
        this.fetchData()
      },
      handleSearch(keyword) {
        this.setKeyword(keyword)
        this.fetchData()
        // this
      },
      handleBatchDelete(ids) {
        this.deleteBatchArticle(ids)
      },
      async handleEdit(id) {
        console.log(Date.now())
        await this.getArticleById(id)
        this.$router.push({
          name: 'ArticleEdit',
        })
      },
    },
  }
</script>
<style scoped></style>
