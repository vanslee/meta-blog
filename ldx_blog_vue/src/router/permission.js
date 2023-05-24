import router from '.'
import { useUserStore } from '@/stores/user'
import { Message } from 'element-ui'
import { close, start } from '@/utils/nprogress'
import { isLogin } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'
import pinia from '@/stores'
const whiteList = ['/login'] // no redirect whitelist

router.beforeEach(async (to, from, next) => {
  const store = useUserStore(pinia)
  // start progress bar
  start()

  // set page title
  document.title = getPageTitle(to.meta.title)

  // determine whether the user has logged in
  if (isLogin()) {
    if (to.path === '/login') {
      // if is logged in, redirect to the home page
      next({ path: from.fullPath })
      close()
    } else {
      const name = store.local.name
      if (name) {
        next()
      } else {
        try {
          // get user info
          await store.getInfo()
          next()
        } catch (error) {
          // remove token and go to login page to re-login
          await store.logout()
          Message.error(error || 'Has Error')
          next(`/login?redirect=${to.path}`)
          close()
        }
      }
    }
  } else {
    /* has no token*/

    if (whiteList.indexOf(to.path) !== -1) {
      // in the free login whitelist, go directly
      next()
    } else {
      // other pages that do not have permission to access are redirected to the login page.
      next(`/login?redirect=${to.path}`)
      close()
    }
  }
})

router.afterEach(() => {
  // finish progress bar
  close()
})
