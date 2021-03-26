## 前言

> 大家好，我是陌溪，欢迎点击下方的公众号名片，关注陌溪，让我们一起成长~

首先特别感谢群里小伙伴 @林间露 给蘑菇博客提供的 **Golang** 版本，开源仓库地址如下

>https://gitee.com/yanglei88/mogu-go-v2

老林写的 **mogu-go-v2** 是根据 **蘑菇博客** 改编的一个 **Golang** 简化版分支，实现了蘑菇博客后台原有的大部分基础功能，同时前端代码和数据库基本没有变化，因此可以直接使用蘑菇博客原有的前端页面。

**mogu-go-v2** 目前采用的技术有：**beego**、**gorm**、**mysql**、**redis** 和 **nginx**。相比于蘑菇博客微服务版，**Golang**版蘑菇能够做到 **1C2G** 的服务器轻松运行，再也不怕网站第二天起来就宕机了~。

好了，话不多说，下面我们直接进入正题，开启 **蘑菇Golang版** 的安装之旅。

## 拉取代码

陌溪前两天为了测试，特意 **fork** 了老林的 **mogu-go-v2** 仓库，同时解决了目前存在的一些问题，因此小伙伴们在拉取代码的时候，可以使用 **git** 命令直接拉取我 **fork** 的仓库。

```bash
git clone https://gitee.com/moxi159753/mogu-go-v2.git
```

下图是拉取仓库后的样子

![image-20210323185800186](images/image-20210323185800186.png)

## 修改配置

首先我们需要修改项目的配置文件，找到 **mogu-go-v2/conf/app.conf** 文件，首先修改 **dev** 环境的配置

```bash
#以下是mysql，redis和文件上传临时目录,根据环境切换
[dev]
#mysql配置
mysqlurls = "localhost:3306"
mysqladmin="root"
mysqlpwd="root"
mysqldb="mogu_blog_go"
#redis密码
redisConn="localhost:6379"
redisPwd=""

fileUploadPath="D:\mogu_blog\data"
data_website_url=http://localhost:9527
data_web_url=http://localhost:8607
```

将 **mysql** 和 **redis** 配置修改成自己环境中对应的账号和密码

同时还需要到 **mogu-go-v2/doc/SQL** 目录下，导入 **MySQL** 数据库脚本

![image-20210323185538642](images/image-20210323185538642.png)

## 后台项目启动

在启动项目前，我们需要确保 **redis** 和 **mysql** 已经配置无误，同时安装了 **Golang** 运行环境

关于 **Golang** 环境的安装，这里就不展开叙述，可以参考这篇文章

> Go语言的安装：http://moguit.cn/#/info?blogOid=27 

完成上述操作后 **mogu-go-v2** 根目录，打开 **cmd** 执行窗口。同时因为 **Golang** 在获取依赖的时候，经常会出现 timeout，因此可以设置一下代理

```bash
go env -w GO111MODULE=on
go env -w GOPROXY=https://goproxy.io,direct
```

设置完成代理后，即可开始运行程序

```bash
go run main.go
```

运行的过程中，即会进行依赖的安装，项目启动完成后运行在 **8607** 端口，如下图所示

![image-20210323194121102](images/image-20210323194121102.png)

## 前台项目启动

### 安装环境

前端项目使用的是 **Vue** 编写的，所以在这之前，需要下载好nodejs，因为 **nodejs** 里的 **npm** 模块是用于管理vue项目中的依赖，就类似于 **maven** 一样

> node官网：https://nodejs.org/en/

在安装的时候，记得选择好加入到环境变量中，这样我们就能在任何使用了。

查看是否安装成功： **npm -v**

![img](images/1562769208770.png)

### 拉取代码

因为 **mogu-go-v2** 只包含了蘑菇博客的 **Golang** 后台，因此前端代码还是使用的蘑菇博客下的，所以需要拉取蘑菇博客的代码仓库。

```bash
https://gitee.com/moxi159753/mogu_blog_v2
```

### 启动 vue_mogu_admin

进入vue_mogu_admin 文件夹内，使用下面命令进行安装

```bash
# 使用淘宝镜像源进行依赖安装，解决国内下载缓慢的问题(出现警告可以忽略)
npm install --registry=https://registry.npm.taobao.org

# 启动项目
npm run dev

#打包项目（在部署的时候才需要使用）
npm run build
```

强烈建议不要用直接使用 cnpm 安装，会有各种诡异的 bug，可以通过重新指定 registry 来解决 npm 安装速度慢的问题。若还是不行，可使用 [yarn](https://github.com/yarnpkg/yarn) 替代 `npm`。

Windows 用户若安装不成功，很大概率是`node-sass`安装失败，[解决方案](https://github.com/PanJiaChen/vue-element-admin/issues/24)。

```
# 指定node-sass的国内镜像源
npm i node-sass --sass_binary_site=https://npm.taobao.org/mirrors/node-sass

# 重新安装
npm install --registry=https://registry.npm.taobao.org
```

另外因为 `node-sass` 是依赖 `python`环境的，如果你之前没有安装和配置过的话，需要自行查看一下相关安装教程。在启动项目成功后，会跳转到：http://localhost:9528 ，我们输入账号密码： admin, mogu2018 访问即可

![img](http://image.moguit.cn/1562769468634.png) 

### 启动 vue_mogu_web

这个步骤其实和admin端的安装时一致的，这里就不做过多的叙述

```bash
# 使用淘宝镜像源进行依赖安装，解决国内下载缓慢的问题(出现警告可以忽略)
npm install --registry=https://registry.npm.taobao.org

# 启动项目
npm run dev

#打包项目（在部署的时候才需要使用）
npm run build
```

下面是启动成功的界面，跳转到：http://localhost:9527

![img](images/1562769602284.png)

注意，如果登录后没有图片显示，那么我们需要进入到后台管理，配置一下**七牛云**文件存储，参考下面博客

> 蘑菇博客配置七牛云：http://www.moguit.cn/#/info?blogOid=202

目前暂不支持 本地存储和 Minio存储

![image-20210323195931423](images/image-20210323195931423.png)

配置完成后，我们到图片管理上传对应的图片即可~

到此为止，蘑菇博客 **Golang** 版已经能够运行了，后面关于 **Golang** 版的一键部署，陌溪也会尽快安排~

好了，我是陌溪，让我们下期再见~

## 往期推荐

- [蘑菇博客从0到2000Star，分享我的Java自学路线图](https://mp.weixin.qq.com/s/3u6OOYkpj4_ecMzfMqKJRw)
- [从三本院校到斩获字节跳动后端研发Offer-讲述我的故事](https://mp.weixin.qq.com/s/c4rR_aWpmNNFGn-mZBLWYg)
- [陌溪在公众号摸滚翻爬半个月，整理的入门指南](https://mp.weixin.qq.com/s/Jj1i-mD9Tw0vUEFXi5y54g)
- [Windows+VMware快速搭建蘑菇博客本地开发环境](http://mp.weixin.qq.com/s?__biz=MzkyMzE5NTYzMA==&mid=100001477&idx=1&sn=a8d8e1dd302ad9e434c3151996a39559&chksm=41e98744769e0e5281d064e6eb4863719b7917fa2f0ebf4b5a049eab2bd5498598023347651c#rd)

## 结语

**陌溪**是一个从三本院校一路摸滚翻爬上来的互联网大厂程序员。独立做过几个开源项目，其中**蘑菇博客**在码云上有 **2K Star** 。目前就职于**字节跳动的Data广告部门**，是字节跳动全线产品的商业变现研发团队。本公众号将会持续性的输出很多原创小知识以及学习资源。如果你觉得本文对你有所帮助，麻烦给文章点个「赞」和「在看」。同时欢迎各位小伙伴关注陌溪，让我们一起成长~

![和陌溪一起学编程](images/image-20210122092846701.png)