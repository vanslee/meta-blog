<template>
  <div class="scorll-wrapper">
    <el-timeline style="padding: 0">
      <el-timeline-item
        v-for="article in articles"
        :key="article.id"
        :timestamp="formatTimeStamp(article.publishDate)"
        placement="top"
      >
        <el-card>
          <ArticleCard class="hidden-xs-only" :article="article" />
          <ArticleMobile class="hidden-sm-and-up" :article="article" />
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
import ArticleMobile from '@/layout/components/Mains/ArticleMobile.vue'
import { useUserStore } from '@/stores/user'
export default {
  components: {
    ArticleCard,
    ArticleMobile
  },
  data() {
    const userStore = useUserStore()
    const params = {
      current: 0,
      size: 3
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
    console.log(this.userStore.token)
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
  height: 90vh;
  overflow-y: auto;
  overflow-x: hidden;
}
</style>
