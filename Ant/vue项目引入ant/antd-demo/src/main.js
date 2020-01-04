import Vue from 'vue'
import App from './App.vue'
import store from './store'
import Router from 'vue-router'
import 'ant-design-vue/dist/antd.css';
import Antd from 'ant-design-vue';
import router from './router/index.js'

Vue.use(Router)
Vue.config.productionTip = false

Vue.use(Antd);

new Vue({
  render: h => h(App),
  router,
  //需要将store和vue实例进行关联，这里将其传递进去
  store
}).$mount('#app')
