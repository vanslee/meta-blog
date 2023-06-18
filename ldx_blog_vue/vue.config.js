// vue.config.js
const config = {
  publicPath: '/',
  lintOnSave: false,
  runtimeCompiler: true,
  // 选项
  transpileDependencies: true
}
const flag = process.env.NODE_ENV
console.log("=================================");
console.log('正在使用的配置文件是: ', flag)
console.log("=================================");
if (flag === 'development') {
  config.devServer = {
    client: {
      overlay: false
    },
    proxy: {
      '/apis': {
        changeOrigin: true,
        target: process.env.VUE_APP_BASE_API
      }
    }
  }
}
module.exports = config
