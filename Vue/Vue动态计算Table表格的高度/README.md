前言
--

因为每个用户不同的电脑屏幕宽高度，造成了Table表格的高度不一致，因此想要动态计算出table的高度，让其能够正常的铺满整个屏幕

代码
--

![](http://image.moguit.cn/2652bc26060547bf8fabb9636979303a)

完整代码如下：首先计算  窗口的高度 - 搜索框的高度 - 固定数值

```js
  mounted () {
    // 计算搜索框的高度
    var searchBarHeight = window.getComputedStyle(this.$refs.searchBar).height.replace('px', '')
    searchBarHeight = parseInt(searchBarHeight)
    this.tableHeight = window.innerHeight - searchBarHeight - 270
  },
```
