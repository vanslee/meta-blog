import Vue from "vue";
// import "./plugins";
import App from "@/App.vue";
import "virtual:windi.css";
import router from "@/router/index";
// import "@/layout/admin/export";
export const EventBus = new Vue();
Vue.prototype.$bus = EventBus;
Vue.config.productionTip = false;
Vue.config.devtools = true;
new Vue({
  el: "#app",
  router,
  render: (h) => h(App),
});
