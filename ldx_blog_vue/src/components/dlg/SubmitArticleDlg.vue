<template>
  <el-dialog width="90%" :visible.sync="visible" center title="发布文章">
    <div style="display: flex">
      <div style="width: 50%">
        <div style="display: flex;white-space: nowrap;overflow: hidden;; flex-direction: column; align-items: center">
          <el-image :src="user.avatarImgUrl" style="border-radius: 50%; width: 5rem; height: 5rem" />
          <h3 style="
              white-space: nowrap;
              text-overflow: ellipsis;
            ">
            作者: {{ article.author }}
          </h3>
          <div style="display: flex; align-items: center">
            <span style="white-space: nowrap">标题:</span>
            <el-input type="text" v-model="article.articleTitle" />
          </div>

        </div>
        <!-- <div>
          文章作者:
          <el-input size="mini" type="text" style="width: 30vw" disabled />
        </div> -->
      </div>
      <div style="flex-grow: 1; text-align: center">
        <h1>封面</h1>
        <!-- {{ article.imgUrl }} -->
        <el-upload drag name="file" accept="image/*" :show-file-list="false" :on-success="handleCoverSuccess"
          :action="UPLOAD_SERVER_URL" :headers="{ litubao_authentication: getAccessToken() }">
          <img class="hidden-sm-and-up" v-lazy="article.imgUrl" style="
              width: 9rem;
              height: 9rem;
              object-fit: fill;
              border-radius: 0;
            ">
          <img class="hidden-sm-and-down" v-lazy="article.imgUrl" style="
              width: 20rem;
              height: 10rem;
              object-fit: fill;
              border-radius: 0;
            ">
        </el-upload>
      </div>
    </div>
    <el-divider />
    <h2 style="text-align: center;">文章预览</h2>
    <div style="height: 20vh;width: 100%;overflow-x: hidden;overflow-y: scroll;" v-html="html" />
    <el-divider />
    <!-- <el-card style="" v-html="html" /> -->
    <div style="display: flex; align-items: center; justify-content: center">
      <span>标签:</span>
      <tag-input text="新建" @set-items="setTags" :data="article.tagNames" @rm-item="removeTag" icon="icon-tag-fill" />
    </div>
    <div style="display: flex; align-items: center; justify-content: center">
      <span>分类:</span>
      <tag-input text="新建" icon="icon-pushpin-fill" @set-items="setCategories" @rm-item="removeCategory"
        :data="article.categoryNames" type="danger" />
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button @click="(visible = false), (loading = false)">取消</el-button>
      <el-button type="primary" @click="submit" :loading="loading">发布</el-button>
    </span>
  </el-dialog>
</template>
<script>
import MarkdownIt from 'markdown-it'
import { useUserStore } from '@/stores/user'
import { useArticleStore } from '@/stores/article'
import { mapState, mapActions } from 'pinia'
import TagInput from '@/components/TagInput.vue'
import { getToken } from '@/utils/auth'
import { getAccessToken } from '@/utils/accessToken'
export default {
  components: {
    TagInput
  },
  data() {
    const userStore = useUserStore()
    const article = {
      imgUrl: '',
      tagNames: [],
      categoryNames: [],
      articleTitle: '',
      articleContent: '',
      articleType: '原创',
      userId: userStore.user.id,
      author: userStore.user.username
    }
    return {
      url: '',
      html: '',
      rules: {},
      visible: false,
      article,
      userStore,
      uploadImg: '',
      loading: false,
      formItemLayout: {},
      tagValue: '',
      categoryValue: '',
      tagInputVisible: false,
      categoryInputVisible: false,
      UPLOAD_SERVER_URL: process.env.VUE_APP_UPLOAD_SERVER
    }
  },

  computed: {
    ...mapState(useUserStore, ['user'])
    // 给方法起别名
    // ...mapActions(useArticleStore,{alias:'setTitles'})
  },
  methods: {
    ...mapActions(useArticleStore, ['publishArticle']),
    onFinishFailed() { },
    beforeUpload() { },

    async submit() {
      // 发布文章
      this.loading = true
      const success = await this.publishArticle(this.article)
      this.loading = false
      if (success) {
        this.visible = false
        localStorage.removeItem('content')
        this.$router.push({ name: 'Index' })
      }
    },
    handleCoverSuccess(res) {
      const { data, code } = res
      if (code === 200) {
        this.article.imgUrl = data.url
      }
    },
    showDlg(content) {
      this.article.articleContent = content
      const md = new MarkdownIt()
      this.html = md.render(content)
      this.visible = true
    },
    setCategories(category) {
      this.article.categoryNames.push(category)
    },
    setTags(tag) {
      this.article.tagNames.push(tag)
    },
    removeTag(tag) {
      this.article.tagNames.splice(this.article.tagNames.indexOf(tag), 1)
    },
    removeCategory(category) {
      this.article.categoryNames.splice(
        this.article.categoryNames.indexOf(category),
        1
      )
    },
    getAccessToken
  }
}
</script>
<style scoped>
div {
  margin: 5px;
}

::v-deep .el-upload {
  width: 100%;
  height: 100%;
}

::v-deep .el-upload-dragger {
  width: 100%;
  height: 100%;
}
</style>
