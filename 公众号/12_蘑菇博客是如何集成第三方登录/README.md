## 前言

大家好，我是**陌溪**

这篇主要给搭建讲解的是，蘑菇博客项目是如何集成第三方登录。陌溪在做第三方登录的时候，也没有上来就造轮子，而是先在 **Gitee** 中找到了一个第三方登录的开源库：**JustAuth**。

**JustAuth**，如你所见，它仅仅是一个**第三方授权登录**的**工具类库**，它可以让我们脱离繁琐的第三方登录 SDK，让登录变得 **So easy!**  **JustAuth** 集成了诸如：**Github**、**Gitee**、支付宝、新浪微博、微信、Google、Facebook、Twitter、StackOverflow等国内外数十家第三方平台。

>JustAuth仓库：https://gitee.com/yadong.zhang/JustAuth
>
>JustAuth文档：https://docs.justauth.whnb.wang/#/

## 编写登录页面

首先需要编写一个登录框代码，下面是使用 Vue 创建了一个组件 **LoginBox.vue**，同时里面还引入了**阿里矢量库**中的几个图标，感兴趣的小伙伴可以查看这篇博客：[蘑菇博客前端页面如何引入矢量图标](https://mp.weixin.qq.com/s?__biz=MzkyMzE5NTYzMA==&tempkey=MTA5NV82TGI3dzYxV1kybWV3NUlQMHFOSm1Ma1g4NUVMZFZuUzhWSzhfMUNMS0VDZ0ZWX253VTFUdUdrY3BJLUZ3cTMwYUhCSzBvY1RpQzNsSGlBOThjZXVKTUhuNThGVmJMMjdrR1VoVDJnV2dqR3FaRHZBRnpzRGdQekZyVlRoYVRFcDZaUVBiTkV6SkI3QkpjRkxTU2U5cFM5aGNTdjM5TFZQMGY2WkdRfn4%3D&chksm=41e9871a769e0e0c61b80fba64b260d0c1065bcfeca47c34a615f3c971f2bf1ead248b3e7367#rd)

```html
<template>
  <div>
    <div class="box loginBox" v-if="showLogin == true">
      <div class="title"  >
        <span class="t1">
          登录
        </span>
        <div class="t2" @click="closeLogin()">
          X
        </div>
      </div>
      <el-divider></el-divider>
      <el-form :label-position="labelPosition" :model="loginForm">
        <el-form-item label="用户名">
          <el-input v-model="loginForm.userName" disabled></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input type="password" v-model="loginForm.password" disabled></el-input>
        </el-form-item>
        <el-row class="btn">
          <el-button class="loginBtn" type="primary" @click="startLogin" disabled>登录</el-button>
          <el-button class="registerBtn" type="info" @click="goRegister" disabled>注册</el-button>
        </el-row>

        <el-row class="elRow">
          <el-tooltip content="码云" placement="bottom">
            <el-button type="danger" circle @click="goAuth('gitee')">
              <span class="iconfont">&#xe602;</span>
            </el-button>
          </el-tooltip>

          <el-tooltip content="Github" placement="bottom">
            <el-button type="info" circle @click="goAuth('github')">
              <span class="iconfont">&#xe64a;</span>
            </el-button>
          </el-tooltip>

          <el-tooltip content="QQ" placement="bottom">
            <el-button type="primary" circle disabled>
              <span class="iconfont">&#xe601;</span>
            </el-button>
          </el-tooltip>

          <el-tooltip content="微信" placement="bottom">
            <el-button type="success" circle disabled>
              <span class="iconfont">&#xe66f;</span>
            </el-button>
          </el-tooltip>

        </el-row>
        <div class="loginTip">登录过的用户请沿用之前的登录方式</div>
      </el-form>
    </div>

    <div class="box registerBox" v-if="showLogin == false">
      <div class="title">
        <span class="t1">
          登录
        </span>
        <div class="t2" @click="closeLogin()">
          X
        </div>
      </div>
      <el-divider></el-divider>
      <el-form :label-position="labelPosition" :model="registerForm">
        <el-form-item label="用户名">
          <el-input v-model="registerForm.userName"></el-input>
        </el-form-item>

        <el-form-item label="密码">
          <el-input type="password" v-model="registerForm.password"></el-input>
        </el-form-item>

        <el-form-item label="重复密码">
          <el-input type="password" v-model="registerForm.password2"></el-input>
        </el-form-item>

        <el-form-item label="邮箱">
          <el-input v-model="registerForm.email"></el-input>
        </el-form-item>

        <el-row class="btn">
          <el-button class="loginBtn" type="primary" @click="startRegister">注册</el-button>
          <el-button class="registerBtn" type="info" @click="goLogin">返回登录</el-button>
        </el-row>

        <div class="loginTip">注册后，需要到邮箱进行邮件认证~</div>
      </el-form>
    </div>

    <div class="mask"></div>

  </div>
</template>

<script>
  import {login, register} from "@/api/user";

  export default {
    name: "share",
    data() {
      return {
        // 显示登录页面
        showLogin: true,
        isLogin: false,
        table: false,
        dialog: false,
        loading: false,
        labelPosition: "right",
        loginForm: {
          userName: "",
          password: ""
        },
        registerForm: {
          userName: "",
          password: "",
          password2: "",
          email: ""
        }
      };
    },
    components: {},
    created() {
    },
    methods: {
      startLogin: function () {
        var params = {};
        params.userName = this.loginForm.userName;
        params.passWord = this.loginForm.password;
        params.isRememberMe = 0;
        console.log("登录表单", params);
        login(params).then(response => {
          if (response.code == "success") {
            console.log(response.data);
          }
        });
      },
      startRegister: function () {

        var params = {};
        params.userName = this.registerForm.userName;
        params.passWord = this.registerForm.password;
        params.email = this.registerForm.email;
        console.log("登录表单", params);
        register(params).then(response => {
          if (response.code == "success") {
            console.log(response.data);
          }
        });
      },
      goLogin: function () {
        console.log("去登录页面");
        this.showLogin = true;
      },
      goRegister: function () {
        console.log("去注册页面");
        this.showLogin = false;
      },
      goAuth: function (source) {
        console.log("go", source)
        var params = new URLSearchParams();
        params.append("source", source);
        login(params).then(response => {
          if (response.code == "success") {
            console.log(response.data.url);
            var token = response.data.token;
            console.log(response);
            window.location.href = response.data.url
          }
        });
      },
      closeLogin: function() {
        this.$emit("closeLoginBox", "");
      }
    }
  };
</script>


<style>
  .box {
    width: 400px;
    height: 420px;
    background: white;
    position: fixed;
    margin: auto;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    z-index: 1000; /* 要比遮罩层大 */
  }

  .registerBox {
    height: 570px;
  }

  .box .title {
    height: 48px;
    font-size: 22px;
    font-weight: bold;
    text-align: center;
    line-height: 48px;
  }
  .box .title .t2 {
    font-size: 16px;
    float: right;
    margin-right: 6px;
    margin-top: -6px;
    cursor: pointer;
  }

  .box .el-divider--horizontal {
    margin: 12px 0;
  }

  .box .el-form-item__label {
    margin-left: 10px;
    font-size: 16px;
  }

  .box .el-input__inner {
    margin-left: 10px;
    width: 90%;
  }

  .box .btn {
    text-align: center;
  }

  .box .loginBtn {
    width: 40%;
  }

  .box .registerBtn {
    width: 40%;
  }

  .elRow {
    margin-top: 15px;
    text-align: center;
  }

  .loginTip {
    margin-top: 10px;
    font-size: 14px;
    text-align: center;
    color: #bababa;
  }

  .remarksBox {
    position: fixed;
    left: 50%;
    margin-left: -100px;
    top: 50%;
    margin-top: -50px;
    border: 1px solid red;
    width: 200px;
    height: 100px;
    text-align: center;
    z-index: 1000; /* 要比遮罩层大 */
  }

  /* 遮罩层 */
  .mask {
    position: fixed;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    z-index: 999;
  }
</style>
```

下面是运行后的结果如下所示

![img](images/1577773839268.png)

下面是第三方登录的请求接口

```javascript
import request from '@/utils/request'

export function login(params) {
  return request({
    url: process.env.WEB_API + '/oauth/render',
    method: 'post',
    params
  })
}
```

## 引入第三方登录

完成了前端的页面后，我们就需要撰写后端代码了

首先需要引入 **JustAuth** 的 **Maven** 依赖，在 **pom** 文件中添加对应依赖

```xml
<!--JustAuth第三方登录模块-->
<dependency>
    <groupId>me.zhyd.oauth</groupId>
    <artifactId>JustAuth</artifactId>
    <version>1.13.1</version>
</dependency>
```

然后在编写一个用于处理第三方登录的 **Controller** 控制器 ，这里我创建了 **AuthRestApi.java** 文件

```java
/**
 * 第三方登录认证
 */
@RestController
@RequestMapping("/oauth")
@Api(value = "认证RestApi", tags = {"AuthRestApi"})
public class AuthRestApi {

    private static Logger log = LogManager.getLogger(IndexRestApi.class);

    @Autowired
    private UserService userService;
    @Value(value = "${justAuth.clientId.gitee}")
    private String giteeClienId;
    @Value(value = "${justAuth.clientSecret.gitee}")
    private String giteeClientSecret;
    @Value(value = "${justAuth.clientId.github}")
    private String githubClienId;
    @Value(value = "${justAuth.clientSecret.github}")
    private String githubClientSecret;
    @Value(value = "${data.webSite.url}")
    private String webSiteUrl;
    @Value(value = "${data.web.url}")
    private String moguWebUrl;
    @Value(value = "${BLOG.USER_TOKEN_SURVIVAL_TIME}")
    private Long userTokenSurvivalTime;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @ApiOperation(value = "获取认证", notes = "获取认证")
    @RequestMapping("/render")
    public String renderAuth(String source, HttpServletResponse response) throws IOException {
        log.info("进入render:" + source);
        AuthRequest authRequest = getAuthRequest(source);
        String token = AuthStateUtils.createState();
        String authorizeUrl = authRequest.authorize(token);
        Map<String, String> map = new HashMap<>();
        map.put(SQLConf.URL, authorizeUrl);
        return ResultUtil.result(SysConf.SUCCESS, map);
    }

    /**
     * oauth平台中配置的授权回调地址，以本项目为例，在创建gitee授权应用时的回调地址应为：http://127.0.0.1:8603/oauth/callback/gitee
     */
    @RequestMapping("/callback/{source}")
    public void login(@PathVariable("source") String source, AuthCallback callback, HttpServletRequest request, HttpServletResponse httpServletResponse) throws IOException {
        log.info("进入callback：" + source + " callback params：" + JSONObject.toJSONString(callback));
        AuthRequest authRequest = getAuthRequest(source);
        AuthResponse response = authRequest.login(callback);
        String result = JSONObject.toJSONString(response);
        System.out.println(JSONObject.toJSONString(response));

        Map<String, Object> map = JsonUtils.jsonToMap(result);
        Map<String, Object> data = JsonUtils.jsonToMap(JsonUtils.objectToJson(map.get(SysConf.DATA)));
        Map<String, Object> token = JsonUtils.jsonToMap(JsonUtils.objectToJson(data.get(SysConf.TOKEN)));
        String accessToken = token.get(SysConf.ACCESS_TOKEN).toString();
        User user = userService.insertUserInfo(request, result);

        if (user != null) {
            //将从数据库查询的数据缓存到redis中
            stringRedisTemplate.opsForValue().set(SysConf.USER_TOEKN + SysConf.REDIS_SEGMENTATION + accessToken, JsonUtils.objectToJson(user), userTokenSurvivalTime, TimeUnit.SECONDS);
        }

        httpServletResponse.sendRedirect(webSiteUrl + "?token=" + accessToken);
    }

    @RequestMapping("/revoke/{source}/{token}")
    public Object revokeAuth(@PathVariable("source") String source, @PathVariable("token") String token) throws IOException {
        AuthRequest authRequest = getAuthRequest(source);
        return authRequest.revoke(AuthToken.builder().accessToken(token).build());
    }

    @RequestMapping("/refresh/{source}")
    public Object refreshAuth(@PathVariable("source") String source, String token) {
        AuthRequest authRequest = getAuthRequest(source);
        return authRequest.refresh(AuthToken.builder().refreshToken(token).build());
    }

    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    @GetMapping("/verify/{accessToken}")
    public String verifyUser(@PathVariable("accessToken") String accessToken) {
        String userInfo = stringRedisTemplate.opsForValue().get("TOKEN:" + accessToken);
        if (StringUtils.isEmpty(userInfo)) {
            return ResultUtil.result(SysConf.ERROR, MessageConf.INVALID_TOKEN);
        } else {
            Map<String, Object> map = JsonUtils.jsonToMap(userInfo);
            return ResultUtil.result(SysConf.SUCCESS, map);
        }
    }

    @ApiOperation(value = "删除accessToken", notes = "删除accessToken")
    @RequestMapping("/delete/{accessToken}")
    public String deleteUserAccessToken(@PathVariable("accessToken") String accessToken) {
        stringRedisTemplate.delete(SysConf.USER_TOEKN + SysConf.REDIS_SEGMENTATION + accessToken);
        return ResultUtil.result(SysConf.SUCCESS, MessageConf.DELETE_SUCCESS);
    }


    private AuthRequest getAuthRequest(String source) {
        AuthRequest authRequest = null;
        switch (source) {
            case SysConf.GITHUB:
                authRequest = new AuthGithubRequest(AuthConfig.builder()
                        .clientId(githubClienId)
                        .clientSecret(githubClientSecret)
                        .redirectUri(moguWebUrl + "/oauth/callback/github")
                        .build());
                break;
            case SysConf.GITEE:
                authRequest = new AuthGiteeRequest(AuthConfig.builder()
                        .clientId(giteeClienId)
                        .clientSecret(giteeClientSecret)
                        .redirectUri(moguWebUrl + "/oauth/callback/gitee")
                        .build());
                break;
            default:
                break;
        }
        if (null == authRequest) {
            throw new AuthException(MessageConf.OPERATION_FAIL);
        }
        return authRequest;
    }
}
```

**application.yml** 部分配置文件如下所示：

```yml
data:
  # 门户页面
  webSite:
    url: http://localhost:9527/#/
  # mogu_web网址，用于第三方登录回调
  web:
    url: http://127.0.0.1:8603
    
# 第三方登录
justAuth:
  clientId:
    gitee: XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    github: XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
  clientSecret:
    gitee: XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    github: XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX 
```

## Gitee获取授权密钥

在码云中：我们首先进入设置页面，然后选择第三方应用，然后创建应用


![img](images/1577774049160.png)

然后开始填写对应的内容

![img](images/1577774070401.png)

重新点击第三方应用，获取到对应的ClientID和Client Secret替换即可

![img](images/1577774097799.png)

Github上的操作同理，我们需要设置setting，然后选择Developer settings，OAuth Apps：创建一个新的

![img](images/1577774134599.png)

这里填写的信息和刚刚码云上差不多，最后一个 **Authorization callback URL** 需要改成 **Github** 的回调地址

```bash
http://127.0.0.1:8603/oauth/callback/github
```

页面如下所示

![img](images/1577774162993.png)

然后最后在创建成功后复制对应的 **ClientID** 和 **Client Secret** 即可：

![img](images/1577774183497.png)

## 关于AuthRestApi中方法的作用

在 **AuthRestApi** 中，下面几个方法的主要作用是：

- **renderAuth**：获取认证，前端通过 **login** 方法，即访问的是该接口，然后会创建一个认证请求，里面调用了**getAuthRequest** 方法
- **getAuthRequest**：该方法需要传入一个 **source** 参数，该参数主要是失败用户请求的接口，然后封装一个 **URL**，最后通过 **renderAuth** 返回到前台页面中，该方法前端接受后，最终会生成一个 **URL** ，然后跳转到对应的页面进行授权即可

例如下面的 **vue** 代码：

```
    goAuth: function (source) {
        var params = new URLSearchParams();
        params.append("source", source);
        login(params).then(response => {
          if (response.code == "success") {
            console.log(response.data.url);
            var token = response.data.token;
            console.log(response);
            window.location.href = response.data.url
          }
        });
      },
```

vue代码，就是通过source判断我点击的按钮，如果是github，那么source为 ”github“，然后调用后台的登录方法，通过传递的source，生成一个授权页面url，最后我们通过window.location.href 跳转到授权页面：

![img](images/1577774935816.png)

回调的接口如下所示：

```java
 /**
     * oauth平台中配置的授权回调地址，以本项目为例，在创建gitee授权应用时的回调地址应为：http://127.0.0.1:8603/oauth/callback/gitee
     */
    @RequestMapping("/callback/{source}")
    public void login(@PathVariable("source") String source, AuthCallback callback, HttpServletRequest request, HttpServletResponse httpServletResponse) throws IOException {
        log.info("进入callback：" + source + " callback params：" + JSONObject.toJSONString(callback));
        AuthRequest authRequest = getAuthRequest(source);
        AuthResponse response = authRequest.login(callback);
        String result = JSONObject.toJSONString(response);
        System.out.println(JSONObject.toJSONString(response));

        Map<String, Object> map = JsonUtils.jsonToMap(result);
        Map<String, Object> data = JsonUtils.jsonToMap(JsonUtils.objectToJson(map.get(SysConf.DATA)));
        Map<String, Object> token = JsonUtils.jsonToMap(JsonUtils.objectToJson(data.get(SysConf.TOKEN)));
        String accessToken = token.get(SysConf.ACCESS_TOKEN).toString();
        User user = userService.insertUserInfo(request, result);

        if (user != null) {
            //将从数据库查询的数据缓存到redis中
            stringRedisTemplate.opsForValue().set(SysConf.USER_TOEKN + SysConf.REDIS_SEGMENTATION + accessToken, JsonUtils.objectToJson(user), userTokenSurvivalTime, TimeUnit.SECONDS);
        }

        httpServletResponse.sendRedirect(webSiteUrl + "?token=" + accessToken);
    }
```

我们需要将得到的用户信息，存储到数据库，同时生成一个token，通过url的方式，传递到前台，然后前台得到token后，通过token获取用户信息：

```java
    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    @GetMapping("/verify/{accessToken}")
    public String verifyUser(@PathVariable("accessToken") String accessToken) {
        String userInfo = stringRedisTemplate.opsForValue().get("TOKEN:" + accessToken);
        if (StringUtils.isEmpty(userInfo)) {
            return ResultUtil.result(SysConf.ERROR, MessageConf.INVALID_TOKEN);
        } else {
            Map<String, Object> map = JsonUtils.jsonToMap(userInfo);
            return ResultUtil.result(SysConf.SUCCESS, map);
        }
    }
```

然后在vue项目中，我们只需要判断是否有token通过url传递过来

```javascript
 let token = this.getUrlVars()["token"];
      // 判断url中是否含有token
      if (token != undefined) {
        setCookie("token", token, 1)
      }

      // 从cookie中获取token
      token = getCookie("token")
      if (token != undefined) {
        authVerify(token).then(response => {
          if (response.code == "success") {
            this.isLogin = true;
            this.userInfo = response.data;
          } else {
            this.isLogin = false;
            delCookie("token");
          }
        });
      } else {
        this.isLogin = false;
 }
```

如果有，那么就通过token获取用户信息，登录完成后，就能够看到头像回显了：

![img](images/1577775104658.png)