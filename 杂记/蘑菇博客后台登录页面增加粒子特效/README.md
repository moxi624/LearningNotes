# 蘑菇博客后台登录页面增加粒子特效

## 前言

这两天有个老哥给我推荐博客的后台登录页面添加粒子特效，后面看了看效果还不错，就把打加入到博客的后台登录页面中了，下面看添加后的效果图：

![111](images/111.gif)

图上那些类似于星座图的点和线，是由vue-particles生成的，不仅自己动，而且能与用户鼠标事件产生互动。

## 添加粒子特效

安装 vue-particles依赖

```
npm install vue-particles --save-dev
```

main.js引入

```
import VueParticles from 'vue-particles'  
Vue.use(VueParticles)  
```

在index.vue页面中引入

```html
<vue-particles
    color="#fff"
    :particleOpacity="0.7"
    :particlesNumber="60"
    shapeType="circle"
    :particleSize="4"
    linesColor="#fff"
    :linesWidth="1"
    :lineLinked="true"
    :lineOpacity="0.4"
    :linesDistance="150"
    :moveSpeed="2"
    :hoverEffect="true"
    hoverMode="grab"
    :clickEffect="true"
    clickMode="push"
    class="lizi"
>
</vue-particles>
```

相关配置分别为：

- `color: String类型。`默认'#dedede'。粒子颜色。
- `particleOpacity: Number类型。`默认0.7。粒子透明度。
- `particlesNumber: Number类型。`默认80。粒子数量。
- `shapeType: String类型。`默认'circle'。可用的粒子外观类型有："circle","edge","triangle", "polygon","star"。
- `particleSize: Number类型。`默认80。单个粒子大小。
- `linesColor: String类型。`默认'#dedede'。线条颜色。
- `linesWidth: Number类型。`默认1。线条宽度。
- `lineLinked: 布尔类型。`默认true。连接线是否可用。
- `lineOpacity: Number类型。`默认0.4。线条透明度。
- `linesDistance: Number类型。`默认150。线条距离。
- `moveSpeed: Number类型。`默认3。粒子运动速度。
- `hoverEffect: 布尔类型。`默认true。是否有hover特效。
- `hoverMode: String类型。`默认true。可用的hover模式有: "grab", "repulse", "bubble"。
- `clickEffect: 布尔类型。`默认true。是否有click特效。
- `clickMode: String类型。`默认true。可用的click模式有: "push", "remove", "repulse", "bubble"

## 引入时需要注意

在引入到页面的时候，我们需要注意，不能够直接放在页面的最上方，这样会显示不了我们写的页面，因为这个粒子特效是向上覆盖的，所以我们需要在我们页面的最下方引入才行，如我的页面所示：

```
<template>
  <div class="login-container">
    <el-form
      ref="loginForm"
      :model="loginForm"
      :rules="loginRules"
      class="login-form"
      auto-complete="on"
      label-position="left"
    >
      <h3 class="title">蘑菇博客后台管理系统</h3>
      <el-form-item prop="username">
        <span class="svg-container svg-container_login">
          <svg-icon icon-class="user"/>
        </span>
        <el-input
          v-model="loginForm.username"
          name="username"
          type="text"
          auto-complete="on"
          placeholder="username"
        />
      </el-form-item>
      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password"/>
        </span>
        <el-input
          :type="pwdType"
          v-model="loginForm.password"
          name="password"
          auto-complete="on"
          placeholder="password"
          @keyup.enter.native="handleLogin"
        />
        <span class="show-pwd" @click="showPwd">
          <svg-icon icon-class="eye"/>
        </span>
      </el-form-item>
      <el-form-item>
        <el-button
          :loading="loading"
          type="primary"
          style="width:100%;"
          @click.native.prevent="handleLogin"
        >登 录</el-button>
      </el-form-item>
    </el-form>
    <!--引入粒子特效-->
    <vue-particles
      color="#fff"
      :particleOpacity="0.7"
      :particlesNumber="60"
      shapeType="circle"
      :particleSize="4"
      linesColor="#fff"
      :linesWidth="1"
      :lineLinked="true"
      :lineOpacity="0.4"
      :linesDistance="150"
      :moveSpeed="2"
      :hoverEffect="true"
      hoverMode="grab"
      :clickEffect="true"
      clickMode="push"
      class="lizi"
    >
    </vue-particles>
  </div>
</template>
```

## 参考

vue-particles官方地址：https://vue-particles.netlify.com/

