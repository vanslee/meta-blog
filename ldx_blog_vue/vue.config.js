'use strict'
// const { defineConfig } = require('@vue/cli-service')
module.exports = {
  lintOnSave: false,
  // publicPath: process.env.NODE_ENV === 'production'? ''
  devServer: {
    proxy: {
      '/apis': {
        ws: true,
        changeOrigin: true,
        target: 'http://localhost:8000/'
      }
    }
  },
  transpileDependencies: true
}
