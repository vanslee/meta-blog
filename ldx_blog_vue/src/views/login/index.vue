<template>
  <div class="content">
    <div class="login-wrapper">
      <div class="left-img">
        <div class="glass">
          <div class="tips">
            <div class="title">THE SPACE NETWORK</div>
            <h1>Explore The Universe</h1>
            <span>5 Million+ people have joined our network.</span>
            <span>we invite you to join the tribe</span>
          </div>
        </div>
      </div>
      <div class="right-login-form">
        <div class="form-wrapper">
          <h1>登 录</h1>
          <form>
            <div class="input-items">
              <span class="input-tips">账号:</span>
              <input
                autocomplete="new-password"
                type="text"
                class="inputs"
                placeholder="请输入账号"
                v-model="params.username"
                id="username"
              />
            </div>
            <div class="input-items">
              <span class="input-tips">密码:</span>
              <input
                v-model="params.password"
                type="password"
                autocomplete="new-password"
                class="inputs"
                placeholder="请输入密码"
                id="password"
              />
              <span class="forgot">忘记密码?</span>
            </div>
          </form>
          <el-button class="btn" @click="toLogin" :loading="isLoding">登 录</el-button>
          <div class="siginup-tips">
            <span>没有账户?&nbsp;</span>
            <span>注册</span>
          </div>
          <div class="other-login">
            <div class="divider">
              <span class="line"></span>
              <span class="divider-text">or</span>
              <span class="line"></span>
            </div>
            <div class="other-login-wrapper">
              <div class="other-login-item">
                <img src="@/assets/imgs/QQ.png" alt="QQ" />
              </div>
              <div class="other-login-item">
                <img src="@/assets/imgs/WeChat.png" alt="WeChat" />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { loginApi } from '@/apis/user'
import { setToken } from '@/utils/auth'
// import { validateIsNull } from "@/util/validate";
export default {
  name: 'BlogLogin',
  data() {
    const params = {
      username: '',
      password: ''
    }
    const rules = {
      // pass: [{ validator: validateIsNull, trigger: 'blur' }],
      // checkPass: [{ validator: validateIsNull, trigger: 'blur' }],
      // age: [{ validator: validateIsNull, trigger: 'blur' }]
    }
    return {
      rules,
      params,
      isLoding: false,
      redirect: undefined,
      userStore: {}
    }
  },
  watch: {
    $route: {
      handler(route) {
        //URL变化时触发,route代表更新后的$route对象
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  created() {},
  computed: {},
  mounted() {},
  methods: {
    async toLogin() {
      const { username, password } = this.params
      let usernameInput = document.getElementById('username')
      let passwordInput = document.getElementById('password')
      if (typeof username !== 'string' || username.length < 5) {
        usernameInput.focus()
        usernameInput.style = 'border: 1px solid red'
        return
      }
      usernameInput.style = ''
      if (typeof password !== 'string' || password.length <= 6) {
        passwordInput.focus()
        passwordInput.style = 'border: 1px solid red'
        return
      }
      passwordInput.style = ''
      usernameInput = null
      passwordInput = null
      this.isLoding = true
      const { code, data } = await loginApi(this.params)
      console.log('data', data)
      if (code === 200) {
        setToken(data['tokenValue'])
        this.$router.push({ path: this.redirect || '/' })
      }
      this.isLoding = false
    }
  }
}
</script>
<style scoped></style>
