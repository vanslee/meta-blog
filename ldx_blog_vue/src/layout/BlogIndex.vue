<template>
  <div id="app">
    <el-container class="app_wrapper">
      <el-header>
        <BlogHeader />
      </el-header>
      <el-container>
        <el-aside width="15%">
          <router-view name="left_aside" />
        </el-aside>
        <el-main style="margin: 0 100px">
          <router-view name="main" />
        </el-main>
        <el-aside width="15%">
          <BlogRightAside />
        </el-aside>
      </el-container>
      <el-footer><BlogFooter /></el-footer>
    </el-container>
  </div>
</template>
<script>
import BlogFooter from '@/layout/BlogFooter.vue'
import BlogHeader from '@/layout/BlogHeader.vue'
import BlogRightAside from '@/layout/BlogRightAside.vue'
export default {
  components: {
    BlogFooter,
    BlogHeader,
    BlogRightAside
  },
  data() {
    return {}
  },
  created() {},
  computed: {},
  methods: {}
}
</script>
<style scoped>
.article_box {
  height: 100%;
}
.pagination {
  display: flex;
  justify-content: center;
}
.scorll-wrapper {
  height: 90vh;
  overflow-y: auto;
  overflow-x: hidden;
}
</style>
