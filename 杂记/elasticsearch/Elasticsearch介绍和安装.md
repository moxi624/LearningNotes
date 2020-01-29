# 1.Elasticsearch介绍和安装

以一个电商项目为例，用户访问我们的首页，一般都会直接搜索来寻找自己想要购买的商品。而商品的数量非常多，而且分类繁杂。如果能正确的显示出用户想要的商品，并进行合理的过滤，尽快促成交易，是搜索系统要研究的核心。面对这样复杂的搜索业务和数据量，使用传统数据库搜索就显得力不从心，一般我们都会使用全文检索技术，比如之前大家学习过的Solr。不过今天，我们要讲的是另一个全文检索技术：Elasticsearch。

## 1.1.简介

### 1.1.1.Elastic

Elastic官网：https://www.elastic.co/cn/

![1528546351055](./assets/1528546351055.png)



Elastic有一条完整的产品线及解决方案：Elasticsearch、Kibana、Logstash等，前面说的三个就是大家常说的ELK技术栈。

![1528546493105](./assets/1528546493105.png)



### 1.1.2.Elasticsearch

Elasticsearch官网：https://www.elastic.co/cn/products/elasticsearch

![1528547087016](./assets/1528547087016.png)

如上所述，Elasticsearch具备以下特点：

- 分布式，无需人工搭建集群（solr就需要人为配置，使用Zookeeper作为注册中心）
- Restful风格，一切API都遵循Rest原则，容易上手
- 近实时搜索，数据更新在Elasticsearch中几乎是完全同步的。



### 1.1.3.版本

目前Elasticsearch最新的版本是6.3.1，我们就使用6.3.0

![1528547283102](./assets/1528547283102.png)

需要虚拟机JDK1.8及以上



## 1.2.安装和配置

为了模拟真实场景，我们将在linux下安装Elasticsearch。

### 1.2.1.新建一个用户

出于安全考虑，elasticsearch默认不允许以root账号运行。

创建用户：

```sh
useradd zerol
```

设置密码：

```
passwd 123456
```

切换用户：

```
su zerol
```



### 1.2.2.上传安装包,并解压

我们将安装包上传到：/home/zerol目录

解压缩：

```
tar -zxvf elasticsearch-6.2.4.tar.gz
```



我们把目录重命名：

```
mv elasticsearch-6.2.4/ elasticsearch
```

![1528610397414](./assets/1528610397414.png)

进入，查看目录结构：

![1528551465373](./assets/1528551465373.png)



### 1.2.3.修改配置

我们进入config目录：`cd config`

需要修改的配置文件有两个：

![1528551598931](./assets/1528551598931.png)

1. **jvm.options**

Elasticsearch基于Lucene的，而Lucene底层是java实现，因此我们需要配置jvm参数。

编辑jvm.options：

```
vim jvm.options
```

默认配置如下：

```
-Xms1g
-Xmx1g
```

内存占用太多了，我们调小一些：

```
-Xms512m
-Xmx512m
```

1. **elasticsearch.yml**

```
vim elasticsearch.yml
```

- 修改数据和日志目录：


```yml
path.data: /home/leyou/elasticsearch/data # 数据目录位置
path.logs: /home/leyou/elasticsearch/logs # 日志目录位置
```

我们把data和logs目录修改指向了elasticsearch的安装目录。但是这两个目录并不存在，因此我们需要创建出来。

进入elasticsearch的根目录，然后创建：

```
mkdir data
mkdir logs
```

![1528552839032](./assets/1528552839032.png)



- 修改绑定的ip：

```
network.host: 0.0.0.0 # 绑定到0.0.0.0，允许任何ip来访问
```

默认只允许本机访问，修改为0.0.0.0后则可以远程访问



目前我们是做的单机安装，如果要做集群，只需要在这个配置文件中添加其它节点信息即可。

> elasticsearch.yml的其它可配置信息：

| 属性名                             | 说明                                                         |
| ---------------------------------- | ------------------------------------------------------------ |
| cluster.name                       | 配置elasticsearch的集群名称，默认是elasticsearch。建议修改成一个有意义的名称。 |
| node.name                          | 节点名，es会默认随机指定一个名字，建议指定一个有意义的名称，方便管理 |
| path.conf                          | 设置配置文件的存储路径，tar或zip包安装默认在es根目录下的config文件夹，rpm安装默认在/etc/ elasticsearch |
| path.data                          | 设置索引数据的存储路径，默认是es根目录下的data文件夹，可以设置多个存储路径，用逗号隔开 |
| path.logs                          | 设置日志文件的存储路径，默认是es根目录下的logs文件夹         |
| path.plugins                       | 设置插件的存放路径，默认是es根目录下的plugins文件夹          |
| bootstrap.memory_lock              | 设置为true可以锁住ES使用的内存，避免内存进行swap             |
| network.host                       | 设置bind_host和publish_host，设置为0.0.0.0允许外网访问       |
| http.port                          | 设置对外服务的http端口，默认为9200。                         |
| transport.tcp.port                 | 集群结点之间通信端口                                         |
| discovery.zen.ping.timeout         | 设置ES自动发现节点连接超时的时间，默认为3秒，如果网络延迟高可设置大些 |
| discovery.zen.minimum_master_nodes | 主结点数量的最少值 ,此值的公式为：(master_eligible_nodes / 2) + 1 ，比如：有3个符合要求的主结点，那么这里要设置为2 |
|                                    |                                                              |



## 1.3.运行

进入elasticsearch/bin目录，可以看到下面的执行文件：

![1528553103468](./assets/1528553103468.png)

然后输入命令：

```
./elasticsearch
```

发现报错了，启动失败：

### 1.3.1.错误1：内核过低

![1528598315714](./assets/1528598315714.png)

我们使用的是centos6，其linux内核版本为2.6。而Elasticsearch的插件要求至少3.5以上版本。不过没关系，我们禁用这个插件即可。

修改elasticsearch.yml文件，在最下面添加如下配置：

```
bootstrap.system_call_filter: false
```

然后重启



### 1.3.2.错误2：文件权限不足

再次启动，又出错了：

![1528599116836](./assets/1528599116836.png)

```
[1]: max file descriptors [4096] for elasticsearch process likely too low, increase to at least [65536]
```

我们用的是zerol用户，而不是root，所以文件权限不足。

**首先用root用户登录。**

然后修改配置文件:

```
vim /etc/security/limits.conf
```

添加下面的内容：

```
* soft nofile 65536

* hard nofile 131072

* soft nproc 4096

* hard nproc 4096
```



### 1.3.3.错误3：线程数不够

刚才报错中，还有一行：

```
[1]: max number of threads [1024] for user [leyou] is too low, increase to at least [4096]
```

这是线程数不够。

继续修改配置：

```
vim /etc/security/limits.d/90-nproc.conf 
```

修改下面的内容：

```
* soft nproc 1024
```

改为：

```
* soft nproc 4096
```



### 1.3.4.错误4：进程虚拟内存

```
[3]: max virtual memory areas vm.max_map_count [65530] likely too low, increase to at least [262144]
```

vm.max_map_count：限制一个进程可以拥有的VMA(虚拟内存区域)的数量，继续修改配置文件， ：

```
vim /etc/sysctl.conf 
```

添加下面内容：

```
vm.max_map_count=655360
```

然后执行命令：

```
sysctl -p
```



### 1.3.5.重启终端窗口

所有错误修改完毕，一定要重启你的 Xshell终端，否则配置无效。



### 1.3.6.启动

再次启动，终于成功了！

![1528603044862](./assets/1528603044862.png)

可以看到绑定了两个端口:

- 9300：集群节点间通讯接口
- 9200：客户端访问接口

我们在浏览器中访问：http://192.168.56.101:9200

![1528611090621](./assets/1528611090621.png)



## 1.4.安装kibana

### 1.4.1.什么是Kibana？

![1528603530298](./assets/1528603530298.png)

Kibana是一个基于Node.js的Elasticsearch索引库数据统计工具，可以利用Elasticsearch的聚合功能，生成各种图表，如柱形图，线状图，饼图等。

而且还提供了操作Elasticsearch索引数据的控制台，并且提供了一定的API提示，非常有利于我们学习Elasticsearch的语法。



### 1.4.2.安装

因为Kibana依赖于node，我们的虚拟机没有安装node，而window中安装过。所以我们选择在window下使用kibana。

最新版本与elasticsearch保持一致，也是6.3.0

![1528611218599](./assets/1528611218599.png)

解压到特定目录即可



### 1.4.3.配置运行

> 配置

进入安装目录下的config目录，修改kibana.yml文件：

修改elasticsearch服务器的地址：

```
elasticsearch.url: "http://localhost:9200"
```

> 运行

进入安装目录下的bin目录：

![1528612108406](./assets/1528612108406.png)

双击运行：

![1528612216033](./assets/1528612216033.png)

发现kibana的监听端口是5601

我们访问：http://127.0.0.1:5601

![1528612265677](./assets/1528612265677.png)



### 1.4.4.控制台

选择左侧的DevTools菜单，即可进入控制台页面：

![1528612350020](./assets/1528612350020.png)



在页面右侧，我们就可以输入请求，访问Elasticsearch了。

![1528612514556](./assets/1528612514556.png)



## 1.5.安装ik分词器

Lucene的IK分词器早在2012年已经没有维护了，现在我们要使用的是在其基础上维护升级的版本，并且开发为ElasticSearch的集成插件了，与Elasticsearch一起维护升级，版本也保持一致，最新版本：6.3.0

https://github.com/medcl/elasticsearch-analysis-ik

### 1.5.1.安装

上传课前资料中的zip包，解压到Elasticsearch目录的plugins目录中：

![1526482432181](./assets/1528612654570.png)

使用unzip命令解压：

```
unzip elasticsearch-analysis-ik-6.3.0.zip -d ik-analyzer
```

然后重启elasticsearch：

![1528612928524](./assets/1528612928524.png)



### 1.5.2.测试

大家先不管语法，我们先测试一波。

在kibana控制台输入下面的请求：

```
POST _analyze
{
  "analyzer": "ik_max_word",
  "text":     "我是中国人"
}
```

运行得到结果：

```
{
  "tokens": [
    {
      "token": "我",
      "start_offset": 0,
      "end_offset": 1,
      "type": "CN_CHAR",
      "position": 0
    },
    {
      "token": "是",
      "start_offset": 1,
      "end_offset": 2,
      "type": "CN_CHAR",
      "position": 1
    },
    {
      "token": "中国人",
      "start_offset": 2,
      "end_offset": 5,
      "type": "CN_WORD",
      "position": 2
    },
    {
      "token": "中国",
      "start_offset": 2,
      "end_offset": 4,
      "type": "CN_WORD",
      "position": 3
    },
    {
      "token": "国人",
      "start_offset": 3,
      "end_offset": 5,
      "type": "CN_WORD",
      "position": 4
    }
  ]
}
```

