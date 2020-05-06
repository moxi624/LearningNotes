前言
--

之前制作了第三方登录页面，每次点击的时候，都会出现这个错误：Uncaught TypeError: Cannot read property 'disabled' of null

![](http://image.moguit.cn/1577929824870.png)

之前以为是因为我给input组件设置了disabled的原因

    <el-button type="success" circle disabled>
      <span class="iconfont">&#xe66f;</span>
    </el-button>

例如我对上面设置了disabled属性，让按钮无法被点击，但是我把全部的disabled都给删除后，还是有这样的错误

 经过了排查，发现是因为element-ui的其它组件而引起的：[Dropdown 下拉菜单](https://element.eleme.cn/#/zh-CN/component/dropdown)

引入的代码如下所示：

    <el-dropdown @command="handleCommand" class="userInfoAvatar">
      <span class="el-dropdown-link" @click="userLogin">
        <img v-if="!isLogin" src="../../static/images/defaultAvatar.png">
        <img v-if="isLogin&&userInfo.photoUrl!=undefined" :src="PICTURE_HOST + userInfo.photoUrl">
        <img v-if="isLogin&&userInfo.photoUrl==undefined"
             src="https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif">
      </span>
    
      <el-dropdown-menu slot="dropdown" v-if="isLogin">
        <el-dropdown-item command="goUserInfo">主页</el-dropdown-item>
        <el-dropdown-item command="logout">退出</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>

从上面可以看出，当 isLogin = false的时候，el-dropdown-menu是不会被渲染出来的，那么就会存在问题了

因为el-dropdown如果没有设置它的子元素，就会报错，也就是刚刚我们看到的那个Uncaught TypeError: Cannot read property 'disabled' of null错误

我们只需要把原来的v-if改成v-show即可，如下所示：

    <el-dropdown-menu slot="dropdown" >
      <el-dropdown-item command="goUserInfo" v-show="isLogin">主页</el-dropdown-item>
      <el-dropdown-item command="logout" v-show="isLogin">退出</el-dropdown-item>
    </el-dropdown-menu>

那么错误就消失了~！