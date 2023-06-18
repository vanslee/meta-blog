<template>
  <div
    style="
      width: 100vw;
      height: 100vh;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
    "
  >
    <header style="height: 10%; width: 100%">
      <BlogHeader />
    </header>
    <main
      style="
        height: 90%;
        width: 100%;
        display: flex;
        justify-content: space-between;
      "
    >
      <aside
        style="width: 30%"
        class="hidden-sm-and-down"
      >
        <router-view name="left_aside" />
      </aside>
      <content style="width: 100%; padding: 0 5vw">
        <router-view name="main" />
      </content>
      <aside
        style="width: 30%"
        class="hidden-sm-and-down"
      >
        <router-view name="right_aside" />
      </aside>
    </main>
    <footer style="width: 100%">
      <BlogFooter />
    </footer>
  </div>
  <!-- <el-container style="height: 100%; width: 100%">
    <el-header height="8vh">
      <BlogHeader />
    </el-header>
    <el-container style="height: 50%">
      <el-aside width="15%" class="hidden-xs-only">
        <router-view name="left_aside" />
      </el-aside>
      <el-main>
        <router-view name="main" />
      </el-main>
      <el-aside width="15%" class="hidden-xs-only">
        <router-view name="right_aside" />
        <BlogRightAside />
      </el-aside>
    </el-container>
    <el-footer height="7vh">112312</el-footer>
  </el-container> -->
</template>
<script>
import BlogFooter from '@/layout/components/Footers/BlogFooter.vue'
import BlogHeader from '@/layout/components/Headers/BlogHeader.vue'
// import BlogRightAside from '@/layout/components/Sidebar/BlogRightAside.vue'
export default {
  name: 'LayoutIndex',
  components: {
    BlogFooter,
    BlogHeader
  },
  data () {
    return {}
  },
  created () {},
  computed: {},
  methods: {}
}
</script>
<style scoped>
* {
  overflow: hidden;
}
</style>
