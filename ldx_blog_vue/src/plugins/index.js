/* 公共引入,勿随意修改,修改时需经过确认 */
import Vue from 'vue'
import '@/assets/iconfont/iconfont.css'
import './elementui'
import './markdown'
import './pinia'
import '@/config/permission'
import VueLazyload from 'vue-lazyload'
Vue.use(VueLazyload, {
    loading: 'https://lidengxiang.top/blog/loading.gif',
})
