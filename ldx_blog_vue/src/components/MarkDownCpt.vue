<template>
  <div v-html="html" />
</template>
<script>
import showdown from 'showdown'
import { getMarkdownTextApi } from '@/apis/article'
export default {
  name: 'MarkDownCpt',
  props: {
    mdUrl: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      html: ''
    }
  },
  mounted() {
    this.fetchData()
  },
  computed: {},
  methods: {
    async fetchData() {
      const { text } = await getMarkdownTextApi(this.mdUrl)
      const converter = new showdown.Converter()
      this.html = converter.makeHtml(text)
    }
  }
}
</script>
<style scoped></style>
