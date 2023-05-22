import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI, { Table } from 'element-ui'
import '@/assets/styles/index.css'
import '@/assets/login/style_login.css'
import 'element-ui/lib/theme-chalk/index.css'
import 'default-passive-events'
import pinia from './stores'

/**
 * ----------------------------
 */
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js'
import hljs from 'highlight.js'
// editor
import VMdEditor from '@kangc/v-md-editor'
import createCopyCodePlugin from '@kangc/v-md-editor/lib/plugins/copy-code/index'
import '@kangc/v-md-editor/lib/plugins/copy-code/copy-code.css'
import '@kangc/v-md-editor/lib/style/base-editor.css'
import '@kangc/v-md-editor/lib/theme/style/github.css'
// preview
import VMdPreview from '@kangc/v-md-editor/lib/preview'
import '@kangc/v-md-editor/lib/style/preview.css'
import '@kangc/v-md-editor/lib/theme/style/github.css'
VMdPreview.use(githubTheme, {
  Hljs: hljs
})
VMdEditor.use(githubTheme, {
  Hljs: hljs
})
VMdEditor.use(createCopyCodePlugin())
Vue.use(VMdPreview)
Vue.use(VMdEditor)
Vue.use(pinia)
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
  router,
  render: h => h(App)
}).$mount('#app')
