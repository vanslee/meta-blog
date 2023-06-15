<template>
  <div @click="jumpToArticleDetails">
    <el-row type="flex" align="center" justify="space-between">
      <el-col :span="6">
        <div style="display: flex; justify-content: space-around">
          <div style="display: flex; justify-content: space-around; align-items: center; flex-direction: column">
            <el-image style="width: 10vw" :src="article.authorAvatar" />
            <i style="font-weight: 700; color: #e6a23c">{{ article.authorName }}</i>
            <span style="font-size: 0.75rem; color: #909399">
              分类:
              <i v-for="(category, index) in article.categories" :key="'category' + index">{{ category }}</i>
            </span>
            <span style="font-size: 0.75rem; color: #909399">
              标签:
              <i v-for="(tag, index) in article.tags" :key="'tag' + index">{{ tag }}</i>
            </span>
          </div>
        </div>
      </el-col>
      <el-col :span="10">
        <h3 style="color: #409eff">{{ article.articleTitle }}</h3>
        <mark-down-cpt :md-url="article.mdUrl" />
      </el-col>
      <el-col :span="6">
        <el-image :src="article.imgUrl" />
      </el-col>
    </el-row>
  </div>
</template>
<script>
import MarkDownCpt from '@/components/MarkDownCpt.vue'
// import { useArticleStore } from '@/stores/article'
// import { mapState } from 'pinia'
export default {
  name: 'ArticleMobile',
  props: {
    article: {
      type: Object,
      default: () => {}
    }
  },
  components: {
    MarkDownCpt
  },
  data() {
    return {}
  },
  computed: {},
  methods: {
    jumpToArticleDetails() {
      this.$router.push({ name: `Article`, params: { id: this.article.id } })
    }
  }
}
</script>
<style scoped></style>
