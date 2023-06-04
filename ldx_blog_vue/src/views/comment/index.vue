<template>
  <div>
    <el-row type="flex" justify="space-between" align="middle">
      <el-col :span="2">
        <el-image v-if="isLogin()" :src="`${cdn}${commentParam.user_avatar}`" class="user-avatar" />
        <el-image
          v-else
          src="https://lidengxiang.top/%E7%94%A8%E6%88%B7%E9%BB%98%E8%AE%A4%E5%A4%B4%E5%83%8F.jpg"
          class="user-avatar"
        ></el-image>
      </el-col>
      <el-col :span="17">
        <el-input
          type="textarea"
          :rows="3"
          placeholder="机会是留给有准备的人"
          v-model="commentParam.content"
          :disabled="!isLogin()"
        />
      </el-col>
      <el-col :span="4">
        <el-button v-if="isLogin()" style="height: 75px" type="primary" @click="submitComment" :loading="isLoading">
          发表评论
        </el-button>
        <el-button v-else style="height: 75px" type="primary" disabled>请先登录</el-button>
      </el-col>
    </el-row>
    <el-row :gutter="10" type="flex" justify="space-between">
      <el-col :span="2">&nbsp;</el-col>
      <el-col :span="17">
        <el-tooltip v-model="emojiRootVisible">
          <div slot="content">
            <VEmojiPicker @select="selectRootEmoji" />
          </div>
          <el-button size="mini">
            <i class="el-icon-wind-power"></i>
            表情
          </el-button>
        </el-tooltip>
      </el-col>
      <el-col :span="4">&nbsp;</el-col>
    </el-row>
    <template v-for="comment in comments">
      <comment-item-vue :comment="comment" :article_id="article_id" :key="comment.id" @fetch-data="fetchData" />
    </template>
  </div>
</template>
<script>
import CommentItemVue from './CommentItem.vue'
import { getCommentBriefApi } from '@/apis/comment'
import { getUserInfo, isLogin } from '@/utils/auth'
import { VEmojiPicker } from 'v-emoji-picker'
import { publishCommentApi } from '@/apis/comment'
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
  data() {
    const params = {
      current: 1,
      size: 5,
      article_id: this.article_id
    }
    const commentParam = {
      content: '',
      user_id: '',
      user_nick: '',
      article_id: this.article_id,
      user_avatar: ''
    }
    return {
      params,
      user: {},
      comments: [],
      commentParam,
      isLoading: false,
      emojiRootVisible: false,
      cdn: process.env.VUE_APP_WEBSITE_CDN
    }
  },
  created() {
    if (isLogin()) {
      const user = getUserInfo()
      this.commentParam.user_id = user.id
      this.commentParam.user_nick = user.username
      this.commentParam.user_avatar = user.avatarImgUrl.replace(process.env.VUE_APP_WEBSITE_CDN, '')
    }
  },
  mounted() {
    this.fetchData()
  },
  methods: {
    selectRootEmoji(emoji) {
      this.emojiRootVisible = false
      this.commentParam.content = `${this.commentParam.content}${emoji['data']}`
    },
    async submitComment() {
      this.isLoading = true
      const { code } = await publishCommentApi(this.commentParam)
      if (code === 200) {
        this.$message.success('发送成功')
        this.fetchData()
      } else {
        this.$message.error('发送失败')
      }
      this.commentParam.content = ''
      this.isLoading = false
    },
    async fetchData() {
      const { data } = await getCommentBriefApi(this.params)
      this.comments = data
    },
    isLogin: isLogin
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
