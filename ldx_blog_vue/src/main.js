import './plugins'
import Vue from 'vue'
import App from './App.vue'
import '@/layout/admin/export'
import router from './router'
// import '@/router/permission'
// import 'default-passive-events'



/**
 * 事件总线
 */
export const EventBus = new Vue()
Vue.prototype.$bus = EventBus // 将EventBus实例挂载到Vue原型上
/**
 * ----------------------------
 */
// Vue.config.productionTip = false
// const fixElTableErr = (table) => {
//   const oldResizeListener = table.methods.resizeListener
//   table.methods.resizeListener = function () {
//     window.requestAnimationFrame(oldResizeListener.bind(this))
//   }
// }
// 一定要在Vue.use之前执行此函数
// fixElTableErr(Table)
// Vue.use(ElementUI)
new Vue({
  router,
  render: (h) => h(App)
}).$mount('#app')
