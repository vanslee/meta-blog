<template>
  <div
    style="width: 100%; height: 100%"
    @click="jumpToArticleDetails"
  >
    <div style="display: flex; justify-content: space-between; flex-shrink: 0; align-items: center">
      <div style="width: 15%; display: flex; align-items: center; flex-direction: column">
        <img
          style="width: 100%"
          v-lazy="article.authorAvatar"
        >
        <i style="font-weight: 700; color: #e6a23c">{{ article.authorName }}</i>
        <span style="font-size: 0.75rem; color: #909399">
          分类:
          <i
            v-for="(category, index) in article.categories"
            :key="'category' + index"
          >{{ category }}</i>
        </span>
        <span style="font-size: 0.75rem; color: #909399">
          标签:
          <i
            v-for="(tag, index) in article.tags"
            :key="'tag' + index"
          >{{ tag }}</i>
        </span>
      </div>
      <div style="width: 50%; padding: 0 10vw; overflow: hidden">
        <h3 style="color: #409eff; white-space: nowrap; text-overflow: ellipsis">
          {{ article.articleTitle }}
        </h3>
        <mark-down-cpt :md-url="article.mdUrl" />
      </div>
      <div style="width: 35%">
        <img
          v-lazy="article.imgUrl"
          style="width: 95%; height: 100%; border: 1px solid #909399; border-radius: 0; object-fit: fill"
        >
      </div>
    </div>
  </div>
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
  data () {
    return {
      content: ''
    }
  },
  created () {},
  computed: {},
  mounted () {},
  methods: {
    jumpToArticleDetails () {
      this.$router.push({ name: 'Article', params: { id: this.article.id } })
    }
  }
}
</script>
<style scoped>
.article_card_box {
  cursor: pointer;
}
</style>
