import Vue from 'vue'
import App from './App.vue'
import '@/router/permission'
import pinia from '@/stores'
import router from './router'
import 'default-passive-events'
import { PiniaVuePlugin } from 'pinia'
import '@/assets/iconfont/iconfont.css'
import ElementUI, { Table } from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import 'element-ui/lib/theme-chalk/display.css'
import VueLazyload from 'vue-lazyload'

/**
 * v-md-editor
 */
// --------------------------------
import VMdEditor from '@kangc/v-md-editor'
import '@kangc/v-md-editor/lib/style/base-editor.css'
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js'
import '@kangc/v-md-editor/lib/theme/style/github.css'
import hljs from 'highlight.js'
import createCopyCodePlugin from '@kangc/v-md-editor/lib/plugins/copy-code/index'
import '@kangc/v-md-editor/lib/plugins/copy-code/copy-code.css'
import createEmojiPlugin from '@kangc/v-md-editor/lib/plugins/emoji/index'
import '@kangc/v-md-editor/lib/plugins/emoji/emoji.css'
import createTodoListPlugin from '@kangc/v-md-editor/lib/plugins/todo-list/index'
import '@kangc/v-md-editor/lib/plugins/todo-list/todo-list.css'
import createLineNumbertPlugin from '@kangc/v-md-editor/lib/plugins/line-number/index'
import createAlignPlugin from '@kangc/v-md-editor/lib/plugins/align'
VMdEditor.use(githubTheme, {
  Hljs: hljs
})
VMdEditor.use(createCopyCodePlugin())
VMdEditor.use(createEmojiPlugin())
VMdEditor.use(createTodoListPlugin())
VMdEditor.use(createTodoListPlugin())
VMdEditor.use(createAlignPlugin())
Vue.use(VMdEditor)
// import json from 'highlight.js/lib/languages/json'
// hljs.registerLanguage('json', json)
// --------------------------------

// preview
Vue.use(PiniaVuePlugin)
/**
 * 懒加载
 */
Vue.use(VueLazyload, {
  loading: 'https://lidengxiang.top/blog/loading.gif'
})
/**
 * 事件总线
 */
export const EventBus = new Vue()
Vue.prototype.$bus = EventBus // 将EventBus实例挂载到Vue原型上
/**
 * ----------------------------
 */
// Vue.config.productionTip = false
const fixElTableErr = table => {
  const oldResizeListener = table.methods.resizeListener
  table.methods.resizeListener = function () {
    window.requestAnimationFrame(oldResizeListener.bind(this))
  }
}
// 一定要在Vue.use之前执行此函数
fixElTableErr(Table)
Vue.use(ElementUI)
new Vue({
  pinia,
  router,
  render: h => h(App)
}).$mount('#app')
