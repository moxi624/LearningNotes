# Vue如何使用G2绘制图片

## 使用vue cli创建项目

```
# 全局安装脚手架
npm install -g @vue/cli-init
# 初始化项目
vue init webpack-simple my-g2-study
# 进入目录
cd my-g2-study
# 安装g2依赖
yarn add @antv/g2
```

## 项目启动

首先使用下面启动项目

```
yarn run serve
```

然后使用webstorm打开项目目录，我们需要在components下，创建一个文件HelloG2.vue

### 引用G2包

```
import G2 from '@antv/g2';
```

### 创建div图表容器

我们需要在页面的body部分创建一个div，然后设置id

```
<div id="c1"></div>
```

### 编写图表绘制代码

创建 `div` 容器后，我们就可以进行简单的图表绘制，主要分为下面几步：

1. 创建 Chart 图表对象，指定图表所在的容器 ID、指定图表的宽高、边距等信息；
2. 载入图表数据源；
3. 使用图形语法进行图表的绘制；
4. 渲染图表

完整代码如下所示：

```vue
<template>
    <div id="c1"></div>
</template>

<script>
    import G2 from '@antv/g2';

    export default {
        name: 'HelloG2',
        props: {
            msg: String
        },
        mounted() {
            const data = [
                {genre: 'Sports', sold: 275},
                {genre: 'Strategy', sold: 115},
                {genre: 'Action', sold: 120},
                {genre: 'Shooter', sold: 350},
                {genre: 'Other', sold: 150},
            ];
            // G2 对数据源格式的要求，仅仅是 JSON 数组，数组的每个元素是一个标准 JSON 对象。

            // Step 1: 创建 Chart 对象
            const chart = new G2.Chart({
                container: 'c1',
                width: 600,
                height: 300,
            });

            // Step 2: 载入数据源
            chart.source(data);

            // Step 3：创建图形语法，绘制柱状图，由 genre 和 sold 两个属性决定图形位置，genre 映射至 x 轴，sold 映射至 y 轴
            chart
                .interval()
                .position('genre*sold')
                .color('genre');

            // Step 4: 渲染图表
            chart.render();
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>

```

然后在App.vue引用该组件即可

```vue
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

```

最后效果图：

![image-20191222163912882](.\images\image-20191222163912882.png)

