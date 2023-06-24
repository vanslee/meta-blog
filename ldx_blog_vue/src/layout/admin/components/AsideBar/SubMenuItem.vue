<template>
    <el-submenu ref="subMenu" :index="handlePath(item.path)" :popper-append-to-body="false">
        <template slot="title">
            <i :class="item.meta.icon" />
            <span>{{ item.meta.title }}</span>
        </template>
        <slot />
    </el-submenu>
</template>
  
<script>
import path from 'path'
import { mapState } from 'pinia'
import { useSettingStore } from '@/stores/settings'
import { isExternal } from '@/utils/validate'

export default {
    name: 'VabSubmenu',
    props: {
        routeChildren: {
            type: Object,
            default() {
                return null
            },
        },
        item: {
            type: Object,
            default() {
                return null
            },
        },
        fullPath: {
            type: String,
            default: '',
        },
    },
    computed: {
        ...mapState(useSettingStore, ['collapse']),
    },
    methods: {
        handlePath(routePath) {
            if (isExternal(routePath)) {
                return routePath
            }
            if (isExternal(this.fullPath)) {
                return this.fullPath
            }
            return path.resolve(this.fullPath, routePath)
        },
    },
}
</script>
  