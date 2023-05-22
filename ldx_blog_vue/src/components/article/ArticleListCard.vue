<template>
  <div class="scorll-wrapper">
    <el-timeline>
      <el-timeline-item
        v-for="article in articles"
        :key="article.id"
        :timestamp="formatTimeStamp(article.publishDate)"
        placement="top"
      >
        <el-card>
          <ArticleCard :article="article" />
        </el-card>
      </el-timeline-item>
    </el-timeline>
    <el-pagination
      class="pagination"
      background
      layout="prev, pager, next"
      :total="total"
      @current-change="changePage"
    />
  </div>
</template>
<script>
import { formatTimeStamp } from '@/utils/time'
import { getArticleListApi } from '@/apis/article'
import ArticleCard from '@/components/ArticieCard.vue'
export default {
  components: {
    ArticleCard
  },
  data() {
    const params = {
      current: 1,
      size: 3
    }
    return {
      total: 0,
      articles: [],
      params,
      formatTimeStamp
    }
  },
  created() {
    this.fetchData()
  },
  computed: {},
  methods: {
    async fetchData() {
      const { data = {} } = await getArticleListApi(this.params)
      this.articles = data['records'] || []
      this.total = data['total']
    },
    changePage(current) {
      this.params.current = current
      this.fetchData()
    }
  }
}
</script>
<style scoped>
.article_box {
  height: 100%;
}
.pagination {
  display: flex;
  justify-content: center;
}
.scorll-wrapper {
  height: 90vh;
  overflow-y: auto;
  overflow-x: hidden;
}
</style>
