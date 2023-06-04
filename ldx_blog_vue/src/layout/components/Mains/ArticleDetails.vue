<template>
  <div class="scorll_wraper" ref="scorll_wraper">
    <el-row style="margin: 10px 0">
      <span style="font-weight: 700; font-size: 3rem">{{ article.articleTitle }}</span>
    </el-row>
    <el-row style="margin: 10px 0">
      <span style="margin;color: gray;font-size: 0.75rem">
        {{ formatTime(article.publishDate) }}
        {{ article.views }}阅读 {{ article.likes }}喜欢 {{ article.collects }}收藏 {{ article.comments }}评论
      </span>
    </el-row>
    <el-row type="flex" align="middle">
      <el-col :span="2">
        <el-image :src="article.user.avatarImgUrl" class="user-avatar" />
      </el-col>
      <el-col :span="18">
        <el-col>{{ article.user.username }}</el-col>
        <el-col>
          <span style="color: gray; font-size: 0.75rem">
            粉丝: {{ article.user.fans }} 文章: {{ article.user.articleCount }}
          </span>
        </el-col>
      </el-col>
      <el-col :span="3"><el-button style="width: 100%" type="primary">关注</el-button></el-col>
    </el-row>
    <el-image
      :src="article.imgUrl"
      style="width: 95%; height: 300px; border-radius: 4px; margin: 20px 0; border: 1px solid gray"
    />
    <el-divider />
    <v-md-preview :text="article.articleContent" ref="preview" />
    <comment :article_id="article_id" />
  </div>
</template>
<script>
import { getArticleDetailsApi } from '@/apis/article'
import Comment from '@/views/comment'
import { formatTime } from '@/utils/time'
export default {
  components: {
    Comment
  },
  computed: {
    article_id() {
      return parseInt(this.$route.params.id)
    }
  },
  data() {
    const titles = []
    return {
      titles,
      formatTime,
      article: { user: {} }
    }
  },
  created() {},
  mounted() {
    this.fetchData()
    this.$bus.$on('directory-navigation', anchor => {
      const { scorll_wraper, preview } = this.$refs
      if (!scorll_wraper || !preview) {
        return
      }
      scorll_wraper.scrollTop = scorll_wraper.clientHeight
      const { lineIndex } = anchor
      scorll_wraper.scrollTop =
        preview.$el.querySelector(`[data-v-md-line="${lineIndex}"]`).offsetTop - scorll_wraper.offsetTop
    })
  },
  methods: {
    async fetchData() {
      const { data } = await getArticleDetailsApi(this.$route.params.id)
      this.article = data
      this.article.imgUrl = `${process.env.VUE_APP_WEBSITE_CDN}${this.article.imgUrl}`
      this.article.user.avatarImgUrl = `${process.env.VUE_APP_WEBSITE_CDN}${this.article.user.avatarImgUrl}`
      console.log('article', this.article)
      this.$nextTick(() => {
        const anchors = this.$refs['preview']?.$el.querySelectorAll('h1,h2,h3,h4,h5,h6')
        if (!anchors) {
          return
        }
        const titles = Array.from(anchors).filter(title => !!title.innerText.trim())
        if (!titles.length) {
          this.titles = []
          return
        }
        const hTags = Array.from(new Set(titles.map(title => title.tagName))).sort()
        this.titles = titles.map(el => ({
          title: el.innerText,
          lineIndex: el.getAttribute('data-v-md-line'),
          indent: hTags.indexOf(el.tagName)
        }))
        this.articleStore.titles = this.titles
      })
    }
  }
}
</script>
<style scoped>
.scorll_wraper {
  height: 90vh;
  overflow-y: auto;
  overflow-x: hidden;
}
.user-avatar {
  width: 60px;
  height: 60px;
  border-radius: 100px;
}
</style>
