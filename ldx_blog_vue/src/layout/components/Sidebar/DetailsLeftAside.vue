<template>
  <el-card :body-style="{ border: '2px solid  #909399' }">
    <div
      v-for="anchor in store.titles"
      :key="anchor.lineIndex"
      :style="{ padding: `10px 0 10px ${anchor.indent * 20}px` }"
      @click="handleAnchorClick(anchor)"
    >
      <a style="cursor: pointer">{{ anchor.title }}</a>
    </div>
  </el-card>
</template>
<script>
import { useArticleStore } from '@/stores/article'
export default {
  data() {
    return {
      store: useArticleStore()
    }
  },
  created() {},
  mounted() {},
  computed: {},

  methods: {
    handleAnchorClick(anchor) {
      this.$bus.$emit('directory-navigation', anchor)
    }
  }
}
</script>
<style scoped></style>
