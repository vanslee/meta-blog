<template>
  <div>
    <template v-for="comment in comments">
      <comment-item-vue :comment="comment" :article_id="article_id" :key="comment.id" />
    </template>
  </div>
</template>
<script>
import CommentItemVue from './CommentItem.vue'
import { getCommentBriefApi } from '@/apis/comment'
export default {
  name: 'CommentIndex',
  components: {
    CommentItemVue
  },
  props: {
    article_id: {
      type: String,
      default: '0'
    }
  },
  data() {
    const params = {
      current: 0,
      size: 5,
      article_id: this.article_id
    }
    return {
      params,
      comments: []
    }
  },
  created() {
    this.fetchData()
  },
  computed: {},
  methods: {
    async fetchData() {
      const { data } = await getCommentBriefApi(this.params)
      this.comments = data.records
    }
  }
}
</script>
<style scoped></style>
