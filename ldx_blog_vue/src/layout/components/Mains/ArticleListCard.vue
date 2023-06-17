<template>
  <div class="scorll-wrapper">
    <el-timeline style="padding: 0">
      <el-timeline-item
        v-for="article in articles"
        :key="article.id"
        :timestamp="formatTimeStamp(article.publishDate)"
        placement="top"
      >
        <el-card :body-style="{ padding: '10px' }">
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
import ArticleCard from '@/layout/components/Mains/ArticieCard.vue'
import { useUserStore } from '@/stores/user'
export default {
  components: {
    ArticleCard
  },
  data() {
    const userStore = useUserStore()
    const params = {
      current: 0,
      size: 5
    }
    return {
      total: 0,
      params,
      userStore,
      articles: [],
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
::v-deep .el-timeline-item__wrapper {
  padding-left: 18px;
}
.article_box {
  height: 100%;
}
.pagination {
  display: flex;
  justify-content: center;
}
.scorll-wrapper {
  height: 100%;
  overflow-y: auto;
  overflow-x: hidden;
}
</style>
