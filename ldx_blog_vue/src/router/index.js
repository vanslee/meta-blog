import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '@/layout'
Vue.use(VueRouter)
const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/redirect/')
      }
    ]
  },
  {
    path: '/',
    redirect: '/articles',
    title: '文章列表',
    component: Layout,
    meta: {
      requireAuth: false
    },
    children: [
      {
        path: 'articles',
        name: 'articles',
        components: {
          main: () => import('@/layout/components/Mains/ArticleListCard.vue'),
          left_aside: () => import('@/layout/components/Sidebar/BlogRightAside.vue')
        }
      }
    ]
  },
  {
    path: '/login',
    name: 'login',
    title: 'name',
    meta: {
      requireAuth: false
    },
    component: () => import('@/views/login/'),
    hidden: true
  },
  {
    path: '/auth-redirect',
    component: () => import('@/views/login/auth-redirect')
  },
  {
    path: '/article/:id',
    component: Layout,
    title: '文章详情',
    meta: {
      requireAuth: false
    },
    children: [
      {
        path: '',
        name: 'Article',
        components: {
          main: () => import('@/layout/components/Mains/ArticleDetails.vue'),
          left_aside: () => import('@/layout/components/Sidebar/DetailsLeftAside.vue')
        }
      }
    ]
  },
  {
    path: '/write',
    component: Layout,
    title: '发布文章',
    meta: {
      requireAuth: false
    },
    children: [
      {
        path: '',
        name: 'Write',
        components: {
          main: () => import('@/layout/components/Mains/ArticleWrite.vue'),
          left_aside: () => import('@/layout/components/Sidebar/WriteLeftAside.vue')
        }
      }
    ]
  },
  {
    path: '*',
    meta: {
      requireAuth: false
    },
    redirect: '/404'
  },
  {
    path: '/404',
    meta: {
      requireAuth: false
    },
    component: () => import('@/views/error-page/404.vue')
  }
]

const createRouter = () =>
  new VueRouter({
    scrollBehavior: () => ({ y: 0 }),
    routes: constantRoutes
  })
const router = createRouter()

export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}
// router.beforeEach((to, from, next) => {
//   start()
//   if (to.name === 'login') {
//     if (getStorage('LITUBAO_user')['isLogin']) {
//       next(from.fullPath)
//     }
//   }
//   next()
// })

// router.afterEach(() => {
//   close()
// })
export default router
