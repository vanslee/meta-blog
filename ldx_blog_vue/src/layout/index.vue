<template>
  <div id="app">
    <el-container class="app_wrapper">
      <el-header>
        <BlogHeader />
      </el-header>
      <el-container>
        <el-aside width="15%" class="hidden-xs-only">
          <router-view name="left_aside" />
        </el-aside>
        <el-main>
          <router-view name="main" />
        </el-main>
        <el-aside width="15%" class="hidden-xs-only">
          <router-view name="right_aside" />
          <!-- <BlogRightAside /> -->
        </el-aside>
      </el-container>
      <el-footer><BlogFooter /></el-footer>
    </el-container>
  </div>
</template>
<script>
import BlogFooter from "@/layout/components/Footers/BlogFooter.vue";
import BlogHeader from "@/layout/components/Headers/BlogHeader.vue";
// import BlogRightAside from '@/layout/components/Sidebar/BlogRightAside.vue'
export default {
  name: "LayoutIndex",
  components: {
    BlogFooter,
    BlogHeader,
  },
  data() {
    return {};
  },
  created() {},
  computed: {},
  methods: {},
};
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
