// webpack.config.js
const path = require('path')
/**
 * 加载配置文件 env
 */
const dotenv = require('dotenv')
const { VueLoaderPlugin } = require('vue-loader')
const MiniCssExtractPlugin = require('mini-css-extract-plugin')
const HtmlWebpackPlugin = require('html-webpack-plugin')
const { CleanWebpackPlugin } = require('clean-webpack-plugin')
const CssMinimizerPlugin = require('css-minimizer-webpack-plugin')
const { ProvidePlugin, DefinePlugin } = require('webpack')
/**
 * @type {import('webpack').Configuration}
 */
const envFile = `.env.${process.env.NODE_ENV}`
const envFilePath = path.resolve(process.cwd(), envFile)
dotenv.config({ path: envFilePath })
const config = {
  name: '李图报日记',
  //   context: path.resolve(__dirname, 'src'),
  resolve: {
    alias: {
      '@': path.resolve(__dirname, 'src')
    }
  },
  entry: './src/main.js',
  output: {
    path: path.resolve(__dirname, 'target'),
    filename: 'index.js'
  },
  module: {
    rules: [
      {
        test: /\.vue$/,
        loader: 'vue-loader'
      },
      {
        test: /\.scss$/,
        use: ['vue-style-loader', 'css-loader', 'sass-loader']
      },
      {
        test: /\.css$/,
        use: ['style-loader', 'css-loader']
      },
      {
        test: /\.(png|jpe?g|gif|svg)$/i,
        loader: 'url-loader',
        options: {
          limit: 8192,
          name: '[name].[ext]',
          outputPath: 'images'
        }
      },
      {
        test: /\.(woff|woff2|eot|ttf|otf)$/i,
        type: 'asset/resource',
        generator: {
          filename: 'fonts/[hash][ext][query]'
        }
      }
    ]
  },
  plugins: [
    new VueLoaderPlugin(),
    new MiniCssExtractPlugin({
      filename: 'static/css/index.css'
    }),
    new HtmlWebpackPlugin({ title: '李图报日记', template: path.resolve(__dirname, './public/index.html') }),
    new CssMinimizerPlugin(),
    new CleanWebpackPlugin(),
    new DefinePlugin({
      'process.env': JSON.stringify(process.env)
    })
  ]
}
const ENVIROMENT = process.env.NODE_ENV
config.mode = ENVIROMENT
console.log('ENVIROMENT', process.env.VUE_APP_WEBSITE_CDN)
if (ENVIROMENT === 'development') {
  config.devServer = {
    port: 8080,
    open: false,
    host: '0.0.0.0',
    proxy: {
      '/apis': 'http://localhost:8000'
    }
  }
  config.devtool = 'eval-source-map'
} else {
  config.devtool = 'nosources-source-map'
}

module.exports = config
