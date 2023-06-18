// vue.config.js
const path = require('path')
const { defineConfig } = require('@vue/cli-service')
function resolve (dir) {
  return path.join(__dirname, dir)// path.join(__dirname)设置绝对路径
}

module.exports = defineConfig({
  publicPath: '/',
  outputDir: 'target',
  assetsDir: 'resources',
  lintOnSave: false,
  runtimeCompiler: true,
  // 选项
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
  },
  chainWebpack: config => {
    config.resolve.alias.set('@', resolve('./src'))
  },

  transpileDependencies: true
})
