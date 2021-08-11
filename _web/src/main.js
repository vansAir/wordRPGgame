import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from './utils/axios.config'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import animated from 'animate.css' // npm install animate.css --save安装，再引入
import Cookies from 'js-cookie';

Vue.use(animated)
Vue.use(ElementUI);


Vue.prototype.axios = axios;
Vue.prototype.Cookies = Cookies;

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
