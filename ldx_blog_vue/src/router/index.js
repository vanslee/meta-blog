import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '@/layout/index.vue'
import AdminLayout from '@/layout/admin/index.vue'
import { publicPath, routerMode } from '@/config'
Vue.use(VueRouter)
export const constantRoutes = [
  {
    path: '/login',
    name: 'Login',
    title: '登录',
    component: () => import('@/views/login/index.vue'),
    hidden: true
  },

  {
    path: '/loading/:tk',
    name: 'Loading',
    title: '加载',
    component: () => import('@/views/error-page/loading.vue'),
    hidden: true,
    props: (route) => ({ query: route.query.tk })
  },
  {
    path: '/401',
    name: '401',
    component: () => import('@/views/error-page/401.vue'),
    hidden: true,
  },
  {
    path: '/404',
    name: '404',
    component: () => import('@/views/error-page/404.vue'),
    hidden: true,
  },
  // {
  //   path: '*',
  //   meta: {
  //     requireAuth: false
  //   },
  //   component: () => import('@/views/error-page/404.vue')
  // },
]
export const asyncRoutes = [
  {
    path: '/',
    component: Layout,

    children: [
      {
        meta: {
          title: '文章列表',
          icon: 'user'
        },
        path: '/',
        name: 'Index',
        components: {
          main: () => import('@/layout/components/Mains/ArticleListCard.vue'),
          left_aside: () =>
            import('@/layout/components/Sidebar/BlogRightAside.vue')
        }
      }
    ]
  },
  {
    path: '/article/:id',
    component: Layout,
    children: [
      {
        path: '',
        name: 'Article',
        meta: {
          title: '文章详情',
          icon: 'user',
          permissions: ['admin']
        },
        components: {
          main: () => import('@/layout/components/Mains/ArticleDetails.vue'),
          left_aside: () =>
            import('@/layout/components/Sidebar/DetailsLeftAside.vue'),
          right_aside: () =>
            import('@/layout/components/Sidebar/DetailsRightAside.vue')
        }
      }
    ]
  },
  {
    path: '/write',
    component: Layout,
    children: [
      {
        path: '',
        name: 'Write',
        meta: {
          icon: 'user',
          title: '发布文章',
          permissions: ['admin']
        },
        components: {
          main: () => import('@/layout/components/Mains/ArticleWrite.vue'),
          left_aside: () =>
            import('@/layout/components/Sidebar/WriteLeftAside.vue')
        }
      }
    ]
  },
  {
    path: '/article/category/:cid',
    component: Layout,
    children: [
      {
        path: '/',
        name: 'CArticle',
        meta: {
          title: '文章类别',
          permissions: ['admin']
        },
        components: {
          main: () => import('@/layout/components/Mains/ArticleListCard.vue'),
          left_aside: () =>
            import('@/layout/components/Sidebar/BlogLeftAside.vue')
        }
      }
    ]
  },
  {
    path: '/categories',
    component: Layout,
    children: [
      {
        path: '/',
        name: 'Categories',
        meta: {
          icon: 'user',
          title: '文章分类',
          permissions: ['admin']
        },
        components: {
          main: () => import('@/layout/components/Mains/ArticleCategories.vue'),
          left_aside: () =>
            import('@/layout/components/Sidebar/BlogLeftAside.vue')
        }
      }
    ]
  },
  {
    path: '/tags',
    component: Layout,
    title: '文章标签',
    children: [
      {
        path: '/',
        name: 'Tags',
        meta: {
          icon: 'user',
          title: '文章标签',
          permissions: ['admin']
        },
        components: {
          main: () => import('@/layout/components/Mains/ArticleTags.vue'),
          left_aside: () =>
            import('@/layout/components/Sidebar/BlogLeftAside.vue')
        }
      }
    ]
  },
]
export const userRoutes = [
  {
    path: '/user',
    component: AdminLayout,
    redirect: '/user',
    children: [
      {
        path: '/user',
        name: 'User',
        component: () => import('@/views/user/index.vue'),
        meta: {
          icon: 'user',
          title: '个人中心',
          permissions: ['admin']
        },
      }
    ]
  }
]
const router = new VueRouter({
  base: publicPath,
  mode: routerMode,
  scrollBehavior: () => ({
    y: 0,
  }),
  routes: constantRoutes,
})
export function resetRouter() {
  location.reload()
}


export default router
