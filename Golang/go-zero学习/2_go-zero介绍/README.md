# go-zero简介

## 来源

文档：https://go-zero.dev

官网：

## go-zero介绍

go-zero是一个集成了各种工程实践的web和rpc框架。通过弹性设计保障了大并发服务端的稳定性，经受了充分的实战检验。

go-zero 包含极简的 API 定义和生成工具 goctl，可以根据定义的 api 文件一键生成 Go, iOS, Android, Kotlin, Dart, TypeScript, JavaScript 代码，并可直接运行。

使用go-zero的好处：

- 轻松获得支撑千万日活服务的稳定性

- 内建级联超时控制、限流、自适应熔断、自适应降载等微服务治理能力，无需配置和额外代码

- 微服务治理中间件可无缝集成到其它现有框架使用

- 极简的API描述，一键生成各端代码

- 自动校验客户端请求参数合法性
- 大量微服务治理和并发工具包

![image-20201104093933723](images/image-20201104093933723.png)

## go-zero框架背景

18年初，晓黑板后端在经过频繁的宕机后，决定从`Java+MongoDB`的单体架构迁移到微服务架构，经过仔细思考和对比，我们决定：



- 基于Go语言

- - 高效的性能

- - 简洁的语法

- - 广泛验证的工程效率

- - 极致的部署体验

- - 极低的服务端资源成本

- 自研微服务框架

- - 有过很多微服务框架自研经验

- - 需要有更快速的问题定位能力

- - 更便捷的增加新特性



## go-zero框架设计思考

对于微服务框架的设计，我们期望保障微服务稳定性的同时，也要特别注重研发效率。所以设计之初，我们就有如下一些准则：

- 保持简单

- 高可用

- 高并发

- 易扩展

- 弹性设计，面向故障编程

- 尽可能对业务开发友好，封装复杂度

- 尽可能约束做一件事只有一种方式

我们经历不到半年时间，彻底完成了从`Java+MongoDB`到`Golang+MySQL`为主的微服务体系迁移，并于18年8月底完全上线，稳定保障了晓黑板后续增长，确保了整个服务的高可用。

## 3. go-zero项目实现和特点

go-zero是一个集成了各种工程实践的包含web和rpc框架，有如下主要特点：

- 强大的工具支持，尽可能少的代码编写

- 极简的接口

- 完全兼容net/http

- 支持中间件，方便扩展

- 高性能

- 面向故障编程，弹性设计

- 内建服务发现、负载均衡

- 内建限流、熔断、降载，且自动触发，自动恢复

- API参数自动校验

- 超时级联控制

- 自动缓存控制

- 链路跟踪、统计报警等

- 高并发支撑，稳定保障了晓黑板疫情期间每天的流量洪峰

如下图，我们从多个层面保障了整体服务的高可用：

![image-20201104094000878](images/image-20201104094000878.png)

## Installation

在项目目录下通过如下命令安装：



```
GOPROXY=https://goproxy.cn/,direct go get -u github.com/zeromicro/go-zero
```



## 5. Quick Start

1. 安装goctl工具

   ```
   goctl
   ```

   读作

   ```
   go control
   ```

   ，不要读成

   ```
   go C-T-L
   ```

   。

   ```
   goctl
   ```

   的意思是不要被代码控制，而是要去控制它。其中的

   ```
   go
   ```

   不是指

   ```
   golang
   ```

   。在设计

   ```
   goctl
   ```

   之初，我就希望通过

   ```
   她
   ```

   来解放我们的双手👈

   ```
   GO111MODULE=on GOPROXY=https://goproxy.cn/,direct go install github.com/zeromicro/go-zero/tools/goctl@latest
   ```

   确保goctl可执行

2. 快速生成api服务

   ```
      goctl api new greet
      cd greet
      go run greet.go -f etc/greet-api.yaml
   ```

   默认侦听在8888端口（可以在配置文件里修改），可以通过curl请求：

   ```
      curl -i http://localhost:8888/greet/from/you
   ```

   返回如下：

   ```
      HTTP/1.1 200 OK
      Date: Sun, 30 Aug 2020 15:32:35 GMT
      Content-Length: 0
   ```

   编写业务代码：

- - api文件定义了服务对外暴露的路由，可参考[api规范](https://github.com/zeromicro/zero-doc/blob/main/doc/goctl.md)

- - 可以在servicecontext.go里面传递依赖给logic，比如mysql, redis等

- - 在api定义的get/post/put/delete等请求对应的logic里增加业务处理逻辑

1. 可以根据api文件生成前端需要的Java, TypeScript, Dart, JavaScript代码

   ```
   goctl api java -api greet.api -dir greet
   goctl api dart -api greet.api -dir greet
   ...
   ```



## 6. go-zero背后的设计思考

go-zero背后的思考，从整体上来理解微服务系统设计：

https://www.bilibili.com/video/BV1rD4y127PD/
