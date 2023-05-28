import router from '.'
// import { Message } from 'element-ui'
import { close, start } from '@/utils/nprogress'
import { getUserInfo, isLogin, setUserInfo } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'
import { userInfoApi } from '@/apis/user/'
// import { useUserStore } from '@/stores/user'
// import pinia from '@/stores'

// const whiteList = ['/login', '/articles', '/article/'] // no redirect whitelist

router.beforeEach(async (to, from, next) => {
  document.title = getPageTitle(to.meta.title)
  start()
  if (to.meta.requireAuth) {
    const hasLogin = isLogin()
    const name = getUserInfo()['username']
    if (hasLogin) {
      if (typeof name === 'string' && name.length > 0) {
        next()
      } else {
        const { data, code } = await userInfoApi()
        if (code === 200) {
          setUserInfo(data)
          next()
        }
      }
    } else {
      next(`/login?redirect=${to.path}`)
    }
  } else {
    next()
  }
})

router.afterEach(() => {
  close()
})
