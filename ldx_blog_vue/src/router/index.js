import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '@/layout'
Vue.use(VueRouter)
const constantRoutes = [
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
        name: 'Index',
        components: {
          main: () => import('@/layout/components/Mains/ArticleListCard.vue'),
          left_aside: () => import('@/layout/components/Sidebar/BlogRightAside.vue')
        }
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    title: '登录',
    meta: {
      requireAuth: false
    },
    component: () => import('@/views/login/'),
    hidden: true
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
          left_aside: () => import('@/layout/components/Sidebar/DetailsLeftAside.vue'),
          right_aside: () => import('@/layout/components/Sidebar/DetailsRightAside.vue')
        }
      }
    ]
  },
  {
    path: '/write',
    component: Layout,
    title: '发布文章',
    children: [
      {
        path: '',
        name: 'Write',
        meta: {
          requireAuth: true
        },
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
    component: () => import('@/views/error-page/404.vue')
  }
]

const createRouter = () =>
  new VueRouter({
    scrollBehavior: () => ({ y: 0 }),
    routes: constantRoutes
  })
const router = createRouter()

export default router
