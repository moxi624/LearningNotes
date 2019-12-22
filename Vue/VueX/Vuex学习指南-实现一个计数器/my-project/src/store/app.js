import {INCREMENT, DECREMENT, INCREMENT_N} from "./mutation-types";

const app = {
  // 全局状态
  state: {
    count: 0
  },
  // getters是对数据的包装，例如对数据进行拼接，或者过滤
  getters: {
    //类似于计算属性
    myCount(state) {
      return `current count is ${state.count}`
    }
  },
  // 如果我们需要更改store中的状态，一定要通过mutations来进行操作
  mutations: {
    // 增加的方法
    [INCREMENT](state) {
      state.count += 1
    },
    // 减少的方法
    [DECREMENT](state) {
      state.count -= 1
    },
    // 传入自定义参数
    [INCREMENT_N](state, N) {
      state.count += N
    }
  },
  // actions是我们定义的一些操作，正常情况下，我们很少会直接调用mutation方法来改变state
  actions: {
    // 编写业务代码
    myIncrement: function (context) {
      // 进行一系列的计算
      context.commit(INCREMENT)
    },
    myIncrementN: function (context, N) {
      // 这里在提交的时候，我们就可以在添加一个参数
      console.log("传递过来的N", N);
      context.commit(INCREMENT_N, N)
    },
    myDecrement: function (context) {
      // 进行一系列的状态
      context.commit(DECREMENT)
    }
  }
}
export default app
