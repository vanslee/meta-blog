<template>
  <el-menu :router="true" class="header-menu" :default-active="activeIndex" mode="horizontal" @select="handleSelect">
    <el-menu-item index="/">首页</el-menu-item>
    <el-menu-item index="2">友链</el-menu-item>
    <el-menu-item index="3">文章分类</el-menu-item>
    <el-menu-item index="4">文章标签</el-menu-item>
    <el-menu-item index="5">照片墙</el-menu-item>
    <el-menu-item index="6">捐赠</el-menu-item>
    <el-submenu index="6">
      <template slot="title">
        <el-image
          style="border-radius: 50%; width: 40px; height: 40px; margin-right: 10px"
          :src="avatarImgUrl"
        ></el-image>
        <span v-if="tokenInfo.isLogin">
          {{ tokenInfo.tag }}
        </span>
        <span v-else>登录</span>
      </template>
      <div v-show="tokenInfo.isLogin">
        <el-menu-item index="/write">
          <i class="el-icon-edit-outline" />
          写博客
        </el-menu-item>
        <el-menu-item index="2-2">
          <i class="el-icon-s-custom" />
          个人中心
        </el-menu-item>
        <el-menu-item index="2-3">
          <i class="el-icon-warning-outline" />
          退出登录
        </el-menu-item>
      </div>
    </el-submenu>
  </el-menu>
</template>
<script>
import { getStorage } from '@/utils/storage'
import { userInfoApi } from '@/apis/user'
export default {
  data() {
    return {
      user: {},
      activeIndex: '1',
      activeIndex2: '1',
      avatarImgUrl:
        'https://th.bing.com/th/id/R.0dee2228031e4ef5b03d0c5734aef866?rik=BD%2bnjbFbllVmEQ&riu=http%3a%2f%2fimg.zcool.cn%2fcommunity%2f01cf02554336f10000019ae9df1dad.jpg%403000w_1l_2o_100sh.jpg&ehk=zvcYgjHlqK2U2x9ploUbmiBIk7BewUd6lyA0AIswegQ%3d&risl=&pid=ImgRaw&r=0',
      tokenInfo: getStorage('LITUBAO_AUTHENTICATION')
    }
  },
  created() {
    this.fetchData()
  },
  computed: {},
  methods: {
    async fetchData() {
      const { data } = await userInfoApi(this.tokenInfo['loginId'])
      this.user = data
      this.avatarImgUrl = `${process.env.VUE_APP_WEBSITE_CDN}${this.user.avatarImgUrl} `
    },
    handleSelect(key, keyPath) {
      console.log(key, keyPath)
    }
  }
}
</script>
<style scoped>
.header-menu {
  display: flex;
  justify-content: space-between;
}
</style>
