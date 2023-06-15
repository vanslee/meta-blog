// webpack.config.js
const path = require('path')
const { VueLoaderPlugin } = require('vue-loader')
const StyleLintPlugin = require('stylelint-webpack-plugin');
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
            //eslint
            // {
            //     enforce: 'pre',
            //     test: /\.(js|vue)$/,
            //     loader: 'eslint-loader',
            //     exclude: /node_modules/
            // },
            // vue规则
            {
                test: /\.vue$/,
                loader: 'vue-loader'
            },
            // 处理js
            {
                test: /\.js$/,
                loader: 'babel-loader',
                exclude: file => (
                    /node_modules/.test(file) &&
                    !/\.vue\.js/.test(file)
                )
            },
            // 处理css
            {
                test: /\.css$/,
                use: [
                    'vue-style-loader',
                    {
                        loader: 'css-loader',
                        options: { importLoaders: 1 }
                    },
                ]
            }
        ]
    },
    plugins: [
        // vue-loader
        new VueLoaderPlugin(),
        new StyleLintPlugin({
            files: ['**/*.{vue,htm,html,css,sss,less,scss,sass}'],
        })
    ],
}
config.devServer = {
    port: 8080,
    open: false,
    host: '192.168.97.18',

}
module.exports = config