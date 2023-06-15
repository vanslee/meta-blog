// webpack.config.js
const path = require('path')
const { VueLoaderPlugin } = require('vue-loader')
const MiniCssExtractPlugin = require('mini-css-extract-plugin')
const HtmlWebpackPlugin = require('html-webpack-plugin')
const CssMinimizerPlugin = require('css-minimizer-webpack-plugin')
/**
 * @type {import('webpack').Configuration}
 */
const config = {
    context: path.resolve(__dirname, 'src'),
    resolve: {
        alias: {
            '@': path.resolve(__dirname, 'src')
        }
    },
    entry: './main.js',
    output: {
        path: path.resolve(__dirname, 'target'),
        filename: 'index.js'
    },
    mode: 'development',
    module: {
        rules: [
            {
                test: /\.vue$/,
                loader: 'vue-loader'
            },
            {
                oneOf: [

                    // 处理css
                    {
                        test: /\.css$/, // 正则匹配需要处理的文件后缀名
                        // loader: 'style-loader' // 使用loader字段时,可以只传入一个loader,而使用use,则需要多个
                        use: [
                            // 'style-loader',
                            MiniCssExtractPlugin.loader,
                            'css-loader',
                            {
                                loader: 'postcss-loader',
                                options: {
                                    postcssOptions: {
                                        plugins: ['postcss-preset-env']
                                    }
                                }
                            }
                        ] // 处理样式资源 style-loader和css-loader缺一不可
                    },
                    {
                        test: /\.scss$/,
                        use: [
                            'vue-style-loader',
                            'css-loader',
                            'sass-loader'
                        ]
                    },
                    {
                        test: /\.sass$/, // postcss识别不了sass语法
                        use: ['style-loader', 'css-loader', 'sass-loader'] // 处理sass需要这三个,缺一不可
                    },
                    {
                        test: /\.(png|jpe?g|gif|svg|webp)$/,
                        type: 'asset/resource',
                        parser: {
                            dataUrlCondition: {
                                maxSize: 200 * 1024
                            }
                        },
                        generator: {
                            filename: 'static/imgs/[hash][ext][query]' // target/static/imgs/....
                        }
                    },
                    {
                        test: /\.(ttf|woff|woff2)$/,
                        type: 'asset/resource',
                        generator: {
                            filename: 'static/fonts/[hash][ext][query]'
                        }
                    },
                    {
                        test: /\.js$/,
                        // exclude: /node_modules/,
                        include: path.resolve(__dirname, './src'), // 只转换src目录下的代码
                        // use: ['babel-loader'],
                        loader: 'babel-loader', // 开启缓存就需要注释use: ['babel-loader'],并且缓存文件在 node_modules/.bin
                        options: {
                            cacheDirectory: true,
                            cacheCompression: false
                        }
                    }
                ]
            }
        ]
    },
    plugins: [
        new VueLoaderPlugin(),
        new MiniCssExtractPlugin({
            filename: 'static/css/index.css'
        }),
        new HtmlWebpackPlugin(),
        new CssMinimizerPlugin(),
    ],
    devtool: 'source-map'
}
config.devServer = {
    port: 8080,
    open: false,
    host: '192.168.97.18',

}
module.exports = config