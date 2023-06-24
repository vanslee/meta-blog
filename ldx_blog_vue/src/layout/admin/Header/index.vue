<template>
    <div
        style="display: flex;justify-content: space-between;height: 100%; width: 100%;background-color: #ffffff;align-items: center;">
        <i class="el-icon-s-unfold" style="cursor: pointer;padding-left: 10px;" @click="changeCollapse" />
        <el-dropdown trigger="click" style="padding-right: 10px;">
            <span class="el-dropdown-link" style="display: flex;align-items: center;">
                <img v-lazy="src" style="margin-right: 10px;" />
                lidengxiang<i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>
                    <router-link to="/">
                        首页
                    </router-link>
                </el-dropdown-item>
                <el-dropdown-item>写博客</el-dropdown-item>
                <el-dropdown-item>退出登录</el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
    </div>
</template>
<script>
import { useSettingStore } from '@/stores/settings'
import { mapActions } from 'pinia'
export default {
    data() {
        return {
            src: "https://lidengxiang.top/blog/img/1190797313/favicon-32x32.png"
        }
    },
    created() { },
    computed: {
    },
    methods: {
        ...mapActions(useSettingStore, ['changeCollapse']),
        logout() {
            const sucess = this.userStore.logout()
            if (sucess) {
                this.$router.push(`/login?redirect=${this.$route.fullPath}`)
            }
        },
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
  