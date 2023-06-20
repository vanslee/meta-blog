<template>
  <div>
    <comprehensive-table
      :table-data="tableData"
      :params="params"
      @current-change="handleCurrentChange"
      @size-change="handleSizeChange"
    />
  </div>
</template>
<script>
  import ComprehensiveTable from '@/views/vab/table/index'
  import { getArticlesByUidApi } from '@/api/article.js'
  export default {
    components: {
      ComprehensiveTable,
    },
    data() {
      const params = {
        desc: '',
        size: 6,
        total: 0,
        current: 1,
        keyword: '',
      }
      return {
        params,
        uid: '1190797313',
        tableData: [],
      }
    },
    computed: {},
    created() {
      this.fetchData()
    },
    methods: {
      async fetchData() {
        const { data } = await getArticlesByUidApi(this.uid, this.params)
        this.tableData = data.records
        this.params.total = data.total
        // console.log('data', data)
        // console.log('code', code)
      },
      handleCurrentChange(current) {
        console.log('ssss')
        this.params.current = current
        this.fetchData()
      },
      handleSizeChange(size) {
        this.params.size = size
        this.fetchData()
      },
    },
  }
</script>
<style scoped></style>
