# 如何使用docsify给蘑菇博客编写开发文档

## 前言

最近看到很多开源项目都有自己的帮助文档，如下图所示，因为它确实能够方便大家进行文档的检索

![image-20200209103832912](images/image-20200209103832912.png)

后面就开始琢磨着，自己也编写一个帮助文档，来展示蘑菇博客的一些配置博客都放在上面，这样即使网站宕机了，也能够查看到相关的文档

经过技术调查，发现别人开源项目的文档都是通过docsify进行编写的

官网：https://docsify.js.org/#/

下面看最终的效果图：

![image-20200210133005865](images/image-20200210133005865.png)

有了 docsify我们就可以编写和下图一样的文档了，方便文档的查阅与长期维护。

![image-20200209105342392](images/image-20200209105342392.png)

## 安装

首先需要安装docsify-cli脚手架，用于初始化docsify文档项目

```
npm i docsify-cli -g
```

然后初始化

```
docsify init ./docs
```

初始化后，我们就能看到 docs文件夹，里面含有下面内容

![image-20200209105721071](images/image-20200209105721071.png)

目录结构如下所示

- index.html 入口文件
- README.md 会做为主页内容渲染
- .nojekyll 用于阻止 GitHub Pages 会忽略掉下划线开头的文件 (.后缀结尾的是隐藏文件)

## 启动

在我们使用init命令初始化一个文档后，我们需要通过下面命令运行一个本地服务器

```
docsify serve
```

项目启动后，默认访问 [http://localhost:3000](http://localhost:3000/) ，如下图所示，我们能够看到一个帮助文档的骨架了

![image-20200209110442467](images/image-20200209110442467.png)

同时docsify还提供了LiveReload功能，也就是可以在我们修改文档后，能够实时预览

## 编辑

### 修改Loading

初始化时会显示 Loading... 内容，你可以自定义提示信息，我们只需要修改index.html中的<div id='app'>标签即可，在里面加入我们需要的提示内容

![image-20200209110822841](images/image-20200209110822841.png)

### 定制侧边栏

默认情况下，侧边栏会根据当前文档的标题生成目录，也可以通过设置文档链接，通过Markdown文件生成，效果如当前的文件的侧边栏，首先我们在index.html里面进行设置

```
    window.$docsify = {
      loadSidebar: true, // 设置侧边栏
    }
```

然后新增一个markdown文件`_sidebar.md` , 如我的侧边栏为下面所示，其实是使用了超链接，每个目录都链接到我的目录下的markdown文件。

```
- [**蘑菇博客**](README.md)

- **文档**

  - [项目介绍](doc/文档/项目介绍.md)
  - [技术选型](doc/文档/技术选型.md)
  - 项目搭建
    - [Windows环境下搭建蘑菇博客](doc/文档/项目搭建/Windows环境下搭建蘑菇博客/README.md)
    - [Docker搭建蘑菇博客](doc/文档/项目搭建/Docker搭建蘑菇博客/README.md)
    - [蘑菇博客部署到云服务器](doc/文档/项目搭建/蘑菇博客部署到云服务器/README.md)
    - [Github Actions完成蘑菇博客持续集成](doc/文档/项目搭建/蘑菇博客使用GithubAction完成持续集成/README.md)
    - [蘑菇博客切换搜索模式](doc/文档/项目搭建/蘑菇博客切换搜索模式/README.md)
    - [蘑菇博客配置七牛云对象存储](doc/文档/项目搭建/蘑菇博客配置七牛云存储/README.md)
    - [使用Zipkin搭建蘑菇博客链路追踪](doc/文档/项目搭建/使用Zipkin搭建蘑菇博客链路追踪/README.md)

- **其他**

  - [致谢](doc/文档/致谢.md)
  - [将要做的事](doc/文档/将要做的事.md)
  - [贡献代码](doc/文档/贡献代码.md)
```

效果图：

![image-20200210092508749](images/image-20200210092508749.png)

### 显示页面目录

定制的侧边栏仅显示了页面的链接。还可以设置在侧边栏显示当前页面的目录(标题)。需要在`index.html`文件中的`window.$docsify`添加`subMaxLevel`字段来设置：

```
  window.$docsify = {
    loadSidebar: true,
    subMaxLevel: 3
  }
```

通过subMaxLevel来限制显示的标题等级，效果如下所示：

![image-20200210093741441](images/image-20200210093741441.png)

subMaxLevel说明：

> subMaxLevel类型是number(数字)，表示显示的目录层级
> **注意：**如果md文件中的第一个标题是一级标题，那么不会显示在侧边栏，如上图所示

| 值   | 说明                                           |
| ---- | ---------------------------------------------- |
| 0    | 默认值，表示不显示目录                         |
| 1    | 显示一级标题(`h1`)                             |
| 2    | 显示一、二级标题(`h1` ~ `h2`)                  |
| 3    | 显示一、二、三级标题(`h1` ~ `h3`)              |
| n    | n是数字，显示一、二、....n 级标题(`h1` ~ `hn`) |

### 定制导航栏

首先需要在`index.html`文件中的`window.$docsify`添加`loadNavbar: true,`选项：

```
  window.$docsify = {
    loadNavbar: true
  }
```

接着在项目根目录创建`_navbar.md`文件，内容格式如下：

```
- [Gitee](https://gitee.com/moxi159753/mogu_blog_v2)
- [Github](https://github.com/moxi624/mogu_blog_v2)
- [演示](http://moguit.cn/#/)
```

![image-20200210094725641](images/image-20200210094725641.png)

**注意**

- 如果使用配置文件来设置导航栏，那么在`index.html`中定义的导航栏只有在定制的首页才会生效，其他页面会被覆盖。
- 如果只在根目录有一个`_navbar.md`文件，那么所有页面都将使用这个一个配置，也就是所有页面的导航栏都一样。
- 如果一个子目录中有`_navbar.md`文件，那么这个子目录下的所有页面将使用这个文件的导航栏。
- `_navbar.md`的加载逻辑是从每层目录下获取文件，如果当前目录不存在该文件则回退到上一级目录。例如当前路径为`/zh-cn/more-pages`则从`/zh-cn/_navbar.md`获取文件，如果不存在则从`/_navbar.md`获取。

### 设置封面

docsify默认是没有封面的，默认有个首页`./README.md`。
通过设置`coverpage`参数，可以开启渲染封面的功能。

首先需要在`index.html`文件中的`window.$docsify`添加`coverpage: true`选项：

```
  window.$docsify = {
    coverpage: true
  }
```

接着在项目根目录创建`_coverpage.md`文件，内容格式如下：

```
<img width="180px" style="border-radius: 50%" bor src="./doc/images/gitee/favicon2.ico">

# 蘑菇博客

- 蘑菇博客，一个基于微服务架构的前后端分离博客系统。前台使用Vue + Element , 后端使用spring boot + spring cloud + mybatis-plus进行开发，使用 Jwt + Spring Security做登录验证和权限校验，使用ElasticSearch和Solr作为全文检索服务，使用Github Actions完成博客的持续集成，文件支持上传七牛云。

[![star](https://gitee.com/moxi159753/mogu_blog_v2/badge/star.svg?theme=dark)](https://gitee.com/moxi159753/mogu_blog_v2/stargazers)
[![fork](https://gitee.com/moxi159753/mogu_blog_v2/badge/fork.svg?theme=dark)](https://gitee.com/moxi159753/mogu_blog_v2/members)

[Gitee](<https://gitee.com/moxi159753/mogu_blog_v2>)
[Github](<https://github.com/moxi624/mogu_blog_v2>)
[开始阅读](README.md)
```

效果图如下所示

![image-20200210095242574](images/image-20200210095242574.png)

在这里使用了两个挂件，可以在开源项目的挂件按钮那里获取

![image-20200210095321298](images/image-20200210095321298.png)

**注意：**一份文档只会在根目录下加载封面，其他页面或者二级目录下都不会加载。

### 自定义封面背景

目前的背景是随机生成的渐变色，每次刷新都会显示不同的颜色。
docsify封面支持自定义背景色或者背景图，在`_coverpage.md`文档末尾添加：

```
<!-- 背景图片 -->
![](_media/bg.png)

<!-- 背景色 -->
![color](#2f4253)
```

**注意：**

- 自定义背景配置一定要在`_coverpage.md`文档末尾。
- 背景图片和背景色只能有一个生效.
- 背景色一定要是`#2f4253`这种格式的。

### 封面作为首页

配置了封面后，封面和首页是同时出现的，封面在上面，首页在下面。通过设置`onlyCover`参数，可以让docsify网站首页只显示封面，原来的首页通过`http://localhost:3000/#/README`访问。在`index.html`文件中的`window.$docsify`添加`onlyCover: true,`选项：

```
  window.$docsify = {
    coverpage: true,
    onlyCover: true,
  }
```

通过此配置可以把`./README.md`文件独立出来，当成项目真正的README介绍文件

### 搜索插件

全文搜索插件会根据当前页面上的超链接获取文档内容，在 localStorage 内建立文档索引。默认过期时间为一天，当然我们可以自己指定需要缓存的文件列表或者配置过期时间。

```
<script>
    window.$docsify = {
      // 完整配置参数
      search: {
        maxAge: 86400000,               // 过期时间，单位毫秒，默认一天
        paths: [],                      // or 'auto'，匹配文件路径
        placeholder: 'Type to search',  // 搜索提示框文字， 支持本地化，例子在下面
        // placeholder: {
        //   '/zh-cn/': '搜索',
        //   '/': 'Type to search'
        // },
        noData: 'No Results!',          // 找不到结果文字提示，支持本地化，例子在下面
        // noData: {
        //   '/zh-cn/': '找不到结果',
        //   '/': 'No Results'
        // },
        depth: 2,                       // 搜索标题的最大程级, 1 - 6
      }
    }
  </script>
  <!-- 引入搜索模块 -->
  <script src="//unpkg.com/docsify/lib/plugins/search.js"></script>
```

安装后，我们就能够使用搜索功能了

![image-20200210100056607](images/image-20200210100056607.png)

### 使用Github Page发布页面并自定义域名

现在我的文档，通过自定义域名  doc.moguit.cn就能够访问了

首先我们需要创建项目 `moxi624.github.io` ，第一个moxi624是你的用户名

然后我们在创建一个文件 `CNAME` 

![image-20200210132208627](images/image-20200210132208627.png)

里面添加我们需要自定义的域名

```
doc.moguit.cn
```

然后把当前项目提交到 moxi624.github.io远程项目，然后选择setting

![image-20200210132325581](images/image-20200210132325581.png)

然后找到Github Pages，选择主分支master

![image-20200210132527460](images/image-20200210132527460.png)

![image-20200210132602156](images/image-20200210132602156.png)

完成后，我们能够看到这样的页面，说明我们的站点已经发布在 doc.moguit.cn了

![image-20200210132632682](images/image-20200210132632682.png)

这个时候就需要配置域名解析了，我们到阿里云下的域名解析

![image-20200210132754297](images/image-20200210132754297.png)



注意，这边moxi624.github.io就是我们刚刚创建的仓库名

创建完成后，我们等待十分钟后，就能够正常访问我们的页面了~

![image-20200210132925813](images/image-20200210132925813.png)

### 评论插件Gitalk

Gitalk：一个现代化的，基于Preact和Github Issue的评论系统。

Gitalk 的特性：

> 1、使用 GitHub 登录
> 2、支持多语言 [en, zh-CN, zh-TW, es-ES, fr, ru]
> 3、支持个人或组织
> 4、无干扰模式（设置 distractionFreeMode 为 true 开启）
> 5、快捷键提交评论 （cmd|ctrl + enter）



使用例子：

```
<!-- 引入 gitalk留言功能 -->
<link rel="stylesheet" href="//unpkg.com/gitalk/dist/gitalk.css">
<script src="//unpkg.com/gitalk/dist/gitalk.min.js"></script>
<script src="//unpkg.com/docsify/lib/plugins/gitalk.min.js"></script>
<script>
  const gitalk = new Gitalk({
    clientID: 'XXXXXXXXXXXXXXXXXXXX',
    clientSecret: 'XXXXXXXXXXXXXXXX',
    repo: 'XXXXXXXXXXXXXX', // 存放评论的仓库
    owner: 'XXXXX', // 仓库的创建者
    admin: ['XXXXX'], // 如果仓库有多个人可以操作，那么在这里以数组形式写出
    id: location.pathname, // 用于标记评论是哪个页面的
  })
</script>
```

其中我们首先需要 创建一个 New OAuth App

![image-20200210131556706](images/image-20200210131556706.png)

内容如下

![image-20200210131643326](images/image-20200210131643326.png)

创建完成后，在替换里面的密钥

![image-20200210131739060](images/image-20200210131739060.png)

页面引入后的效果图

![image-20200210131515154](images/image-20200210131515154.png)