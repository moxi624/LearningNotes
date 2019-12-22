<template>
  <div id="app">
    <HelloG2 msg="Welcome to Your Vue.js App"/>
  </div>
</template>

<script>
import HelloG2 from './components/HelloG2.vue'

export default {
  name: 'app',
  components: {
    HelloG2
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
