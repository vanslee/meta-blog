<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
div {
  scrollbar-width: thin;
  scrollbar-color: #888 #f1f1f1;
}

/* 滚动条滑块 */
div::-webkit-scrollbar-thumb {
  background-color: #888;
  border-radius: 10px;
}

/* 鼠标悬停在滚动条时的样式 */
div::-webkit-scrollbar-thumb:hover {
  background-color: #555;
}

html,
body {
  margin: 0;
  padding: 0;
  vertical-align: top;
  height: 100vh;
  overflow: hidden;
}
</style>
