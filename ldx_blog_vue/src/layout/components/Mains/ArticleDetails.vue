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
      <el-col :span="2" class="hidden-xs-only">
        <el-image v-if="author.avatarImgUrl" :src="author.avatarImgUrl" class="user-avatar" />
      </el-col>
      <el-col :span="5" class="hidden-sm-and-up">
        <el-image v-if="author.avatarImgUrl" :src="author.avatarImgUrl" class="user-avatar" />
      </el-col>
      <el-col :span="18">
        <el-col>{{ author.username }}</el-col>
        <el-col>
          <span style="color: gray; font-size: 0.75rem">粉丝: {{ author.fans }} 文章: {{ author.articleCount }}</span>
        </el-col>
      </el-col>
      <el-col :span="3" class="hidden-xs-only"><el-button style="width: 100%" type="primary">关注</el-button></el-col>
      <el-col :span="5" class="hidden-sm-and-up"><el-button style="width: 100%" type="primary">关注</el-button></el-col>
    </el-row>
    <el-image
      v-if="article.imgUrl"
      :src="article.imgUrl"
      style="width: 95%; height: 300px; border-radius: 4px; margin: 20px 0; border: 1px solid gray"
    />
    <el-divider />
    <v-md-preview :text="article.articleContent" ref="preview" />
    <comment :article_id="article_id" />
  </div>
</template>
<script>
import Comment from '@/views/comment/index.vue'
import { formatTime } from '@/utils/time'
import { mapActions, mapState } from 'pinia'
import { useArticleStore } from '@/stores/article'
export default {
  components: {
    Comment
  },
  computed: {
    ...mapState(useArticleStore, ['titles', 'article', 'author']),
    article_id() {
      return parseInt(this.$route.params.id)
    }
  },
  data() {
    return {
      formatTime
    }
  },
  created() {
    console.log('article,t', this.article)
  },
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
    this.$nextTick(() => {
      this.$bus.$on('scroll-to-end', this.scrollToEnd)
    })
  },
  methods: {
    fetchData() {
      const success = this.getArticleDetails(this.$route.params.id)
      if (success) {
        this.$nextTick(() => {
          const anchors = this.$refs['preview']?.$el.querySelectorAll('h1,h2,h3,h4,h5,h6')
          if (!anchors) {
            return
          }
          let titles = Array.from(anchors).filter(title => !!title.innerText.trim())
          if (!titles.length) {
            this.setTitles([])
            return
          }
          const hTags = Array.from(new Set(titles.map(title => title.tagName))).sort()
          titles = titles.map(el => ({
            title: el.innerText,
            lineIndex: el.getAttribute('data-v-md-line'),
            indent: hTags.indexOf(el.tagName)
          }))
          this.setTitles(titles)
          // this.articleStore.titles = this.titles
        })
      }
    },
    scrollToEnd() {
      const el = this.$refs['scorll_wraper']
      if (el) {
        el.scrollTo({ top: el.scrollHeight, behavior: 'smooth' })
      }
    },
    ...mapActions(useArticleStore, ['getArticleDetails', 'setTitles'])
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
