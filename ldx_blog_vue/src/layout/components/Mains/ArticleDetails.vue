<template>
  <div class="scorll_wraper" ref="scorll_wraper">
    <article-detail-header />
    <!-- <mark-down-cpt :md-url="article.mdUrl" /> -->
    <v-md-editor mode="preview" :value="html" ref="preview" />
    <comment :article_id="article_id" />
  </div>
</template>
<script>
import Comment from '@/views/comment/index.vue'
import { getMarkdownTextApi } from '@/apis/article'
import { mapActions, mapState } from 'pinia'
import { useArticleStore } from '@/stores/article'
import MarkDownCpt from '@/components/MarkDownCpt.vue'
import ArticleDetailHeader from '@/components/ArticleDetailHeader.vue'
export default {
  components: {
    Comment,
    MarkDownCpt,
    ArticleDetailHeader
  },
  computed: {
    ...mapState(useArticleStore, ['titles', 'article', 'author']),
    article_id() {
      return parseInt(this.$route.params.id)
    }
  },
  data() {
    return {
      html: ''
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
    this.$nextTick(() => {
      this.$bus.$on('scroll-to-end', this.scrollToEnd)
    })
  },
  methods: {
    async fetchData() {
      await this.getArticleDetails(this.$route.params.id)
      const { text } = await getMarkdownTextApi(this.article.mdUrl)
      this.html = text
      setTimeout(() => {
        this.$nextTick(() => {
          const anchors = this.$refs.preview.$el.querySelectorAll('h1,h2,h3,h4,h5,h6')
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
        })
      }, 1000)
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
  height: 100%;
  overflow-y: auto;
  overflow-x: hidden;
}
.user-avatar {
  width: 60px;
  height: 60px;
  border-radius: 100px;
}
</style>
