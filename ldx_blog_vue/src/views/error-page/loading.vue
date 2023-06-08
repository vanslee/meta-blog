<template>
  <div />
</template>
<script>
import { useUserStore } from '@/stores/user'
import { mapActions, mapState } from 'pinia'
export default {
  name: 'LoadingVue',
  data() {
    return {}
  },
  mounted() {
    const loading = this.$loading({
      lock: true,
      text: 'Loading',
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 0.7)'
    })
    const userStore = useUserStore()
    userStore.token = this.$route.query.tk
    const success = this.getUserInfo()
    loading.close()
    if (success) {
      this.$router.push({ name: 'Index' })
    } else {
      this.$router.push({ name: 'Login' })
    }
  },
  created() {},
  computed: {
    ...mapState(useUserStore, ['user'])
  },
  methods: {
    ...mapActions(useUserStore, ['login', 'getUserInfo'])
  }
}
</script>
<style scoped></style>
