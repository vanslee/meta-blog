<template>
  <el-row :gutter="20" type="flex" justify="space-between">
    <el-col :span="16" class="article_card_box">
      <el-row>
        <el-col>
          <el-col :span="8">
            <el-row type="flex" justify="center">
              <el-image :src="article.authorAvatar" fit="fill" style="widht: 4vw; height: 4vw; border-radius: 50%" />
            </el-row>
            <el-row type="flex" justify="center">
              <h3 :span="4">{{ article.authorName }}</h3>
            </el-row>
          </el-col>
          <el-col :span="10" :offset="4">
            <h1 style="font-size: 2rem; color: gray">{{ article.articleTitle }}</h1>
          </el-col>
        </el-col>
        <el-col>
          <div @click="jumpToArticleDetails">
            <v-md-preview :text="article.articleOmission" />
          </div>
        </el-col>
        <el-col>
          <div v-if="article.tags.length">
            <el-divider content-position="left">
              <i class="el-icon-collection-tag" />
              文章标签
            </el-divider>
            <el-tag v-for="(tag, index) in article.tags" :key="tag + index" style="margin: 0 5px 0 0">
              <i class="el-icon-collection-tag" />
              {{ tag }}
            </el-tag>
          </div>
          <div v-if="article.categories.length">
            <el-divider content-position="left">
              <i class="el-icon-collection-tag" />
              文章分类
            </el-divider>
            <el-tag
              v-for="(category, index) in article.categories"
              :key="category + index"
              style="margin: 0 5px 0 0"
              type="danger"
            >
              <i class="el-icon-s-management"></i>
              {{ category }}
            </el-tag>
            <el-divider />
          </div>
        </el-col>
        <el-col style="font-size: 1.25rem">
          <i><span class="el-icon-view"></span></i>
          {{ article.views }} |
          <i><span class="el-icon-trophy"></span></i>
          {{ article.likes }} |
          <i><span class="el-icon-chat-dot-round"></span></i>
          {{ article.comments }}
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
      this.$router.push({ path: `/article/${this.article.id}` })
    }
  }
}
</script>
<style scoped>
.article_card_box {
  cursor: pointer;
}
</style>
