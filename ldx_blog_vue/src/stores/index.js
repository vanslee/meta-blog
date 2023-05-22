import { createPinia } from 'pinia'
import { setStorage, getStorage } from '@/utils/storage'
const __piniaKey = '__DEFAULT_KEY__'

//利用函数柯丽华接受用户入参
const piniaPlugin = options => {
  //将函数返回给pinia  让pinia  调用 注入 context
  return context => {
    const { store } = context
    const data = getStorage(`${options?.key ?? __piniaKey}_${store.$id}`)
    store.$subscribe(() => {
      setStorage(`${options?.key ?? __piniaKey}_${store.$id}`, store.$state.local)
    })
    return {
      ...data
    }
  }
}
const pinia = createPinia()
pinia.use(
  piniaPlugin({
    key: 'LITUBAO'
  })
)
export default pinia
