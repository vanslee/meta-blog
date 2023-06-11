<template>
  <div @click="jumpToArticleDetails">
    <el-row type="flex" align="center" justify="space-between">
      <el-col :span="18">
        <div style="display: flex; justify-content: space-around">
          <div style="display: flex; justify-content: space-around; align-items: center; flex-direction: column">
            <el-image style="width: 10vw" :src="article.authorAvatar" />
            <i style="font-weight: 700">{{ article.authorName }}</i>
          </div>
          <span>{{ article.articleOmission }}</span>
        </div>
      </el-col>
      <el-col :span="6">
        <el-image :src="article.imgUrl" />
      </el-col>
    </el-row>
  </div>
</template>
<script>
// import { useArticleStore } from '@/stores/article'
// import { mapState } from 'pinia'
export default {
  props: {
    article: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {}
  },
  created() {
    console.log(this.article)
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
