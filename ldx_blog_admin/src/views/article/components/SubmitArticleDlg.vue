<template>
  <el-dialog width="100%" :visible.sync="visible" center title="发布文章">
    <div style="display: flex">
      <div style="flex-grow: 1">
        <div
          style="
            display: flex;
            white-space: nowrap;
            overflow: hidden;
            flex-direction: column;
            align-items: center;
          "
        >
          <el-image
            :src="article.avatar"
            style="border-radius: 50%; width: 5rem; height: 5rem"
          />
          <h3 style="white-space: nowrap; text-overflow: ellipsis">
            作者: {{ article.username }}
          </h3>
          <div style="display: flex; align-items: center">
            <span style="white-space: nowrap">标题:</span>
            <el-input v-model="article.articleTitle" type="text" />
          </div>
        </div>
        <!-- <div>
          文章作者:
          <el-input size="mini" type="text" style="width: 30vw" disabled />
        </div> -->
      </div>
      <div style="flex-grow: 1; text-align: center">
        <h1>封面</h1>
        <el-upload
          drag
          name="file"
          accept="image/*"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :action="UPLOAD_SERVER_URL"
          :headers="{ litubao_authentication: getAccessToken() }"
        >
          <img
            v-lazy="article.imgUrl"
            class="hidden-sm-and-up"
            style="
              width: 9rem;
              height: 9rem;
              object-fit: fill;
              border-radius: 0;
            "
          />
          <img
            v-lazy="article.imgUrl"
            class="hidden-sm-and-down"
            style="
              width: 20rem;
              height: 10rem;
              object-fit: fill;
              border-radius: 0;
            "
          />
        </el-upload>
      </div>
    </div>
    <el-divider />
    <h2 style="text-align: center">文章预览</h2>
    <div
      style="height: 20vh; width: 100%; overflow-x: hidden; overflow-y: scroll"
      v-html="html"
    />
    <el-divider />
    <!-- <el-card style="" v-html="html" /> -->
    <div style="display: flex; align-items: center; justify-content: center">
      <span>标签:</span>
      <tag-input
        text="新建"
        :data="article.tags"
        icon="icon-tag-fill"
        @set-items="setTags"
        @rm-item="removeTag"
      />
    </div>
    <div style="display: flex; align-items: center; justify-content: center">
      <span>分类:</span>
      <tag-input
        text="新建"
        type="danger"
        icon="icon-pushpin-fill"
        :data="article.categories"
        @set-items="setCategories"
        @rm-item="removeCategory"
      />
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button @click=";(visible = false), (loading = false)">取消</el-button>
      <el-button :loading="loading" type="primary" @click="submit">
        发布
      </el-button>
    </span>
  </el-dialog>
</template>
<script>
  import MarkdownIt from 'markdown-it'
  import { mapState, mapActions, mapMutations } from 'vuex'
  import TagInput from './TagInput.vue'
  import { getAccessToken } from '@/utils/accessToken'
  export default {
    components: {
      TagInput,
    },
    data() {
      return {
        url: '',
        html: '',
        rules: {},
        visible: false,
        uploadImg: '',
        loading: false,
        formItemLayout: {},
        tagValue: '',
        categoryValue: '',
        tagInputVisible: false,
        categoryInputVisible: false,
        UPLOAD_SERVER_URL: process.env.VUE_APP_UPLOAD,
      }
    },

    computed: mapState({
      article: ({ article }) => article.article,
    }),
    methods: {
      ...mapMutations({
        setTags: 'article/setTags',
        removeTag: 'article/removeTag',
        setCategories: 'article/setCategories',
        removeCategory: 'article/removeCategory',
        setArticleContent: 'article/setArticleContent',
      }),
      ...mapActions({
        updateArticle: 'article/updateArticle',
      }),
      onFinishFailed() {},
      beforeUpload() {},

      async submit() {
        // 发布文章
        this.loading = true
        await this.updateArticle(this.article)
        this.loading = false
      },
      handleAvatarSuccess(res) {
        const { data, code } = res
        if (code === 200) {
          this.article.imgUrl = data.url
        }
      },
      showDlg(content) {
        const md = new MarkdownIt()
        this.html = md.render(content)
        this.setArticleContent(content)
        this.visible = true
      },
      getAccessToken,
    },
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
