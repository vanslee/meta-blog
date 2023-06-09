/**
 * @author https://vue-admin-beautiful.com （不想保留author可删除）
 * @description 路由守卫，目前两种模式：all模式与intelligence模式
 */
import router from '@/router'
import VabProgress from 'nprogress'
import 'nprogress/nprogress.css'
import { useUserStore } from '@/stores/user'
import { isLogin } from '@/utils/accessToken'
import getPageTitle from '@/utils/pageTitle'
import {
    authentication,
    loginInterception,
    progressBar,
    recordRoute,
    routesWhiteList,
} from '@/config'
import { useRouteStore } from '@/stores/routes'
VabProgress.configure({
    easing: 'ease',
    speed: 500,
    trickleSpeed: 200,
    showSpinner: false,
})
router.beforeResolve(async (to, from, next) => {
    if (progressBar) VabProgress.start()
    const userStore = useUserStore()

    let hasLogin = isLogin()
    if (!loginInterception) hasLogin = true

    if (hasLogin) {
        if (to.path === '/login') {
            next({ path: '/' })
            if (progressBar) VabProgress.done()
        } else {
            const hasPermissions = userStore.permissions && userStore.permissions.length > 0
            if (hasPermissions) {
                next()
            } else {
                try {
                    let permissions
                    if (!loginInterception) {
                        //settings.js loginInterception为false时，创建虚拟权限
                        userStore.permissions = ['admin']
                        permissions = ['admin']
                    } else {
                        permissions = await userStore.getUserInfo()
                    }
                    let accessRoutes = []
                    const routeStore = useRouteStore()
                    if (authentication === 'intelligence') {
                        accessRoutes = await routeStore.setRoutes(permissions)
                    } else if (authentication === 'all') {
                        accessRoutes = await routeStore.setAllRoutes()
                    }
                    accessRoutes.forEach((item) => {
                        router.addRoute(item)
                    })
                    next({ ...to, replace: true })
                } catch {
                    await userStore.resetAccessToken()
                    if (progressBar) VabProgress.done()
                }
            }
        }
    } else {
        if (routesWhiteList.indexOf(to.path) !== -1) {
            next()
        } else {
            if (recordRoute) {
                next(`/login?redirect=${to.path}`)
            } else {
                next('/login')
            }

            if (progressBar) VabProgress.done()
        }
    }
    document.title = getPageTitle(to.meta.title)
})
router.afterEach(() => {
    if (progressBar) VabProgress.done()
})
