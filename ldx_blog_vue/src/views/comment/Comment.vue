<template>
  <div style="display: flex; justify-content: space-between; align-items: center">
    <div class="hidden-xs-only">
      <img v-if="hasLogin" v-lazy="user.avatarImgUrl" style="width: 6vw" />
      <img v-else src="https://lidengxiang.top/default.jpg" style="width: 6vw" />
    </div>
    <div style="flex-grow: 8; flex-shrink: 1; padding: 0 2vw">
      <el-input
        type="textarea"
        placeholder="机会是留给有准备的人"
        v-model="commentParam.content"
        :disabled="!hasLogin"
      />
    </div>
    <div style="flex-grow: 1; flex-shrink: 1">
      <el-button v-if="hasLogin" @click="submit" :loading="isLoading" style="height: 10vh">发表评论</el-button>
      <el-button v-else style="height: 10vh" disabled>请先登录</el-button>
    </div>

    <!-- <el-row type="flex" justify="space-between" align="middle">
 
      <el-col :span="3" class="hidden-sm-and-up">
        <img v-if="hasLogin" v-lazy="user.avatarImgUrl" style="width: 12vw" />
        <el-image v-else src="https://lidengxiang.top/default.jpg" style="width: 12vw; border-radius: 50%" />
      </el-col>
      <el-col :span="17" class="hidden-xs-only">
        
      </el-col>
      <el-col :span="15" class="hidden-sm-and-up">
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
      <el-col :span="5" class="hidden-sm-and-up">
        <el-button v-if="hasLogin" style="height: 5vh" type="primary" size="mini" @click="submit" :loading="isLoading">
          发送
        </el-button>
        <el-button v-else style="height: 5vh" type="primary" disabled>登录</el-button>
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
    </el-row> -->
  </div>
</template>
<script>
import { mapState } from 'pinia'
import { useUserStore } from '@/stores/user'
export default {
  data() {
    const userStore = useUserStore()
    const commentParam = {
      content: '',
      user_id: userStore.user.id,
      article_id: this.article_id,
      user_nick: userStore.user.username,
      user_avatar: userStore.user.avatarImgUrl
    }
    return {
      commentParam,
      isLoading: false
    }
  },
  created() {},
  computed: {
    ...mapState(useUserStore, ['user', 'hasLogin'])
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
    }
  }
}
</script>
<style scoped>
::v-deep .el-textarea__inner {
  height: 10vh;
}
</style>
