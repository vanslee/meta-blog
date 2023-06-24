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
* {
  margin: 0;
  padding: 0;
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB",
    "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
}

#app {
  /* background-image: url('@/assets/imgs/991.jpg');
  background-size: cover;
  background-position: center;
  height: 100vh; */
  background-color: #F5F5F5;
}

::-webkit-scrollbar {
  border-radius: 10px;
  background: #f7f7f9;
  width: 0.5rem;
  /* 纵向滚动条滑块宽度 */
  height: 0.5rem;
  /* 横向滚动条滑块宽度 */
  scrollbar-width: thin;
}

::-webkit-scrollbar-track-piece {
  /*滚动条背景颜色*/
  background: #f7f7f9;
}

::-webkit-scrollbar-thumb {
  /* 滑块 */
  border-radius: 10px;
  background: #e5e5e5;
}

/* 也可以单独设置横向滚动条和纵向滚动条的背景颜色 */
::-webkit-scrollbar-thumb:vertical {
  /* 纵向滑块 */
  border-radius: 10px;
  background: #e5e5e5;
}

::-webkit-scrollbar-thumb:horizontal {
  /* 横向滑块 */
  border-radius: 10px;
  background: #e5e5e5;
}</style>
