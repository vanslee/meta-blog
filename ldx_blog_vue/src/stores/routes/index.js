/**
 * @author https://vue-admin-beautiful.com （不想保留author可删除）
 * @description 路由拦截状态管理，目前两种模式：all模式与intelligence模式，其中partialRoutes是菜单暂未使用
 */
import { userRoutes, constantRoutes } from '@/router'
import { getRouterList } from '@/apis/router'
import { convertRouter, filterAsyncRoutes } from '@/utils/handleRoutes'
import { defineStore } from 'pinia'
export const useRouteStore = defineStore('routes', {
  state: () => ({
    routes: [],
    partialRoutes: [],
  }),
  actions: {
    async setRoutes(permissions) {
      //开源版只过滤动态路由permissions，admin不再默认拥有全部权限
      const finallyAsyncRoutes = await filterAsyncRoutes(
        [...userRoutes],
        permissions
      )
      this.routes = constantRoutes.concat(finallyAsyncRoutes)
      return finallyAsyncRoutes
    },
    async setAllRoutes() {
      let { data } = await getRouterList()
      data.push({ path: '*', redirect: '/404', hidden: true })
      let accessRoutes = convertRouter(data)
      this.routes = constantRoutes.concat(accessRoutes)
      return accessRoutes
    },
    setPartialRoutes(accessRoutes) {
      this.partialRoutes = constantRoutes.concat(accessRoutes)
      return accessRoutes
    },
  }
})

