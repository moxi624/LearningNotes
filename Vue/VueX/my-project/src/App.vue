<template>
  <div id="app">
    <h1>{{count}}</h1>
    <h1>{{myCount}}</h1>
    <button @click="incrementClick">+</button>
    <button @click="decrementClick">-</button>
    <button @click="incrementClickN">+N</button>
  </div>
</template>

<script>

  // vuex中有mapState方法，相当于我们能够使用它的getset方法
  import {mapState, mapGetters, mapMutations, mapActions} from 'vuex';
  import {INCREMENT, DECREMENT, INCREMENT_N} from "./store/mutation-types"

  export default {
    name: 'app',
    data() {
      return {
        msg: 'Welcome to Your Vue.js App'
      }
    },
    computed: {
      // 在计算属性下，我们通过mapState(['count'])获取的是数组，然后通过...解析
      // ...mapState(['count']),
      // ...mapGetters(['myCount'])

      ...mapState({
        count: state => {
          return state.app.count
        }
      }),
      ...mapGetters(['myCount'])
    },
    methods: {

      //拿到vuex中的写的两个方法
      ...mapMutations([INCREMENT, DECREMENT]),
      // 拿到vuex中actions写的两个自定义方法
      ...mapActions(['myIncrement', "myDecrement", "myIncrementN"]),

      incrementClick: function () {
        // 通过store，来调用increment
        // this.$store.commit("increment");

        this.myIncrement();

      },
      decrementClick: function () {
        // this.$store.commit("decrement");

        this.myDecrement();

      },
      incrementClickN: function() {
        let N = 10;
        this.myIncrementN(N);
      },
    }
  }
</script>

<style>

</style>
