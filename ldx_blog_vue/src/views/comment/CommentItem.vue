<template>
  <div>
    <el-divider />
    <el-row>
      <el-col :span="2">
        <el-image :src="`${cdn}${comment.userAvatar}`" class="user-avatar" />
      </el-col>
      <el-col :span="20">
        <el-row>
          <el-row>
            {{ comment.userNick }}
            <i style="color: red">[作者]</i>
          </el-row>
          <el-row>{{ comment.content }}</el-row>
          <el-row>
            {{ formatTime(comment.createTime) }} {{ comment.location }} &emsp; &emsp;
            <i class="iconfont">&#xe717;</i>
            {{ comment.likes }}
            &nbsp;
            <i class="iconfont">&#xe716;</i>
            &emsp; &emsp;
            <i @click="showCommmentInput(comment.id, comment.id, comment.userNick)">回复</i>
          </el-row>
        </el-row>
        <el-row
          style="margin-top: 15px"
          type="flex"
          align="middle"
          v-for="children in comment.childrens"
          :key="children.id"
        >
          <el-col :span="2">
            <el-image
              :style="{ width: '60px', height: '60px', borderRadius: '50%' }"
              :src="`${cdn}${children.userAvatar}`"
            />
          </el-col>
          <el-col :span="22">
            <el-row>
              <el-row>
                <i>{{ children.userNick }}</i>
                回复 {{ children.content }}
              </el-row>
              <el-row>
                {{ formatTime(children.createTime) }} {{ children.location }} &emsp; &emsp;
                <i class="iconfont">&#xe717;</i>
                {{ children.likes }}
                &nbsp;
                <i class="iconfont">&#xe716;</i>
                &emsp; &emsp;
                <i @click="showCommmentInput(comment.id, children.id, children.userNick)">回复</i>
              </el-row>
            </el-row>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
    <el-drawer
      size="30%"
      title="发表评论"
      direction="btt"
      :show-close="false"
      style="width: 50%; margin: 0 auto"
      :visible.sync="commentInputVisible"
    >
      <el-row>
        <el-input type="textarea" :rows="3" placeholder="机会是留给有准备的人" v-model="params.content" />
      </el-row>
      <el-tooltip placement="top" v-model="emojiReplyVisible">
        <div slot="content">
          <VEmojiPicker @select="selectReplyEmoji" />
        </div>
        <el-button size="mini">
          <i class="el-icon-wind-power"></i>
          表情
        </el-button>
      </el-tooltip>
      <el-button
        type="primary"
        :loading="isLoading"
        @click="submitComment('reply')"
        style="position: absolute; right: 10px; bottom: 20px"
      >
        发表评论
      </el-button>
    </el-drawer>
  </div>
</template>
<script>
import { VEmojiPicker } from 'v-emoji-picker'
import { formatTime } from '@/utils/time'
import { useUserStore } from '@/stores/user'
import { getUserInfo, isLogin } from '@/utils/auth'
import { publishCommentApi } from '@/apis/comment'
export default {
  components: { VEmojiPicker },
  props: {
    comment: {
      type: Object,
      default: () => {}
    },
    article_id: {
      type: Number,
      default: -1
    }
  },
  data() {
    const params = {
      content: '',
      user_nick: '',
      user_id: '',
      user_avatar: '',
      root_comment_id: '',
      reply_comment_id: '',
      article_id: this.article_id
    }
    return {
      params,
      emoji: '',
      formatTime,
      userStore: {},
      isLoading: false,
      emojiRootVisible: false,
      emojiReplyVisible: false,
      commentInputVisible: false,
      cdn: process.env.VUE_APP_WEBSITE_CDN
    }
  },
  created() {
    if (isLogin()) {
      const user = getUserInfo()
      this.params.user_id = user.id
      this.params.user_nick = user.username
      this.params.user_avatar = user.avatarImgUrl.replace(process.env.VUE_APP_WEBSITE_CDN, '')
      // this.params.user_avatar = user.
    }
  },
  mounted() {
    this.userStore = useUserStore()
  },

  computed: {
    isLogin() {
      return this.userStore.isLogin
    }
  },
  methods: {
    selectReplyEmoji(emoji) {
      this.emojiReplyVisible = false
      this.params.content = `${this.params.content}${emoji['data']}`
    },
    showCommmentInput(root_comment_id, reply_comment_id, nick) {
      if (isLogin()) {
        this.params.root_comment_id = root_comment_id
        this.params.reply_comment_id = reply_comment_id
        this.params.content = `@${nick}: `
        this.commentInputVisible = true
      } else {
        this.$message.error('请先登录')
      }
    },
    async submitComment() {
      this.isLoading = true
      const { code } = await publishCommentApi(this.params)
      if (code === 200) {
        this.$message.success('发送成功')
        this.$emit('fetch-data')
      } else {
        this.$message.error('发送失败')
      }
      this.params.content = ''
      this.isLoading = false
    }
  }
}
</script>
<style scoped>
i {
  cursor: pointer;
}
.user-avatar {
  width: 80px;
  height: 80px;
  border-radius: 50%;
}
</style>
