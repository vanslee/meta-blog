// vue.config.js
const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
  publicPath: '/api',
  outputDir: 'target',
  assetsDir: 'resources',
  indexPath: 'index.html',
  filenameHashing: true,
  pages: {
    index: {
      entry: './src/main.js',
      template: 'target/index.html',
      filename: 'index.html',
      title: '李图报日记',
      
    }
  }
  // 选项
})
const version = process.env.NODE_ENV
if (version === 'development') {
  config.devServer = {

  }
}
export default config