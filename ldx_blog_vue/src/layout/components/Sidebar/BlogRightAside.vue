<template>
  <div>
    <el-divider><i class="el-icon-star-on" /></el-divider>
    <el-card>
      <div
        slot="header"
        class="clearfix"
      >
        <span>网站已运行</span>
      </div>
      {{ time }}
    </el-card>
    <el-divider><i class="el-icon-star-on" /></el-divider>
    <el-card>
      <el-card>
        <div
          slot="header"
          class="clearfix"
        >
          <span>网站数据</span>
        </div>
        <el-row>博客: {{ website.articleCount }}篇</el-row>
        <el-row>评论: {{ website.commentCount }}条</el-row>
        <el-row>标签数: {{ website.categoryCount }}</el-row>
        <el-row>分类数: {{ website.tagCount }}</el-row>
        <el-row>用户人数: {{ website.userCount }}</el-row>
      </el-card>
    </el-card>
  </div>
</template>
<script>
import { getWebSiteInfoApi } from '@/apis/website'
export default {
  data () {
    return {
      time: '',
      website: {
        articleCount: 0,
        commentCount: 0,
        categoryCount: 0,
        tagCount: 0,
        userCount: 0
      }
    }
  },
  created () {
    this.fetchData()
    setInterval(() => {
      this.updateTimer()
    }, 1000)
  },
  computed: {},
  methods: {
    async fetchData () {
      const { data, code } = await getWebSiteInfoApi()
      if (code === 200) {
        this.website = data
      }
    },
    updateTimer () {
      //   const now = new Date()
      //   const startTime = new Date()
      //   // 计算已运行的时间
      const diff = new Date().getTime()
      //   // 将时间间隔转换为天数、小时数、分钟数和秒数
      const days = Math.floor(diff / (1000 * 60 * 60 * 24))
      const hours = Math.floor(
        (diff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
      )
      const minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60))
      const seconds = Math.floor((diff % (1000 * 60)) / 1000)
      //   // 将计时器显示更新为已运行时间
      this.time = ` ${days}天${hours}小时${minutes}分钟${seconds}秒`
    }
  }
}
</script>
<style scoped></style>
