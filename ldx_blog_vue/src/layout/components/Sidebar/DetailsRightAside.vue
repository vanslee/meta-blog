<template>
  <div>
    <el-card class="right-top right-box">
      <el-row>
        <el-col><i class="iconfont" style="font-size: 2.5rem">&#xe608;</i></el-col>
        <el-col>目录</el-col>
      </el-row>
    </el-card>
    <el-card class="right-middle right-box">
      <el-row style="margin-top: 20px">
        <el-col>
          <i class="iconfont">&#xe651;</i>
        </el-col>
        <el-col>1</el-col>
      </el-row>
      <el-row style="margin-top: 20px">
        <el-col>
          <i class="iconfont">&#xe610;</i>
        </el-col>
        <el-col>1</el-col>
      </el-row>

      <el-row style="margin-top: 20px">
        <el-col><i class="iconfont" @click="scrollToEnd">&#xe744;</i></el-col>
        <el-col>1</el-col>
      </el-row>
      <el-row style="margin-top: 20px">
        <el-col><i class="iconfont">&#xe718;</i></el-col>
        <el-col>1</el-col>
      </el-row>
    </el-card>
    <el-card class="right-bottom right-box">
      <i class="iconfont">&#xe6c6;</i>
    </el-card>
  </div>
</template>
<script>
export default {
  data() {
    return {}
  },
  created() {},
  computed: {},
  methods: {
    scrollToEnd() {
      this.$bus.$emit('scroll-to-end')
    }
  }
}
</script>
<style scoped>
i {
  cursor: pointer;
}
.right-box {
  position: relative;
  left: 50px;
  width: 100px;
  text-align: center;
}
.right-top {
  top: 200px;
}
.right-middle {
  top: 240px;
}
.right-bottom {
  top: 280px;
}
.iconfont {
  font-size: 2rem;
}
</style>
