### 前言

目前公司系统采用 **Spring Cloud** 架构，其中服务注册和发现组件用的 **Nacos**，最近运维抱怨说，磁盘不够用，日志增长的太快。简单排查一下，罪魁祸首竟然是 **Nacos**。

按理说 **Nacos** 作为服务注册中心，不会应该会产生太多日志的，本身涉及的服务也不多，但几天就会产生 **1G** 以上的日志，的确有点疯狂。这篇文章就聊聊 **Nacos** 的日志系统。

### 事件背景

经过排查，其中输出最多的日志为 **{nacos.home}/logs/access_log.yyyy-mm-dd.log** 格式的日志。日志中包含了微服务系统调用 **Nacos** 及集群之间通信的日志，比如心跳 **(/nacos/v1/ns/instance/beat)** 、获取服务列表 **(/nacos/v1/ns/instance/list)** 、状态检查 **(/nacos/v1/ns/service/status)** 等。

我们知道 **Nacos** 是基于 **Spring Boot** 实现的，**access_log** 日志是 **Spring Boot** 提内置的Tomcat的访问日志。关于该项日志的配置，没有保留最大天数，也没有日志大小的控制。而且随着 **Nacos Server** 与各个服务直接的心跳、获取、注册等会不停的产生访问日志，微服务越多，日志增长越快。这些日志打印会迅速占用完磁盘空间，带来资源浪费和运维成本。

### 解决方案

上述的 **access_log** 日志输出 **Nacos** 是提供了控制开关的，在 **Nacos** 的 **conf** 目录下 **application.properties** 配置文件中，默认有以下配置：

```
#*************** Access Log Related Configurations ***************# 
### If turn on the access log: 
server.tomcat.accesslog.enabled=true 
 
### The access log pattern: 
server.tomcat.accesslog.pattern=%h %l %u %t "%r" %s %b %D %{User-Agent}i %{Request-Source}i 
 
### The directory of access log: 
server.tomcat.basedir= 
```

可以看到，关于访问日志支持关闭、日志输出格式以及日志输出的目录。

在测试环境，我们可以直接将enabled的配置项设置为false，直接关闭该日志的输出。

```bash
server.tomcat.accesslog.enabled=false 
```

但在生产环境，这样操作就有一定的风险了。当关闭之后，生产出现问题时需要根据日志进行排查，就会找不到对应的日志。

此时，只能通过其他方式进行处理，比如在 **Linux** 操作系统下通过编写 **crontab** 来完成日志的定时删除。对应的脚本示例如下：

```bash
#!/bin/bash 
 
logFile="/data/nacos/bin/logs/nacos_del_access.log" 
# 保留14天日志 
date=`date -d "$date -14 day" +"%Y-%m-%d"` 
# 具体位置可调整 
delFilePath="/data/nacos/bin/logs/access_log.${date}.log" 
 
if [ ! -f "${logFile}" ];then 
    echo 'access log文件打印日志频繁. /etc/cron.daily/nacosDelAccessLogs.sh 会定时删除access日志文件' >>${logFile} 
fi 
# 日志文件存在， 则删除 
if [  -f "${delFilePath}" ];then 
    rm -rf ${delFilePath} 
    curDate=`date --date='0 days ago' "+%Y-%m-%d %H:%M:%S"` 
    echo '['${curDate}'] 删除文件'${delFilePath} >>${logFile} 
fi 
```

虽然问题解决了，但很明显并不优雅，这也是 **Nacos Server** 日志输出的问题之一。

### 日志级别动态调整

关于 **Nacos Server** 日志的输出级别，在1.1.3版本之前，同样会打印大量的日志，而且没办法动态的进行调整。在此版本之后，日志输出得到了优化，并且支持通过 **API** 的形式来进行日志级别的调整，示例如下：

复制

```bash
# 调整naming模块的naming-raft.log的级别为error: 
curl -X PUT '$nacos_server:8848/nacos/v1/ns/operator/log?logName=naming-raft&logLevel=error' 
# 调整config模块的config-dump.log的级别为warn: 
curl -X PUT '$nacos_server:8848/nacos/v1/cs/ops/log?logName=config-dump&logLevel=warn' 
```

### 客户端日志

业务系统集成的客户端在1.1.3版本之后，也进行了优化，避免日志大量打印(主要涉及心跳日志、轮询日志等)。

在业务系统的application.yml配置文件中，可通过日志级别设置来进行控制：

复制

```yml
# 日志级别，可以指定到具体类 
logging: 
  level: 
    com.alibaba.nacos: warn 
```

也可以通过启动时的JVM参数来进行控制，默认是info级别：

```bash
-Dcom.alibaba.nacos.naming.log.level=warn -Dcom.alibaba.nacos.config.log.level=warn 
```

上述示例分别指定了Naming客户端和Config客户端的日志级别，适用于1.0.0及以上版本。

### 更细的日志配置

查看conf目录下的nacos-logback.xml配置，你会发现Nacos相关的日志配置项非常多，如果因项目需要进行更精细化的配置，可在此文件中进行直接配置。

以naming-server对应的append配置为例，看一下默认的配置：

复制

```xml
<appender name="naming-server" 
          class="ch.qos.logback.core.rolling.RollingFileAppender"> 
    <file>${LOG_HOME}/naming-server.log</file> 
    <append>true</append> 
    <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy"> 
        <fileNamePattern>${LOG_HOME}/naming-server.log.%d{yyyy-MM-dd}.%i</fileNamePattern> 
        <maxFileSize>1GB</maxFileSize> 
        <maxHistory>7</maxHistory> 
        <totalSizeCap>7GB</totalSizeCap> 
        <cleanHistoryOnStart>true</cleanHistoryOnStart> 
    </rollingPolicy> 
    <encoder> 
        <Pattern>%date %level %msg%n%n</Pattern> 
        <charset>UTF-8</charset> 
    </encoder> 
</appender> 
```

这里根据自己的需要，可调整输出的日志格式、日志文件分割、日志保留日期及日志压缩等处理。

### 小结

关于 **Nacos** 的日志输出就聊这么多，整体而言相关的日志输出有些过于多了，而且在灵活配置方面还有待提升。基于目前的现状我们可以通过自定义或定时任务等配合完成日志输出与管理。

 