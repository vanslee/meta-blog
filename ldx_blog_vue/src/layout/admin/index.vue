<template>
  <div style="display: flex;justify-content: space-between;" :class="classObj">
    <vab-side-bar style="flex-grow: 1;" />
    <router-view style="flex-grow: 1;" />
  </div>
</template>

<script>
import { useSettingStore } from '@/stores/settings'
import { mapActions, mapState } from 'pinia'
import { tokenName } from '@/config'
export default {
  name: 'Layout',
  data() {
    return { oldLayout: '' }
  },
  computed: {
    ...mapState(useSettingStore, ['layout', 'tabsBar', 'collapse', 'header', 'device']),
    classObj() {
      return {
        mobile: this.device === 'mobile',
      }
    },
  },
  beforeMount() {
    window.addEventListener('resize', this.handleResize)
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.handleResize)
  },
  mounted() {
    this.oldLayout = this.layout
    const userAgent = navigator.userAgent
    if (userAgent.includes('Juejin')) {
      this.$baseAlert(
        'vue-admin-beautiful不支持在掘金内置浏览器演示，请手动复制以下地址到浏览器中查看http://mpfhrd48.sanxing.uz7.cn/vue-admin-beautiful'
      )
    }
    const isMobile = this.handleIsMobile()
    if (isMobile) {
      if (isMobile) {
        //横向布局时如果是手机端访问那么改成纵向版
        this.$store.dispatch('settings/changeLayout', 'vertical')
      } else {
        this.$store.dispatch('settings/changeLayout', this.oldLayout)
      }
      this.$store.dispatch('settings/toggleDevice', 'mobile')
      // setTimeout(() => {
      //   this.$store.dispatch('settings/foldSideBar')
      // }, 2000)
    } else {
      // this.$store.dispatch('settings/openSideBar')
    }
    this.$nextTick(() => {
      window.addEventListener(
        'storage',
        (e) => {
          if (e.key === tokenName || e.key === null) window.location.reload()
          if (e.key === tokenName && e.value === null)
            window.location.reload()
        },
        false
      )
    })
  },
  methods: {
    ...mapActions(useSettingStore, ['foldSideBar']),
    handleIsMobile() {
      return document.body.getBoundingClientRect().width - 1 < 992
    },
    handleResize() {
      // if (!document.hidden) {
      //   const isMobile = this.handleIsMobile()
      //   if (isMobile) {
      //     //横向布局时如果是手机端访问那么改成纵向版
      //     this.$store.dispatch('settings/changeLayout', 'vertical')
      //   } else {
      //     this.$store.dispatch('settings/changeLayout', this.oldLayout)
      //   }

      //   this.$store.dispatch(
      //     'settings/toggleDevice',
      //     isMobile ? 'mobile' : 'desktop'
      //   )
      // }
    },
    handleOpen() {

    },
    handleClose() {

    }
  },
}
</script>

