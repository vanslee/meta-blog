<template>
  <el-submenu
    ref="subMenu"
    :index="handlePath(item.path)"
    :popper-append-to-body="false"
  >
    <template slot="title">
      <vab-icon
        v-if="item.meta && item.meta.icon"
        :icon="['fas', item.meta.icon]"
        class="vab-fas-icon"
      />
      <vab-remix-icon
        v-if="item.meta && item.meta.remixIcon"
        :icon-class="item.meta.remixIcon"
        class="vab-remix-icon"
      />
      <span>{{ item.meta.title }}</span>
    </template>
    <slot />
  </el-submenu>
</template>

<script>
  import path from 'path'
  import { mapGetters } from 'vuex'
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
      ...mapGetters({
        collapse: 'settings/collapse',
      }),
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
