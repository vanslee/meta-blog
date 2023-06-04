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
  start()
  document.title = getPageTitle(to.meta.title)
  if (isLogin() && !getUserInfo()) {
    const { data, code } = await userInfoApi()
    if (code === 200) {
      data['avatarImgUrl'] = process.env.VUE_APP_WEBSITE_CDN + data['avatarImgUrl']
      setUserInfo(data)
    }
  }
  if (to.name === 'login' && isLogin()) {
    next(from.path)
  }
  if (to.meta.requireAuth) {
    const hasLogin = isLogin()
    if (hasLogin) {
      next()
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
