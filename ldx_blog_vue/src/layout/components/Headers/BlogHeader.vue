<template>
  <div style="height: 100%; width: 100%">
    <el-menu :router="false" class="header-menu" :default-active="activeIndex" mode="horizontal">
      <el-menu-item index="1" @click="$router.push({ name: 'Index' })">首页</el-menu-item>
      <el-menu-item index="2" class="hidden-xs-only">友链</el-menu-item>
      <el-menu-item index="3">文章分类</el-menu-item>
      <el-menu-item index="4">文章标签</el-menu-item>
      <el-menu-item index="5" class="hidden-xs-only">照片墙</el-menu-item>
      <el-menu-item index="6" class="hidden-xs-only">捐赠</el-menu-item>
      <el-menu-item index="7" v-if="!userStore.hasLogin" @click="$router.push({ name: 'Login' })">登录</el-menu-item>
      <el-submenu v-else index="8" style="margin-left: 1vw">
        <template slot="title">
          <el-image
            class="hidden-xs-only"
            style="border-radius: 50%; width: 40px; height: 40px; margin-right: 10px"
            :src="user.avatarImgUrl"
          ></el-image>
          <el-image
            class="hidden-sm-and-up"
            style="border-radius: 50%; width: 8vw; height: 8vw; border: 1px solid red"
            :src="user.avatarImgUrl"
          ></el-image>
          <span class="hidden-xs-only">
            {{ user.username }}
          </span>
          <span class="hidden-sm-and-up" style="font-size: 0.75rem">
            {{ user.username }}
          </span>
        </template>
        <div v-show="userStore.hasLogin">
          <el-menu-item @click="$router.push({ name: 'Write' })">
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
  </div>
</template>
<script>
// import { logoutApi } from '@/apis/user'
import { useUserStore } from '@/stores/user'
import { mapState } from 'pinia'
export default {
  data() {
    const activeIndex = '1'
    const userStore = useUserStore()
    return {
      userStore,
      activeIndex
    }
  },
  created() {},
  computed: {
    ...mapState(useUserStore, ['user'])
  },
  methods: {
    logout() {
      const sucess = this.userStore.logout()
      if (sucess) {
        this.$router.push(`/login?redirect=${this.$route.fullPath}`)
      }
    }
  }
}
</script>
<style>
/* el-submenu__icon-arrow el-icon-arrow-down */

.header-menu {
  display: flex;
  justify-content: space-between;
}
</style>
