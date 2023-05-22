<template>
  <el-row :gutter="20" type="flex" justify="space-between">
    <el-col :span="16" class="article_card_box">
      <el-row>
        <el-col>头像</el-col>
        <el-col>
          <el-row>
            <el-col :span="4">{{ article.author }}</el-col>
          </el-row>
        </el-col>
        <el-col>
          <div @click="jumpToArticleDetails">
            <v-md-preview :text="article.articleContent.substring(0, 50)" />
          </div>
        </el-col>
        <el-col>
          <i><span class="el-icon-view"></span></i>
          156 |
          <i><span class="el-icon-trophy"></span></i>
          156 |
          <i><span class="el-icon-chat-dot-round"></span></i>
          2
        </el-col>
      </el-row>
    </el-col>
    <el-col :span="8" style="padding: 15px 10px 10px 0">
      <img :src="article.imgUrl" style="width: 200px; height: 200px" />
    </el-col>
  </el-row>
</template>
<script>
export default {
  props: {
    article: {
      type: Object,
      required: true,
      default: () => {}
    }
  },
  data() {
    return {}
  },
  created() {},
  computed: {},
  mounted() {},
  methods: {
    jumpToArticleDetails() {
      this.$router.push({ path: `/article/${this.article.articleId}` })
    }
  }
}
</script>
<style scoped>
.article_card_box {
  cursor: pointer;
}
</style>
