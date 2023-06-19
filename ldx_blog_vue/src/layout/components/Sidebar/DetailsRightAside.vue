<template>
  <div div="height: 100%">
    <el-card class="right-box">
      <el-row>
        <el-col>
          <i
            class="iconfont"
            style="font-size: 2.5rem"
          >&#xe608;</i>
        </el-col>
        <el-col>目录</el-col>
      </el-row>
    </el-card>
    <el-card class="right-box">
      <el-row style="margin-top: 1vh">
        <el-col>
          <i class="iconfont">&#xe651;</i>
        </el-col>
        <el-col>1</el-col>
      </el-row>
      <el-row style="margin-top: 1vh">
        <el-col>
          <i class="iconfont">&#xe610;</i>
        </el-col>
        <el-col>1</el-col>
      </el-row>

      <el-row style="margin-top: 1vh">
        <el-col>
          <i
            class="iconfont"
            @click="scrollToEnd"
          >&#xe744;</i>
        </el-col>
        <el-col>1</el-col>
      </el-row>
      <el-row style="margin-top: 1vh">
        <el-col><i class="iconfont">&#xe718;</i></el-col>
        <el-col>1</el-col>
      </el-row>
    </el-card>
    <el-card class="right-box">
      <i class="iconfont">&#xe6c6;</i>
    </el-card>
  </div>
</template>
<script>
export default {
  data () {
    return {}
  },
  created () {},
  computed: {},
  methods: {
    scrollToEnd () {
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
  float: right;
  width: 100px;
  text-align: center;
  margin: 1vw 1vw;
}
.iconfont {
  font-size: 2rem;
}
</style>
