<template>
  <div>
    <el-row type="flex" justify="space-between" :gutter="10">
      <el-col :span="4">
        <el-image src="https://lidengxiang.top/Chat/2023-03-23_21-55-27.PNG"></el-image>
      </el-col>
      <el-col :span="16">
        <el-input type="textarea" :rows="3" placeholder="机会是留给有准备的人" v-model="params.root_content" />
      </el-col>
      <el-col :span="4">
        <el-button style="width: 100%; height: 100%" type="primary" @click="submitComment('root')">发表评论</el-button>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="4">&nbsp;</el-col>
      <el-col :span="20">
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
    </el-row>
    <el-divider />
    <el-row>
      <el-col :span="4">
        <el-image src="https://lidengxiang.top/Chat/2023-03-23_21-55-27.PNG"></el-image>
      </el-col>
      <el-col :span="20">
        <el-row>
          <el-row>
            {{ comment.userNick }}
            <i style="color: red">[作者]</i>
          </el-row>
          <el-row>{{ comment.content }}</el-row>
          <el-row>
            {{ formatTime(comment.createTime) }} 湖南 &emsp; &emsp;
            <i class="el-icon-arrow-up">&nbsp;{{ comment.commentLikes }}</i>
            &nbsp;
            <i class="el-icon-arrow-down">&nbsp;210</i>
            &emsp; &emsp;
            <i @click="showCommmentInput(comment.id, comment.id, comment.userNick)">回复</i>
          </el-row>
        </el-row>
        <el-row
          style="margin-top: 15px"
          type="flex"
          align="middle"
          v-for="children in comment.childrens.records"
          :key="children.id"
        >
          <el-col :span="3">
            <el-image
              :style="{ width: '60px', height: '60px', borderRadius: '50%' }"
              src="https://lidengxiang.top/Chat/2023-03-23_21-55-27.PNG"
            ></el-image>
          </el-col>
          <el-col :span="21">
            <el-row>
              <el-row>
                <i>{{ children.userNick }}</i>
                : {{ children.content }}
              </el-row>
              <el-row>
                {{ formatTime(children.createTime) }} 湖南 &emsp; &emsp;
                <i class="el-icon-arrow-up">&nbsp;{{ children.commentLikes }}</i>
                &nbsp;
                <i class="el-icon-arrow-down">&nbsp;210</i>
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
      :modal="false"
      title="发表评论"
      direction="btt"
      :show-close="false"
      :wrapper-closable="true"
      style="width: 50%; margin: 0 auto"
      :visible.sync="commentInputVisible"
    >
      <el-row>
        <el-input type="textarea" :rows="3" placeholder="机会是留给有准备的人" v-model="params.reply_content" />
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
      <el-button type="primary" style="position: absolute; right: 10px; bottom: 20px" @click="submitComment('reply')">
        发表评论
      </el-button>
    </el-drawer>
  </div>
</template>
<script>
import { VEmojiPicker } from 'v-emoji-picker'
import { formatTime } from '@/utils/time'
export default {
  components: { VEmojiPicker },
  props: {
    comment: {
      type: Object,
      default: () => {}
    },
    article_id: {
      type: String,
      default: '0'
    }
  },
  data() {
    const params = {
      content: '',
      root_content: '',
      reply_content: '',
      article_id: this.article_id,
      user_nick: '李登祥',
      user_id: '112312',
      user_avatar: 'https://lidengxiang.top/user/avatar/495234450/1669648257.jpeg',
      root_comment_id: '',
      reply_comment_id: ''
    }
    return {
      params,
      emoji: '',
      formatTime,
      emojiRootVisible: false,
      emojiReplyVisible: false,
      commentInputVisible: false
    }
  },
  created() {},
  computed: {},
  methods: {
    // closeCommentInput() {
    //   this.commentInputVisible = false
    // },
    selectRootEmoji(emoji) {
      this.emojiRootVisible = false
      this.params.root_content = `${this.params.root_content}${emoji['data']}`
    },
    selectReplyEmoji(emoji) {
      this.emojiReplyVisible = false
      this.params.reply_content = `${this.params.reply_content}${emoji['data']}`
    },
    showCommmentInput(root_comment_id, reply_comment_id, nick) {
      this.params.root_comment_id = root_comment_id
      this.params.reply_comment_id = reply_comment_id
      this.params.reply_content = `@${nick}: `
      this.commentInputVisible = true
    },
    submitComment(flag) {
      if (flag === 'reply') {
        this.params.content = this.params.reply_content
      } else {
        this.params.content = this.params.root_content
      }
    }
  }
}
</script>
<style scoped>
i {
  color: gray;
  cursor: pointer;
  font-size: 0.8rem;
}
</style>
