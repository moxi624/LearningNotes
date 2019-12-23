import Vue from 'vue'
import App from './App.vue'

Vue.config.productionTip = false
import echarts from 'echarts'

Vue.use(echarts)

new Vue({
  render: h => h(App),
}).$mount('#app')
