<template>
  <el-menu :router="true" class="header-menu" :default-active="activeIndex" mode="horizontal" @select="handleSelect">
    <el-menu-item index="/">首页</el-menu-item>
    <el-menu-item index="2">友链</el-menu-item>
    <el-menu-item index="3">文章分类</el-menu-item>
    <el-menu-item index="4">文章标签</el-menu-item>
    <el-menu-item index="5">照片墙</el-menu-item>
    <el-menu-item index="6">捐赠</el-menu-item>
    <el-menu-item index="7" v-if="!user.isLogin" @click="$router.push({ name: 'login' })">登录</el-menu-item>
    <el-submenu v-else index="8">
      <template slot="title">
        <el-image
          style="border-radius: 50%; width: 40px; height: 40px; margin-right: 10px"
          :src="user.avatar"
        ></el-image>
        <span>
          {{ user.name }}
        </span>
      </template>
      <div v-show="user.isLogin">
        <el-menu-item index="/write">
          <i class="el-icon-edit-outline" />
          写博客
        </el-menu-item>
        <el-menu-item index="2-2">
          <i class="el-icon-s-custom" />
          个人中心
        </el-menu-item>
        <el-menu-item @click="logout">
          <i class="el-icon-warning-outline" />
          退出登录
        </el-menu-item>
      </div>
    </el-submenu>
  </el-menu>
</template>
<script>
import { useUserStore } from '@/stores/user'
import { getStorage } from '@/utils/auth'
export default {
  data() {
    const activeIndex = '1'
    return {
      user: getStorage('LITUBAO_user'),
      userStore: {},
      activeIndex
    }
  },
  created() {},
  mounted() {
    this.userStore = useUserStore()
  },
  computed: {},
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath)
    },
    async logout() {
      await this.userStore.logout()
      this.$router.push(`/login?redirect=${this.$route.fullPath}`)
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
