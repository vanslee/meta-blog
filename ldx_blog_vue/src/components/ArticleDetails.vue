<template>
  <div class="scorll_wraper" ref="scorll_wraper">
    <v-md-preview :text="article.content" ref="preview" />
    <comment :article_id="$route.params.id" />
  </div>
</template>
<script>
import { getArticleDetailsApi } from '@/apis/article'
import Comment from '@/components/comment'
import { useArticleStore } from '@/stores/article'
export default {
  components: {
    Comment
  },
  data() {
    const titles = []
    return {
      titles,
      article: {},
      store: useArticleStore()
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
      // scorll_wraper['preview']
      scorll_wraper.scrollTop = scorll_wraper.clientHeight
      const { lineIndex } = anchor
      scorll_wraper.scrollTop =
        preview.$el.querySelector(`[data-v-md-line="${lineIndex}"]`).offsetTop - scorll_wraper.offsetTop
      // const { preview } = this.$refs
      // const { lineIndex } = anchor
      // const heading = preview.$el.querySelector(`[data-v-md-line="${lineIndex}"]`)
      // if (heading) {
      //   preview.scrollToTarget({
      //     target: heading,
      //     scrollContainer: window,
      //     top: 60
      //   })
      // }
    })
  },
  computed: {},
  methods: {
    async fetchData() {
      console.log('sad')
      const { data } = await getArticleDetailsApi(this.$route.params.id)
      this.article = data
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
        this.store.titles = this.titles
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
</style>
