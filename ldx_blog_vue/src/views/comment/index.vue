<template>
  <div>
    <CommentVue />
    <div v-for="comment in comments" :key="comment.id">
      <comment-item-vue :comment="comment" :article_id="article_id" @fetch-data="fetchData" />
    </div>
    <el-pagination
      :total="total"
      style="text-align: center"
      layout="prev, pager, next"
      :page-size="params.size"
      @current-change="handleChangePage"
      :current-page.sync="params.current"
    ></el-pagination>
  </div>
</template>
<script>
import CommentItemVue from './CommentItem.vue'
import CommentVue from './Comment.vue'
import { VEmojiPicker } from 'v-emoji-picker'
import { mapActions, mapState } from 'pinia'
import { useUserStore } from '@/stores/user'
import { useCommentStore } from '@/stores/comments'

export default {
  name: 'CommentIndex',
  components: {
    CommentItemVue,
    VEmojiPicker,
    CommentVue
  },
  props: {
    article_id: {
      type: Number,
      default: -1
    }
  },
  computed: {
    ...mapState(useUserStore, ['user', 'hasLogin']),
    ...mapState(useCommentStore, ['comments', 'total'])
  },
  data() {
    const userStore = useUserStore()
    const commentStore = useCommentStore()
    const params = {
      size: 5,
      current: 1,
      article_id: this.article_id
    }

    return {
      params,
      userStore,
      commentStore,
      isLoading: false,
      emojiRootVisible: false
    }
  },
  created() {},
  mounted() {
    this.fetchData(this.params)
  },
  methods: {
    handleChangePage(page) {
      this.params.current = page
      this.fetchData(this.params)
    },
    ...mapActions(useCommentStore, ['fetchData', 'submitComment'])
  }
}
</script>
<style scoped>
.user-avatar {
  width: 100px;
  height: 100px;
  border-radius: 50%;
}
</style>
