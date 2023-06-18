<template>
  <div v-html="html" style="cursor: pointer;" />
</template>
<script>
import MarkdownIt from 'markdown-it'
import { getMarkdownTextApi } from '@/apis/article'
import { mapActions, mapState } from 'pinia'
import { useArticleStore } from '@/stores/article'
export default {
  name: 'MarkDownCpt',
  props: {
    mdUrl: {
      type: String,
      default: ''
    }
  },
  computed: {
    ...mapState(useArticleStore, ['titles'])
  },
  data() {
    return {
      html: ''
    }
  },
  mounted() {
    setTimeout(() => {
      this.fetchData()
    }, 500)
  },
  methods: {
    ...mapActions(useArticleStore, ['setTitles']),
    async fetchData() {
      const { text } = await getMarkdownTextApi(this.mdUrl)
      const md = new MarkdownIt()
      this.html = md.render(text.slice(0, 120))
    }
  }
}
</script>
<style scoped></style>
