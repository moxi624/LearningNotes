# Vue中对数组变化监听

## 前言

我们在实际开发中，经常要对数组进行操作，最为常见的方法就是直接对数组中的某个元素进行赋值，比如下面这样的：

```javascript
<script>
    export default {
        data() {
            return {
                comments: [],
                isReply: [0, 0, 0, 0, 0]
            };
        },
        methods: {
            replyTo: function (index) {
                console.log(this.isReply);
                isReply[index] = 1
                console.log(this.isReply);
            }
        },
    };
</script>
```

但是虽然数组中的元素改变了，但是vue却不能监听到变化，同时我们引入watch来检测也是没有效果，如下所示

```javascript
<script>
    export default {
        data() {
            return {
                comments: [],
                isReply: [0, 0, 0, 0, 0]
            };
        },
      watch: {
        isReply: {
          handler(val, oldval) {

          },
          deep: true
        }
      },
        methods: {
            replyTo: function (index) {
                console.log(this.isReply);
                isReply[index] = 1
                console.log(this.isReply);
            }
        },
    };
</script>
```

从上述例子，我们发现vue是不会响应数据变化而重新去渲染页面。在vue中仅需要通过修改赋值语句的方式，即可让vue响应数组数据的变化。具体操作如下：

```
// Vue.set
Vue.set(vm.items, indexOfItem, newValue)

// Array.prototype.splice
vm.items.splice(indexOfItem, 1, newValue)
```

具体案例如下

```
<script>
    import Comment from '../components/Comment'

    export default {
        data() {
            return {
                comments: [],
                isReply: [0, 0, 0, 0, 0]
            };
        },
        watch: {

        },
        components: {
            Comment
        },
        methods: {
            replyTo: function (index) {
				
				// 最后一个参数为我们需要改变的值
                this.isReply.splice(index, 1, 1);

                this.$set(this.isReply, index, 1);

            }
        },
    };
</script>
```
