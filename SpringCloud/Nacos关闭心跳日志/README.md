# Nacos关闭心跳日志

## 前言

这两天在使用ElasticStack收集我们的日志信息，但是Nacos在启动后，会不断的生成一些心跳日志，如下所示

```bash
[mogu-admin:172.17.0.2:8601] [,] 2020-09-26 20:14:59.112 INFO 19155 [com.alibaba.nacos.client.naming.updater] com.alibaba.nacos.client.naming current ips:(0) service: DEFAULT_GROUP@@mogu-search -> []
[mogu-admin:172.17.0.2:8601] [,] 2020-09-26 20:15:00.114 INFO 19155 [com.alibaba.nacos.client.naming.updater] com.alibaba.nacos.client.naming current ips:(0) service: DEFAULT_GROUP@@mogu-search -> []
[mogu-admin:172.17.0.2:8601] [,] 2020-09-26 20:15:01.115 INFO 19155 [com.alibaba.nacos.client.naming.updater] com.alibaba.nacos.client.naming current ips:(0) service: DEFAULT_GROUP@@mogu-search -> []
[mogu-admin:172.17.0.2:8601] [,] 2020-09-26 20:15:02.117 INFO 19155 [com.alibaba.nacos.client.naming.updater] com.alibaba.nacos.client.naming current ips:(0) service: DEFAULT_GROUP@@mogu-search -> []
[mogu-admin:172.17.0.2:8601] [,] 2020-09-26 20:15:03.118 INFO 19155 [com.alibaba.nacos.client.naming.updater] com.alibaba.nacos.client.naming current ips:(0) service: DEFAULT_GROUP@@mogu-search -> []
[mogu-admin:172.17.0.2:8601] [,] 2020-09-26 20:15:04.120 INFO 19155 [com.alibaba.nacos.client.naming.updater] com.alibaba.nacos.client.naming current ips:(0) service: DEFAULT_GROUP@@mogu-search -> []
[mogu-admin:172.17.0.2:8601] [,] 2020-09-26 20:15:05.121 INFO 19155 [com.alibaba.nacos.client.naming.updater] com.alibaba.nacos.client.naming current ips:(0) service: DEFAULT_GROUP@@mogu-search -> []
[mogu-admin:172.17.0.2:8601] [,] 2020-09-26 20:15:06.122 INFO 19155 [com.alibaba.nacos.client.naming.updater] com.alibaba.nacos.client.naming current ips:(0) service: DEFAULT_GROUP@@mogu-search -> [][{"clusterName":"DEFAULT","enabled":true,"ephemeral":true,"healthy":true,"instanceHeartBeatInterval":5000,"instanceHeartBeatTimeOut":15000,"instanceId":"172.17.0.2#8602#DEFAULT#DEFAULT_GROUP@@mogu-picture","ip":"172.17.0.2","ipDeleteTimeout":30000,"metadata":{"preserved.register.source":"SPRING_CLOUD"},"port":8602,"serviceName":"DEFAULT_GROUP@@mogu-picture","weight":1.0}]
[mogu-admin:172.17.0.2:8601] [,] 2020-09-26 20:15:09.127 INFO 19155 [com.alibaba.nacos.client.naming.updater] com.alibaba.nacos.client.naming current ips:(0) service: DEFAULT_GROUP@@mogu-search -> []
[mogu-admin:172.17.0.2:8601] [,] 2020-09-26 20:15:15.136 INFO 19155 [com.alibaba.nacos.client.naming.updater] com.alibaba.nacos.client.naming current ips:(0) service: DEFAULT_GROUP@@mogu-search -> []
[mogu-admin:172.17.0.2:8601] [,] 2020-09-26 20:15:16.137 INFO 19155 [com.alibaba.nacos.client.naming.updater] com.alibaba.nacos.client.naming current ips:(0) service: DEFAULT_GROUP@@mogu-search -> []
[mogu-admin:172.17.0.2:8601] [,] 2020-09-26 20:15:17.139 INFO 19155 [com.alibaba.nacos.client.naming.updater] com.alibaba.nacos.client.naming current ips:(0) service: DEFAULT_GROUP@@mogu-search -> []
[mogu-admin:172.17.0.2:8601] [,] 2020-09-26 20:15:18.141 INFO 19155 [com.alibaba.nacos.client.naming.updater] com.alibaba.nacos.client.naming current ips:(0) service: DEFAULT_GROUP@@mogu-search -> []
```

因为大量充斥着这样的无用数据，被我们收集上来其实没有啥作用，所以我们打算把这个关闭掉

## 解决方法

解决方法就是在yml文件中，配置一下nacos的日志级别，改成error即可

```yml
# 调整nacos日志级别
logging:
  level:
    com:
      alibaba:
        nacos:
          client:
            naming: error
```

