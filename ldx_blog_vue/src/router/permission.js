import router from '.'
// import { Message } from 'element-ui'
import { close, start } from '@/utils/nprogress'
import { isLogin } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'
// import { useUserStore } from '@/stores/user'
// import pinia from '@/stores'

// const whiteList = ['/login', '/articles', '/article/'] // no redirect whitelist

router.beforeEach(async (to, from, next) => {
  start()
  document.title = getPageTitle(to.meta.title)
  next()
  if (!isLogin()) {
    // 未登录
    if (to.matched.length > 0 && !to.matched.some(record => record.meta.requireAuth)) {
      next()
    } else {
      next({
        path: '/login'
      })
    }
  } else {
    // 用户已经登录
    next()
  }
})
router.afterEach(() => {
  close()
})
