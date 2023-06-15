<template>
  <el-row type="flex" justify="space-around">
    <el-col :span="16">
      <el-row type="flex" align="middle">
        <el-col :span="3">
          <el-image
            :src="article.authorAvatar"
            fit="fill"
            style="widht: 4vw; height: 4vw; border-radius: 50%"
          ></el-image>
        </el-col>
        <el-col :span="20">
          <el-row>
            <el-col>
              <span style="font-size: 1.25rem">{{ article.articleTitle }}</span>
            </el-col>
            <el-col>
              <el-link type="info" v-for="(tag, index) in article.tags" :key="'tag' + index">{{ tag }} &nbsp;</el-link>
              <el-link type="info" v-for="(category, index) in article.categories" :key="'category' + index">
                {{ category }} &nbsp;
              </el-link>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
      <el-row style="cursor: pointer">
        <div @click="jumpToArticleDetails">
          <mark-down-cpt :md-url="article.mdUrl" />
        </div>
      </el-row>
      <el-row :gutter="10">
        <el-col :span="2">
          <i class="iconfont icon-shoucang"></i>
          {{ article.comments }}
        </el-col>
        <el-col :span="2">
          <i class="iconfont icon-icon"></i>
          {{ article.views }}
        </el-col>
        <el-col :span="2">
          <i class="iconfont icon-liuyan"></i>
          {{ article.likes }}
        </el-col>
      </el-row>
    </el-col>
    <el-col :span="4" style="padding: 15px 10px 10px 0">
      <img :src="article.imgUrl" style="width: 200px; height: 200px" />
    </el-col>
  </el-row>
</template>
<script>
import MarkDownCpt from '@/components/MarkDownCpt.vue'
export default {
  props: {
    article: {
      type: Object,
      required: true,
      default: () => {}
    }
  },
  components: {
    MarkDownCpt
  },
  data() {
    return {
      content: ''
    }
  },
  created() {},
  computed: {},
  mounted() {},
  methods: {
    jumpToArticleDetails() {
      this.$router.push({ name: `Article`, params: { id: this.article.id } })
    }
  }
}
</script>
<style scoped>
.article_card_box {
  cursor: pointer;
}
</style>
