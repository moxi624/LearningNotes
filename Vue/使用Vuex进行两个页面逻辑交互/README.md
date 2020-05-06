前言
--

今天在写前端页面的时候，遇到这样一个问题，就是当一个页面由两个Vue文件构成的时候，如果在一个vue文件的时候进行了操作，那么需要将操作得到的数据传递给另外一个文件，那么另外页面就需要能够监听到前面这个页面的数据变化

这里以蘑菇博客举例，我们都知道蘑菇博客的头部是单独写在一个组件中，而下面的index的页面，又是另外一个vue文件，那么我在页面1点击搜索，页面2如何知道搜索的值呢？

![](http://picture.moguit.cn//blog/admin/png/2020/3/7/1583583658750.png)

解决方案
----

其实解决的方案就是通过vuex来进行实现，步骤如下：

*   页面1当点击提交按钮的时候，调用vuex的保存方法，将文本内容存储到vuex中
*   然后页面2使用watch钩子函数，监听 vuex中内容的变化，如果改变了，那么就执行对应的函数

具体代码如下
------

关于vuex的使用，可以参考：[Vuex学习指南-实现一个计数器](http://www.moguit.cn/#/info?blogUid=a00ebe1473c584ff94bdd40402a4d573)

首先我们需要定义一个message状态，用于存储我们需要发送的内容

    import {SET_MESSAGE} from './mutation-types'
    
    const app = {
      // 全局状态
      state: {
        // 消息，用于更新
        message: {}
      },
      // getters是对数据的包装，例如对数据进行拼接，或者过滤
      getters: {
        // 类似于计算属性
        // 增加的方法
      },
      // 如果我们需要更改store中的状态，一定要通过mutations来进行操作
      mutations: {
    
        [SET_MESSAGE] (state, message) {
          state.message = message
        }
      },
    
      // actions是我们定义的一些操作，正常情况下，我们很少会直接调用mutation方法来改变state
      actions: {
    
      }
    }
    export default app
    

然后在页面1的时候引入

    // vuex中有mapState方法，相当于我们能够使用它的getset方法
    import {mapMutations} from 'vuex'

然后在method方法中，解析出刚刚我们定义的setMessage

    methods:{
        // 拿到vuex中的写的方法
        ...mapMutations(['setMessage']),
    }

最后在我们点击的时候，调用setMessage方法

    // 存储到vuex中
    this.setMessage(message)
    

这样，我们的消息就放到了vuex中了，下面我们在页面2，添加watch的钩子函数，用于监听vuex中message状态的变化，如果改变了就会触发，那么我们就可以在这里编写业务逻辑代码了

      watch: {
        '$store.state.app.message': function (newFlag, oldFlag) {
             console.log('监听到页面1的变化')
             // 进行业务逻辑处理
         }
    }