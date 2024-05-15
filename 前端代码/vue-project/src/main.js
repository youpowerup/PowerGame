import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store/store'

//import axios from 'axios'
import Axios from 'axios'
// 设置全局的axios
Vue.prototype.$axios=Axios
// 设置基本路径
Axios.defaults.baseURL=process.env.VUE_APP_BASE_URL || '/api';

//import store from './views/store';

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI);
Vue.config.productionTip = false

new Vue({
  store,
  router,
  //store,
  render: h => h(App)
}).$mount('#app')
