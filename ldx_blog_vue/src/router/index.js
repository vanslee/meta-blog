import Vue from 'vue'
import VueRouter from 'vue-router'
import { close, start } from '@/utils/nprogress'
import { hasPermission } from '@/utils/auth'
import { getStorage } from '@/utils/storage'
Vue.use(VueRouter)
const routes = [
  {
    path: '/login',
    name: 'login',
    component: () => import('@/layout/Login.vue')
  },
  {
    path: '/',
    meta: { requiresAuth: true },
    component: () => import('@/layout/BlogIndex.vue'),
    children: [
      {
        path: '/',
        name: 'index',
        components: {
          main: () => import('@/components/article/ArticleListCard.vue'),
          left_aside: () => import('@/layout/BlogLeftAside.vue')
        }
      }
    ]
  },
  {
    path: '/article/:id',
    name: 'article',
    components: {
      main: () => import('@/components/ArticleDetails.vue'),
      left_aside: () => import('@/components/article/DetailsLeftAside.vue')
    }
  },
  {
    path: '/write',
    name: 'write',
    meta: { requiresAuth: true },
    component: () => import('@/layout/BlogIndex.vue'),
    children: [
      {
        path: '/write',
        components: {
          main: () => import('@/components/article/ArticleWrite.vue'),
          left_aside: () => import('@/components/article/WriteLeftAside.vue')
        }
      }
    ]
  },
  {
    path: '/test',
    name: 'test',
    component: () => import('@/components/comment/CommentItem.vue')
  }
]

const router = new VueRouter({
  mode: 'hash',
  routes
})

router.beforeEach((to, from, next) => {
  start()
  if (to.matched.some(record => record.meta.requiresAuth)) {
    const tokenInfo = getStorage('LITUBAO_AUTHENTICATION') // 假设你将用户角色存储在 localStorage 中
    const { isLogin } = tokenInfo
    if (!isLogin) {
      // 如果没登录
      next({ name: 'login' })
    } else {
      // 已经登录判断是否有权限
      if (hasPermission(isLogin, to.name)) {
        console.log('from', from)
        console.log('to', to)
        // 有权限
        next()
      } else {
        // 没有权限
        next({ name: to.name })
      }
    }
  } else {
    if (to.name === 'login') {
      const tokenInfo = getStorage('LITUBAO_AUTHENTICATION')
      const { isLogin } = tokenInfo
      isLogin ? next({ name: 'index' }) : next()
    } else {
      next()
    }
  }
})

router.afterEach(() => {
  close()
})
export default router
