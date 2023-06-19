<template>
  <div class="scorll-wrapper">
    <el-timeline style="padding: 0">
      <el-timeline-item v-for="article in articles" :key="article.id" :timestamp="formatTimeStamp(article.publishDate)"
        placement="top">
        <el-card :body-style="{ padding: '10px' }">
          <ArticleCard :article="article" />
        </el-card>
      </el-timeline-item>
    </el-timeline>
    <el-pagination class="pagination" background layout="prev, pager, next" :total="total" @current-change="changePage" />
  </div>
</template>
<script>
import { useUserStore } from '@/stores/user'
import { formatTimeStamp } from '@/utils/time'
import { getArticleListApi } from '@/apis/article'
import ArticleCard from '@/layout/components/Mains/ArticieCard.vue'
export default {
  components: {
    ArticleCard
  },
  data() {
    const userStore = useUserStore()
    const params = {
      size: 5,
      cid: -1,
      current: 0,
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
    if (this.$route.params.cid) {
      this.params.cid = parseInt(cid)
    } else {
      this.params.cid = -1
    }

    this.fetchData()
  },
  computed: {},
  methods: {
    async fetchData() {
      const { code, data = {} } = await getArticleListApi(this.params)
      if (code === 200) {
        console.log(data);
        this.articles = data.records || []
        this.total = data.total
      }

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
