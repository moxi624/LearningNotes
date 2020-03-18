# API网关

## 引入pom依赖

## 开启Eureka服务名称转发

```
  cloud:
    gateway:
      discovery:  #是否与服务发现组件进行结合，通过 serviceId(必须设置成大写) 转发到具体的服务实例
        locator:
          enabled: true #开启从注册中心动态创建路由的功能
```



