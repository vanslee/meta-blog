<template>
  <el-dialog :visible.sync="visible" center title="发布文章">
    <el-row :gutter="20" type="flex" justify="space-between">
      <el-col :span="16" class="article_card_box">
        <el-row>
          <el-col>
            <h1>
              文章标题: &nbsp;
              <el-input style="width: 50%" v-model="articleVO.articleTitle" placeholder="请输入文章标题" />
            </h1>
          </el-col>
          <el-col>
            <el-radio-group v-model="articleVO.articleType">
              <el-radio label="原创" value="原创">原创:</el-radio>
              <el-radio label="转载" value="转载">转载:</el-radio>
            </el-radio-group>
            <div v-show="articleVO.articleType === '转载'">
              <div style="margin: 20px 0">
                原文作者:
                <el-input v-model="articleVO.author" style="width: 15vw" size="mini" />
              </div>
              <div>
                原文链接:
                <el-input v-model="articleVO.articleUrl" style="width: 15vw" size="mini" />
              </div>
            </div>

            <el-divider />
          </el-col>
          <el-col :span="6">
            <el-image :src="user.avatarImgUrl" style="border-radius: 50%; width: 60px; height: 60px" />
          </el-col>
          <el-col :span="10">
            <h1>作者: {{ articleVO.author }}</h1>
          </el-col>
          <el-col>
            <v-md-preview :text="articleVO.articleContent" />
          </el-col>
          <el-col>
            <el-tag
              v-for="(tag, index) in articleVO.tags"
              :key="tag + index"
              closable
              style="margin: 5px"
              @close="removeItem('tag', tag)"
            >
              <i class="el-icon-collection-tag" />
              {{ tag }}
            </el-tag>
            <el-input
              v-show="tagInputVisible"
              ref="tagInputRef"
              v-model="tagValue"
              type="text"
              style="width: 100px"
              @keyup.enter="handleInputConfirm('tag')"
              @blur="handleInputConfirm('tag')"
            />
            <el-tag
              v-show="!tagInputVisible"
              style="background: #fff; border-style: dashed; cursor: pointer"
              @click="showInput('tag')"
            >
              新建标签
              <i class="el-icon-collection-tag" />
            </el-tag>
            <el-divider />
          </el-col>
          <el-col>
            <el-tag
              v-for="(category, index) in articleVO.categories"
              :key="category + index"
              class="mx-1"
              style="margin: 5px"
              closable
              type="danger"
              @close="removeItem('category', category)"
            >
              <i class="el-icon-s-management"></i>
              {{ category }}
            </el-tag>
            <el-input
              v-show="categoryInputVisible"
              ref="categoryInputRef"
              v-model="categoryValue"
              type="text"
              style="width: 100px"
              @keyup.enter="handleInputConfirm('category')"
              @blur="handleInputConfirm('category')"
            />
            <el-tag
              type="danger"
              v-show="!categoryInputVisible"
              style="background: #fff; border-style: dashed; cursor: pointer"
              @click="showInput('category')"
            >
              新建分类
              <i class="el-icon-s-management" />
            </el-tag>
            <el-divider />
          </el-col>
        </el-row>
      </el-col>
      <el-col :span="8" style="text-align: center">
        <h2>文章封面</h2>
        <el-upload
          drag
          name="file"
          accept="image/*"
          class="avatar-uploader"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :action="UPLOAD_SERVER_URL"
        >
          <img v-if="articleVO.imgUrl" :src="articleVO.imgUrl" class="avatar" />
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-col>
    </el-row>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="submit" :loading="loading">发布</el-button>
    </span>
  </el-dialog>
</template>
<script>
import { getStorage, getUserInfo, removeStorage } from '@/utils/auth'
import { publishArticleApi } from '@/apis/article'
export default {
  components: {},
  data() {
    const user = getUserInfo()
    const articleVO = {
      imgUrl: '',
      tags: [],
      categories: [],
      userId: user.id,
      articleTitle: '',
      articleContent: '',
      articleType: '原创',
      author: user.username
    }
    return {
      url: '',
      user,
      rules: {},
      visible: false,
      articleVO,
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
  created() {},
  computed: {},
  methods: {
    onFinishFailed() {},
    beforeUpload() {},
    showInput(flag) {
      if (flag === 'category') {
        this.categoryInputVisible = true
        this.$nextTick(() => {
          this.$refs['categoryInputRef'].focus()
          // this.$refs['categoryInputRef'].input.focus()
        })
      }
      if (flag === 'tag') {
        this.tagInputVisible = true
        this.$nextTick(() => {
          this.$refs['tagInputRef'].focus()
        })
      }
    },
    async submit() {
      this.loading = true
      const { code } = await publishArticleApi(this.articleVO)
      if (code === 200) {
        removeStorage('content')
        this.visible = false
        this.articleVO = {}
      }
      this.loading = false
    },
    handleAvatarSuccess(res) {
      const { data, code } = res
      if (code === 200) {
        this.articleVO.imgUrl = data['url']
      }
    },
    showDlg() {
      this.articleVO.articleContent = getStorage('content')
      this.visible = true
    },
    handleInputConfirm(flag) {
      if (flag == 'category') {
        if (this.categoryValue) {
          if (!Array.isArray(this.articleVO.categories)) {
            this.articleVO.categories = []
          }
          this.articleVO.categories.push(this.categoryValue)
        }
        this.categoryValue = ''
        this.categoryInputVisible = false
      }
      if (flag == 'tag') {
        if (this.tagValue) {
          if (!Array.isArray(this.articleVO.tags)) {
            this.articleVO.tags = []
          }
          this.articleVO.tags.push(this.tagValue)
        }
        this.tagValue = ''
        this.tagInputVisible = false
      }
      return
    },
    removeItem(flag, value) {
      if (flag === 'category') {
        this.articleVO.categories.splice(this.articleVO.categories.indexOf(value), 1)
      }
      if (flag === 'tag') {
        this.articleVO.tags.splice(this.articleVO.tags.indexOf(value), 1)
      }
      return
    }
  }
}
</script>
<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.el-upload-dragger {
  width: 15vw;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
