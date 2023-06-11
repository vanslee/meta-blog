<template>
  <div>
    <el-row type="flex" justify="space-between" align="middle">
      <el-col :span="2" class="hidden-xs-only">
        <el-image v-if="hasLogin" :src="user.avatarImgUrl" class="user-avatar" />
        <el-image
          v-else
          src="https://lidengxiang.top/%E7%94%A8%E6%88%B7%E9%BB%98%E8%AE%A4%E5%A4%B4%E5%83%8F.jpg"
          class="user-avatar"
        ></el-image>
      </el-col>
      <el-col :span="3" class="hidden-sm-and-up">
        <el-image v-if="hasLogin" :src="user.avatarImgUrl" style="width: 12vw" />
        <el-image
          v-else
          src="https://lidengxiang.top/%E7%94%A8%E6%88%B7%E9%BB%98%E8%AE%A4%E5%A4%B4%E5%83%8F.jpg"
          class="user-avatar"
        ></el-image>
      </el-col>
      <el-col :span="17" class="hidden-xs-only">
        <el-input
          type="textarea"
          :rows="3"
          placeholder="机会是留给有准备的人"
          v-model="commentParam.content"
          :disabled="!hasLogin"
        />
      </el-col>
      <el-col :span="16" class="hidden-sm-and-up">
        <el-input placeholder="机会是留给有准备的人" v-model="commentParam.content" :disabled="!hasLogin">
          <el-popover trigger="click" slot="append">
            <VEmojiPicker @select="selectRootEmoji" />
            <el-button slot="reference">
              <i class="el-icon-wind-power"></i>
            </el-button>
          </el-popover>
        </el-input>
      </el-col>
      <el-col :span="4" class="hidden-xs-only">
        <el-button v-if="hasLogin" style="height: 75px" type="primary" @click="submit" :loading="isLoading">
          发表评论
        </el-button>
        <el-button v-else style="height: 75px" type="primary" disabled>请先登录</el-button>
      </el-col>
      <el-col :span="4" class="hidden-sm-and-up">
        <el-button v-if="hasLogin" style="height: 5vh" type="primary" size="mini" @click="submit" :loading="isLoading">
          发送
        </el-button>
        <el-button v-else style="height: 5vh" type="primary" disabled>请先登录</el-button>
      </el-col>
    </el-row>
    <el-row :gutter="10" type="flex" justify="space-between">
      <el-col :span="2">&nbsp;</el-col>
      <el-col :span="17">
        <el-popover trigger="click">
          <VEmojiPicker @select="selectRootEmoji" />
          <el-button slot="reference" class="hidden-xs-only">
            <i class="el-icon-wind-power"></i>
            表情
          </el-button>
        </el-popover>
      </el-col>
      <el-col :span="4">&nbsp;</el-col>
    </el-row>
    <template v-for="comment in comments">
      <comment-item-vue :comment="comment" :article_id="article_id" :key="comment.id" @fetch-data="fetchData" />
    </template>
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
import { VEmojiPicker } from 'v-emoji-picker'
import { mapActions, mapState } from 'pinia'
import { useUserStore } from '@/stores/user'
import { useCommentStore } from '@/stores/comments'

export default {
  name: 'CommentIndex',
  components: {
    CommentItemVue,
    VEmojiPicker
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
    const commentParam = {
      content: '',
      user_id: userStore.user.id,
      article_id: this.article_id,
      user_nick: userStore.user.username,
      user_avatar: userStore.user.avatarImgUrl
    }
    return {
      params,
      userStore,
      commentStore,
      commentParam,
      isLoading: false,
      emojiRootVisible: false
    }
  },
  created() {},
  mounted() {
    this.fetchData(this.params)
  },
  methods: {
    selectRootEmoji(emoji) {
      this.emojiRootVisible = false
      this.commentParam.content = `${this.commentParam.content}${emoji['data']}`
    },
    submit() {
      this.isLoading = true
      const success = this.submitComment(this.commentParam)
      if (success) {
        this.$message.success('发送成功')
        this.fetchData(this.params)
      } else {
        this.$message.error('发送失败')
      }
      this.commentParam.content = ''
      this.isLoading = false
    },
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
