'use strict'
// const { defineConfig } = require('@vue/cli-service')
console.log(`enviroment: ${process.env.NODE_ENV}`)
module.exports = {
  lintOnSave: false,
  devServer: {
    onListening: ({ server }) => {
      const { host, port } = server
      console.log(`${host}:${port}`)
      console.log(`enviroment: ${process.env.NODE_ENV}`)
    },
    proxy: {
      '/apis': {
        ws: true,
        changeOrigin: true,
        target: process.env.NODE_ENV === 'development' ? 'http://localhost:8000/' : 'https://www.lidengxiang.top/'
      }
    }
  },
  pages: {
    index: {
      entry: 'src/main.js',
      title: '李图报日记'
    }
  },
  transpileDependencies: true
}
