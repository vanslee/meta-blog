import Vue from "vue";
import type { RouteConfig } from "vue-router";
import VueRouter from "vue-router";
import Layout from "@/layout/index.vue";
import AdminLayout from "@/layout/admin/index.vue";
// import { publicPath, routerMode } from "@/config/index.js";
Vue.use(VueRouter);
export const constantRoutes: RouteConfig[] = [
  {
    path: "/login",
    name: "Login",
    meta: {
      title: "登录",
      hidden: true,
    },
    component: () => import("@/views/login/index.vue"),
  },

  {
    path: "/loading/:tk",
    name: "Loading",
    meta: {
      title: "加载",
      hidden: true,
    },
    component: () => import("@/views/error-page/loading.vue"),
    props: (route) => ({ query: route.query.tk }),
  },
  {
    path: "/401",
    name: "401",
    meta: {
      hidden: true,
    },
    component: () => import("@/views/error-page/401.vue"),
  },
  {
    path: "/404",
    name: "404",
    component: () => import("@/views/error-page/404.vue"),
    meta: {
      hidden: true,
    },
  },
  // {
  //   path: '*',
  //   meta: {
  //     requireAuth: false
  //   },
  //   component: () => import('@/views/error-page/404.vue')
  // },
];
export const asyncRoutes = [
  {
    path: "/",
    component: Layout,
    redirect: "/",
    hidden: true,
    children: [
      {
        meta: {
          title: "文章列表",
          icon: "user",
        },
        path: "/",
        name: "Index",
        components: {
          main: () => import("@/layout/components/Mains/ArticleListCard.vue"),
          left_aside: () =>
            import("@/layout/components/Sidebar/BlogRightAside.vue"),
        },
      },
    ],
  },
  {
    path: "/article/:id",
    component: Layout,
    hidden: true,
    children: [
      {
        path: "",
        name: "Article",
        meta: {
          title: "文章详情",
          icon: "user",
          permissions: ["admin"],
        },
        components: {
          main: () => import("@/layout/components/Mains/ArticleDetails.vue"),
          left_aside: () =>
            import("@/layout/components/Sidebar/DetailsLeftAside.vue"),
          right_aside: () =>
            import("@/layout/components/Sidebar/DetailsRightAside.vue"),
        },
      },
    ],
  },
  {
    path: "/write",
    component: Layout,
    hidden: true,
    children: [
      {
        path: "",
        name: "Write",
        meta: {
          icon: "user",
          title: "发布文章",
          permissions: ["admin"],
        },
        components: {
          main: () => import("@/layout/components/Mains/ArticleWrite.vue"),
          left_aside: () =>
            import("@/layout/components/Sidebar/WriteLeftAside.vue"),
        },
      },
    ],
  },
  {
    path: "/article/category/:cid",
    component: Layout,
    hidden: true,
    children: [
      {
        path: "/",
        name: "CArticle",
        meta: {
          title: "文章类别",
          permissions: ["admin"],
        },
        components: {
          main: () => import("@/layout/components/Mains/ArticleListCard.vue"),
          left_aside: () =>
            import("@/layout/components/Sidebar/BlogLeftAside.vue"),
        },
      },
    ],
  },
  {
    path: "/categories",
    component: Layout,
    hidden: true,
    children: [
      {
        path: "/",
        name: "Categories",
        meta: {
          icon: "user",
          title: "文章分类",
          permissions: ["admin"],
        },
        components: {
          main: () => import("@/layout/components/Mains/ArticleCategories.vue"),
          left_aside: () =>
            import("@/layout/components/Sidebar/BlogLeftAside.vue"),
        },
      },
    ],
  },
  {
    path: "/tags",
    component: Layout,
    title: "文章标签",
    hidden: true,
    children: [
      {
        path: "/",
        name: "Tags",
        meta: {
          icon: "user",
          title: "文章标签",
          permissions: ["admin"],
        },
        components: {
          main: () => import("@/layout/components/Mains/ArticleTags.vue"),
          left_aside: () =>
            import("@/layout/components/Sidebar/BlogLeftAside.vue"),
        },
      },
    ],
  },
  {
    path: "/user",
    component: AdminLayout,
    redirect: "/user",
    children: [
      {
        path: "/user",
        name: "User",
        component: () => import("@/layout/admin/Main/UserInfo.vue"),
        meta: {
          icon: "el-icon-user-solid",
          title: "个人中心",
          permissions: ["admin"],
        },
      },
    ],
  },
  {
    path: "/articles",
    component: AdminLayout,
    redirect: "/article",
    children: [
      {
        path: "/articles",
        name: "Articles",
        component: () => import("@/layout/admin/Main/ArticleList.vue"),
        meta: {
          icon: "el-icon-tickets",
          title: "文章管理",
          permissions: ["admin"],
        },
      },
    ],
  },
];
const router = new VueRouter({
  // base: publicPath,
  // mode: routerMode,
  scrollBehavior: () => ({
    y: 0,
    x: 0,
  }),
  routes: constantRoutes,
});
export function resetRouter() {
  location.reload();
}
/**
 * 解决vue-router跳转同一路由报错问题
 */
// const originalPush = VueRouter.prototype.push;
// VueRouter.prototype.push = function push(location) {
//   return originalPush.call(this, location).catch((err: Error) => err);
// };
export default router;
