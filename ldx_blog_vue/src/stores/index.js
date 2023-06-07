import { createPinia } from 'pinia'
import persistedPlugin from 'pinia-plugin-persistedstate'
const pinia = createPinia()
pinia.use(persistedPlugin)
export default pinia
