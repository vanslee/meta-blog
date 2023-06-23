<template>
    <el-scrollbar class="side-bar-container" :class="{ 'is-collapse': collapse }">
        <el-menu :default-active="activeMenu" :collapse="collapse" :collapse-transition="false"
            :default-openeds="defaultOpens" :unique-opened="uniqueOpened" mode="vertical">
            <div v-for="route in routes">
                <vab-side-bar-item :key="route.path" :full-path="route.path" :item="route" />
            </div>
        </el-menu>
    </el-scrollbar>
</template>
<script>
// import variables from '@/styles/variables.scss'
import { mapState } from 'pinia'
import { useSettingStore } from '@/stores/settings'
import { useRouteStore } from "@/stores/routes"
import { defaultOopeneds, uniqueOpened } from '@/config'

export default {
    name: 'VabSideBar',
    data() {
        return {
            uniqueOpened,
        }
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
}
</script>
<style lang="scss" scoped>
@mixin active {
    &:hover {
        color: #fff;
        background-color: #409eff !important;
    }

    &.is-active {
        color: #fff;
        background-color: #409eff !important;
    }
}

.side-bar-container {
    position: fixed;
    top: 0;
    bottom: 0;
    left: 0;
    z-index: 999;
    width: 256px;
    height: 100vh;
    overflow: hidden;
    background: #21252b;
    box-shadow: 2px 0 6px rgba(0, 21, 41, 0.35);
    transition: width 0.3;

    &.is-collapse {
        width: 65px;
        border-right: 0;

        ::v-deep {
            .el-menu {
                transition: width 0.3;
            }

            .el-menu--collapse {
                border-right: 0;

                .el-submenu__icon-arrow {
                    right: 10px;
                    margin-top: -3px;
                }

                .el-menu-item,
                .el-submenu {
                    text-align: center;
                }
            }
        }
    }

    ::v-deep {
        .el-scrollbar__wrap {
            overflow-x: hidden;
        }

        .el-menu {
            border: 0;

            .vab-fas-icon {
                padding-right: 3px;
                font-size: 14px;
                display: inline-block;
                width: 14px;
            }

            .vab-remix-icon {
                padding-right: 3px;
                font-size: 16px;
            }
        }

        .el-menu-item,
        .el-submenu__title {
            height: 50px;
            line-height: 50px;
            vertical-align: middle;
        }

        .el-menu-item {
            @include active;
        }
    }
}
</style>
  