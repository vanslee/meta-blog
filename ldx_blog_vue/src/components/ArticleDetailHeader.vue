<template>
  <div>
    <div style="font-weight: 600; font-size: 3rem">{{ article.articleTitle }}</div>
    <el-divider />
    <span style="color: #909399; font-size: 0.75rem">
      {{ formatTime(article.publishDate) }}
      {{ article.views }}阅读 {{ article.likes }}喜欢 {{ article.collects }}收藏 {{ article.comments }}评论
    </span>
    <div style="display: flex; justify-content: space-between; align-items: center; margin-top: 1.25vh">
      <div style="display: flex">
        <img v-lazy="author.avatarImgUrl ?? ''" style="width: 10vw" />
        <div style="display: flex; flex-direction: column; justify-content: space-around; margin-left: 1.25vw">
          <span>{{ author.username }}</span>
          <span style="color: #909399; font-size: 0.75rem">
            粉丝: {{ author.fans }} 文章: {{ author.articleCount }}
          </span>
        </div>
      </div>
      <el-button type="primary" style="margin-right: 1vw">关注</el-button>
    </div>
    <el-divider />
    <div style="width: 100%; height: 30vh; text-align: center">
      <img
        v-lazy="article.imgUrl"
        style="object-fit: scale-down; width: 95%; height: 95%; border-radius: 4px; border: 1px solid #909399"
      />
    </div>
    <el-divider />
  </div>
</template>
<script>
import { mapActions, mapState } from 'pinia'
import { useArticleStore } from '@/stores/article'
import { formatTime } from '@/utils/time'
export default {
  data() {
    return {}
  },
  created() {},
  computed: {
    ...mapState(useArticleStore, ['titles', 'article', 'author']),
    article_id() {
      return parseInt(this.$route.params.id)
    }
  },
  methods: {
    formatTime
  }
}
</script>
<style scoped></style>
