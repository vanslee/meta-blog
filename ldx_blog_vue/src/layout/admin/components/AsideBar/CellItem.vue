<template>
    <component :is="menuComponent" v-if="!item.hidden" :item="item" :full-path="fullPath" :route-children="routeChildren">
        <template v-if="item.children && item.children.length">
            <cell-item v-for="route in item.children" :key="route.path" :full-path="handlePath(route.path)" :item="route" />
        </template>
    </component>
</template>
<script>
import path from 'path'
import { isExternal } from '@/utils/validate'
import MenuItem from './MenuItem.vue'
import SubMenuItem from './SubMenuItem.vue'
export default {
    components: {
        MenuItem,
        SubMenuItem
    },
    props: {
        item: {
            type: Object,
            required: true,
        },
        fullPath: {
            type: String,
            default: '',
        },
    },
    data() {
        return {
        }
    },
    created() {
    },
    computed: {
        menuComponent() {
            if (
                this.handleChildren(this.item.children, this.item) &&
                (!this.routeChildren.children ||
                    this.routeChildren.notShowChildren) &&
                !this.item.alwaysShow
            ) {
                return 'MenuItem'
            } else {
                return 'SubMenuItem'
            }
        },
    },
    methods: {
        handleChildren(children = [], parent) {
            if (children === null) children = []
            const showChildren = children.filter((item) => {
                if (item.hidden) {
                    return false
                } else {
                    this.routeChildren = item
                    return true
                }
            })
            if (showChildren.length === 1) {
                return true
            }

            if (showChildren.length === 0) {
                this.routeChildren = {
                    ...parent,
                    path: '',
                    notShowChildren: true,
                }
                return true
            }
            return false
        },
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
<style  scoped></style>
