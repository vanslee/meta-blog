import router from '.'
// import { Message } from 'element-ui'
import { close, start } from '@/utils/nprogress'
import { getUserInfo, isLogin, setUserInfo } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'
import { userInfoApi } from '@/apis/user/'
// import { useUserStore } from '@/stores/user'
// import pinia from '@/stores'

const whiteList = ['/login', '/articles'] // no redirect whitelist

router.beforeEach(async (to, from, next) => {
  // const store = useUserStore(pinia)
  // console.log('store', store)
  start()
  document.title = getPageTitle(to.meta.title)
  console.log('isLogin', isLogin())
  if (isLogin()) {
    if (to.path === '/login') {
      next({ path: from.fullPath })
      close()
    } else {
      const name = getUserInfo()['name']
      if (name) {
        next()
      } else {
        try {
          // get user info
          const { data, code } = await userInfoApi()
          if (code === 200) {
            data['avatarImgUrl'] = process.env.VUE_APP_WEBSITE_CDN + data['avatarImgUrl']
            setUserInfo(data)
          } else {
            console.log('asda')
            next(`/login?redirect=${to.path}`)
          }
          next()
        } catch (error) {
          // remove token and go to login page to re-login
          // await store.logout()
          // Message.error(error || 'Has Error')
          next(`/login`)
          close()
        }
      }
    }
  } else {
    /* has no token*/
    if (whiteList.indexOf(to.path) !== -1) {
      next()
    } else {
      // other pages that do not have permission to access are redirected to the login page.
      next(`/login?redirect=${to.path}`)
      close()
    }
  }
})

router.afterEach(() => {
  close()
})
