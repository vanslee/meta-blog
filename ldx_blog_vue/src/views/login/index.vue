<template>
  <div class="container" ref="containerRef" @keydown.enter="login">
    <div class="forms-container">
      <div class="signin-signup">
        <form class="sign-in-form">
          <h2 class="title">
            登录
          </h2>
          <div class="input-field">
            <i class="fas fa-user" />
            <input type="text" autocomplete="username" placeholder="用户名" v-model="params.username" id="username">
          </div>
          <div class="input-field">
            <i class="fas fa-lock" />
            <input type="password" autocomplete="current-password" placeholder="密码" v-model="params.password"
              id="password">
          </div>
          <el-button @click="login" class="btn solid" :loading="isLoading">
            立即登录
          </el-button>
          <p class="social-text">
            通过其他方式
          </p>
          <div class="social-media">
            <a :href="qqRedirectUri" class="social-icon">
              <i class="iconfont fab icon-qq" />
            </a>
            <a :href="wechatRedirectUri" class="social-icon">
              <i class="fab iconfont icon-wechat" />
            </a>
            <a :href="giteeRedirectUri" class="social-icon">
              <i class="fab iconfont icon-gitee" />
            </a>
            <a :href="githubRedirectUri" class="social-icon">
              <i class="fab iconfont">&#xe628;</i>
            </a>
          </div>
        </form>
        <form class="sign-up-form">
          <h2 class="title">
            注册
          </h2>
          <div class="input-field">
            <i class="fas fa-user" />
            <input type="text" autocomplete="username" placeholder="用户名" v-model="params.username" id="username1">
          </div>
          <!-- <div class="input-field">
            <i class="fas fa-envelope"></i>
            <input type="email" autocomplete="email" placeholder="邮箱" v-model="params.email" />
          </div> -->
          <div class="input-field">
            <i class="fas fa-lock" />
            <input type="password" autocomplete="current-password" placeholder="密码" v-model="params.password"
              id="password1">
          </div>
          <div class="input-field">
            <i class="fas fa-lock" />
            <input type="password" autocomplete="current-password" placeholder="确认密码" v-model="params.confirmPassword"
              id="confirmPassword1">
          </div>
          <el-button class="btn" @click="registry" :loading="isLoading">
            立即注册
          </el-button>
          <p class="social-text">
            通过其他方式
          </p>
          <div class="social-media">
            <a :href="qqRedirectUri" class="social-icon">
              <i class="iconfont fab icon-qq" />
            </a>
            <a :href="wechatRedirectUri" class="social-icon">
              <i class="fab iconfont icon-wechat" />
            </a>
            <a :href="giteeRedirectUri" class="social-icon">
              <i class="fab iconfont icon-gitee" />
            </a>
            <a :href="githubRedirectUri" class="social-icon">
              <i class="fab iconfont">&#xe628;</i>
            </a>
          </div>
        </form>
      </div>
    </div>

    <div class="panels-container">
      <div class="panel left-panel">
        <div class="content">
          <h3>加入我们</h3>
          <p>加入我们，成为本站的一份子。</p>
          <button class="btn transparent" id="sign-up-btn" @click="toSignUp">
            去注册
          </button>
        </div>
        <img src="./img/log.svg" class="image" alt="">
      </div>
      <div class="panel right-panel">
        <div class="content">
          <h3>已有帐号？</h3>
          <p>立即登录已有帐号，享受独家权益。</p>
          <button class="btn transparent" id="sign-in-btn" @click="toLogin">
            去登录
          </button>
        </div>
        <img src="./img/register.svg" class="image" alt="">
      </div>
    </div>
  </div>
</template>
<script>
import './style.css'
import { registryApi } from '@/apis/user'
import { useUserStore } from '@/stores/user'
// import { getToken } from '@/utils/auth'
export default {
  name: 'LoginOrRegPage',
  data() {
    const userStore = useUserStore()
    const params = {
      username: 'vanslee64',
      password: 'zhou2001.',
      confirmPassword: ''
    }
    const qqRedirectUri = `https://graph.qq.com/oauth2.0/show?which=Login&display=pc&response_type=code&client_id=${process.env.VUE_APP_QQ_CLIENTID}&redirect_uri=${process.env.VUE_APP_QQ_REDIRECTURI}&state=litubao`
    const giteeRedirectUri = `https://gitee.com/oauth/authorize?client_id=${process.env.VUE_APP_GITEE_CLIENTID}&redirect_uri=${process.env.VUE_APP_GITEE_REDIRECTURI}&response_type=code`
    const githubRedirectUri = `https://github.com/login/oauth/authorize?client_id=${process.env.VUE_APP_GITHUB_CLIENTID}&redirect_uri=${process.env.VUE_APP_GITHUB_REDIRECTURI}&state=litubao`
    const wechatRedirectUri = `https://open.weixin.qq.com/connect/qrconnect?appid=${process.env.VUE_APP_WECHAT_APPID}&redirect_uri=${process.env.VUE_APP_WECHAT_REDIRECTURI}&response_type=code&scope=snsapi_login&state=litubao`
    return {
      params,
      userStore,
      isLoading: false,
      giteeRedirectUri,
      qqRedirectUri,
      githubRedirectUri,
      wechatRedirectUri,
      redirect: undefined,
    }
  },
  created() {
    // console.log(JSON.parse(localStorage.getItem('user')))
  },
  watch: {
    $route: {
      handler(route) {
        this.redirect = (route.query && route.query.redirect) || '/'
      },
      immediate: true,
    },
  },
  computed: {},
  methods: {
    async registry() {
      const { code, msg } = await registryApi(this.params)
      this.isLoading = true
      if (code === 200) {
        this.$message.success(msg)
        this.$refs.containerRef.classList.remove('sign-up-mode')
      } else {
        this.$message.error(msg)
      }
      this.initParams()
      this.isLoading = false
    },
    login() {
      if (this.checkParams({ type: 'login' }) !== true) return
      this.isLoading = true
      this.userStore.login(this.params).then(res => {
        const routerPath =
          this.redirect === '/404' || this.redirect === '/401'
            ? '/articles'
            : this.redirect
        this.$router.push(routerPath).catch(() => { })
      })
      this.isLoading = false
      this.initParams()
    },
    toLogin() {
      this.$refs.containerRef.classList.remove('sign-up-mode')
    },
    toSignUp() {
      this.$refs.containerRef.classList.add('sign-up-mode')
      // console.log('注册')
    },
    initParams() {
      this.params.password = ''
      this.params.username = ''
      this.params.confirmPassword = ''
    },
    checkParams(options) {
      const { username, password, confirmPassword } = this.params
      // let usernameInput = document.getElementById('username')
      // let passwordInput = document.getElementById('password')
      if (typeof username !== 'string' || username.length < 5) {
        // usernameInput.focus()
        // usernameInput.style = 'border: 1px solid red'
        return false
      }
      // usernameInput.style = ''
      if (typeof password !== 'string' || password.length <= 6) {
        // passwordInput.focus()
        // passwordInput.style = 'border: 1px solid red'
        return false
      }
      // passwordInput.style = ''
      if (options.type === 'registry') {
        // var confirmPwdInput = document.getElementById('confirmPassword')
        if (password !== confirmPassword) {
          // confirmPwdInput.focus()
          // confirmPwdInput.style = 'border: 1px solid red'
          return false
        }
      }
      // confirmPwdInput.style = ''
      // usernameInput = null
      // passwordInput = null
      // confirmPwdInput = null
      return true
    }
  }
}
</script>
