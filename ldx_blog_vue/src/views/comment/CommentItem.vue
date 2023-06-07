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
        @click="submit"
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
import { mapState, mapActions } from 'pinia'
import { useCommentStore } from '@/stores/comments'
import { isLogin } from '@/utils/auth'
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
  computed: {
    ...mapState(useUserStore, ['user', 'hasLogin'])
  },
  data() {
    const userStore = useUserStore()
    const params = {
      content: '',
      root_comment_id: '',
      reply_comment_id: '',
      user_id: userStore.user.id,
      article_id: this.article_id,
      user_nick: userStore.user.username,
      user_avatar: userStore.user.avatarImgUrl
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
  created() {},
  mounted() {
    this.userStore = useUserStore()
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
    submit() {
      this.isLoading = true
      const success = this.submitComment(this.params)
      if (success) {
        this.$message.success('发送成功')
        this.fetchData()
      } else {
        this.$message.error('发送失败')
      }
      this.commentParam.content = ''
      this.isLoading = false
    },
    ...mapActions(useCommentStore, ['fetchData', 'submitComment'])
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
