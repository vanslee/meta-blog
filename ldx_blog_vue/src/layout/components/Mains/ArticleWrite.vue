<template>
  <div style="height: 100%; position: relative" @keydown.ctrl.s.prevent="saveArticleContent">
    <v-md-editor
      ref="editorRef"
      v-model="articleContent"
      left-toolbar="table|image|insertLinkTool|insertFileTool|sumbitArticle"
      :toolbar="toolbar"
      :disabled-menus="[]"
      height="1000px"
      :include-level="[1, 2, 3]"
      @upload-image="handleUploadImage"
      @copy-code-success="handleCopyCodeSuccess"
    ></v-md-editor>
    <submit-article-dlg ref="submitArticleRef" />
  </div>
</template>
<script>
import { uploadFilesApi } from '@/apis/qiniu'
import { useClipboard } from '@vueuse/core'
import { Notification } from 'element-ui'
import SubmitArticleDlg from '@/views/dlg/SubmitArticleDlg.vue'
import { debounce, throttle } from 'lodash'
import { getStorage, setStorage } from '@/utils/auth'
export default {
  components: {
    SubmitArticleDlg
  },
  data() {
    const toolbar = {
      sumbitArticle: {
        title: '发布文章',
        icon: 'el-icon-success',
        _this: this,
        action(editor) {
          editor.$nextTick(async () => {
            const { _this } = this
            _this.submitArticle()
          })
        }
      },
      insertFileTool: {
        title: '上传文件',
        icon: 'el-icon-folder',
        _this: this,
        action(editor) {
          editor.$nextTick(async () => {
            const { _this } = this
            const event = await editor.$refs.uploadFile.upload()
            const fileList = event.target.files
            _this.files = []
            Object.keys(fileList).filter(key => {
              _this.files.push(fileList[key])
            })
            _this.handleUploadFile()
          })
        }
      },
      insertLinkTool: {
        title: '插入链接',
        icon: 'el-icon-link',
        action(editor) {
          editor.insert(function (selected) {
            const prefix = '['
            const suffix = '](链接地址)'
            const placeholder = '链接备注'
            const content = selected || placeholder
            return {
              text: `${prefix}${content}${suffix}`,
              selected: content
            }
          })
        }
      }
    }
    return {
      toolbar,
      files: [],
      articleContent: ''
    }
  },

  computed: {
    // article_content() {
    //   return this.store.local
    // }
  },
  watch: {
    articleContent: debounce(function (newValue) {
      // 发送搜索请求，获取搜索结果
      // 这里只是一个示例，具体的实现方式根据业务需求而定
      // this.searchResults = getSearchResults(newValue);
      // console.log(newValue)
      setStorage('content', newValue)
    }, 3000)
  },
  mounted() {
    this.articleContent = getStorage('content')
  },
  methods: {
    /**
     * 上传图片函数
     */
    async handleUploadImage(_, __, files) {
      const formData = new FormData()
      files.forEach(img => {
        formData.append('files', img)
      })
      const { data, code } = await uploadFilesApi(formData)
      if (code === 200) {
        data.forEach(item => {
          this.$refs['editorRef'].text = this.$refs['editorRef'].text.concat(`\n![${item.name}](${item.url})`)
        })
      }
    },
    /**
     * 上传文件函数
     */
    async handleUploadFile() {
      const formData = new FormData()
      this.files.forEach(file => {
        formData.append('files', file)
      })
      const { code, data } = await uploadFilesApi(formData)
      if (code === 200) {
        data.forEach(item => {
          this.$refs['editorRef'].text = this.$refs['editorRef'].text.concat(`\n[${item.name}](${item.url})`)
        })
      }
    },
    handleCopyCodeSuccess(code) {
      const { copy } = useClipboard()
      copy(code)
      Notification.success({
        message: '复制成功',
        description: '代码已复制',
        duration: 2
      })
    },
    saveData() {
      setStorage('content', this.articleContent)
      // setStorage('LITUBAO_article', this.articleContent)
      this.$notify({
        title: '提示',
        message: '内容已保存',
        type: 'success'
      })
    },
    saveArticleContent: throttle(function () {
      this.saveData()
    }, 5000),
    /**
     * 发布文章
     */
    submitArticle() {
      this.$refs['submitArticleRef'].showDlg()
    }
  }
}
</script>
<style scoped></style>
