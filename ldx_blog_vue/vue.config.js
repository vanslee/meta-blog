// vue.config.js
const { defineConfig } = require('@vue/cli-service')
console.log('当前使用的配置文件: ', process.env.VUE_APP_ENVIROMENT)
module.exports = defineConfig({
  devServer: {
    client: {
      overlay: false
    },
    proxy: {
      '/apis': {
        changeOrigin: true,
        target: 'http://localhost:8000'
      }
    }
  }
  // 选项
})
