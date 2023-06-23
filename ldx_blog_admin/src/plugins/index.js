/* 公共引入,勿随意修改,修改时需经过确认 */
import Vue from 'vue'
import './element'
import './support'
import '@/styles/vab.scss'
import '@/remixIcon'
import '@/colorfulIcon'
import '@/config/permission'
import '@/utils/errorLog'
import '@/markdown/index'
import './vabIcon'
import VabPermissions from 'layouts/Permissions'
import Vab from '@/utils/vab'
import VueLazyload from 'vue-lazyload'
Vue.use(Vab)
Vue.use(VabPermissions)
Vue.use(VueLazyload, {
  loading: 'https://lidengxiang.top/blog/loading.gif',
})
