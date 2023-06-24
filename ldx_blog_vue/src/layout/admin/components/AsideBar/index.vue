<template>
    <el-menu style="height: 100%;" :default-active="activeMenu" :collapse="collapse" :collapse-transition="false"
        :default-openeds="defaultOpens" :unique-opened="uniqueOpened" mode="vertical">
        <div v-for="route in routes">
            <cell-item :key="route.path" :full-path="route.path" :item="route" />
        </div>
    </el-menu>
</template>
<script>
import { mapState } from 'pinia';
import CellItem from './CellItem.vue';
import { useRouteStore } from "@/stores/routes"
import { useSettingStore } from "@/stores/settings"
import { defaultOopeneds, uniqueOpened } from '@/config'
export default {
    components: {
        CellItem,
    },
    data() {
        return {
            uniqueOpened
        }
    },
    created() {
    },
    computed: {
        ...mapState(useSettingStore, ['collapse', 'routes']),
        ...mapState(useRouteStore, ['routes']),
        defaultOpens() {
            if (this.collapse) {
            }
            return defaultOopeneds
        },
        activeMenu() {
            const route = this.$route
            const { meta, path } = route
            if (meta.activeMenu) {
                return meta.activeMenu
            }
            return path
        },
    },
    methods: {
    },
}
</script>
<style  scoped></style>
