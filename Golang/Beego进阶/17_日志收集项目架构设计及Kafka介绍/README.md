# 日志收集项目架构设计及Kafka介绍

## 项目背景

每个业务系统都有日志，当系统出现问题时，需要通过日志信息来定位和解决问题。当系统机器比较少时，登陆到服务器上查看即可满足当系统机器规模巨大，登陆到机器上查看几乎不现实（分布式的系统，一个系统部署在十几台机器上）

## 解决方案

把机器上的日志实时收集，统一存储到中心系统。再对这些日志建立索引，通过搜索即可快速找到对应的日志记录。通过提供一个界面友好的web页面实现日志展示与检索。

## 面临的问题

实时日志量非常大，每天处理几十亿条。日志准实时收集，延迟控制在分钟级别。系统的架构设计能够支持水平扩展。

## 业界方案

### ELK

![image-20200906204717091](images/image-20200906204717091.png)

- AppServer：跑业务的服务器
- Logstash Agent：
- Elastic Search Cluster
- Kibana Server：数据可视化
- Browser：浏览器

### ELK方案的问题

- 运维成本高，每增加一个日志收集项，都需要手动修改配置
- 监控缺失，无法精准获取logstash的状态
- 无法做到定制化开发与维护

## 日志收集系统架构设计

### 架构设计

![image-20200906205046296](images/image-20200906205046296.png)

通过etcd做一个配置中心的概念，它是用go写的，是可以用来替代Zookeeper的

LogAgent收集日志，然后将其发送到Kafka中，Kafka既可以作为消息队列，也可以做到消息的存储组件

然后Log transfer就将Kafka中的日志记录取出来，进行处理，然后写入到ElasticSearch中，然后将对应的日志

最后通过Kibana进行可视化展示，SysAgent是用来采集系统的日志信息（或者使用 普罗米修斯）

### 组件介绍

- LogAgent：日志收集客户端，用来收集服务器上的日志 
- Kafka：高吞吐量的分布式队列（Linkin开发，Apache顶级开源项目）
- ElasticSearch：开源的搜索引擎，提供基于HTTP RESTful的web接口
- Kibana：开源的ES数据分析和可视化工具
- Hadoop：分布式计算框架，能够对大量数据进行分布式处理的平台
- Storm：一个免费并开源的分布式实时计算框架

### 将学到的技能

- 服务端agent开发
- 后端服务组件开发
- Kafka和Zookeeper的使用
- ES和Kibana使用
- etcd的使用（配置中心，配置共享）

## 消息队列的通信模型

### 点对点模式 queue

消息生产者发送到queue中，然后消息消费者从queue中取出并消费信息，一条消息被消费以后，queue中就没有了，不存在重复消费的问题

### 发布/订阅 topic

消息生产者（发布）将消息发布到topic中，同时有多个消息消费者（订阅）消费该消息。和点对点方式不同，发布到topic的消息会被所有的订阅者消费（类似于关注了微信公众号的人都能收到推送的文章）。

补充：发布订阅模式下，当发布者消息量很大时，显然单个订阅者的处理能力是不足的。实际上现实场景中多个订阅者节点组成一个订阅组负载均衡消费topic消息即分组订阅，这样订阅者很容易实现消费能力线扩展。可以看成是一个topic下有多个Queue，每个Queue是点对点的方式，Queue之间是发布订阅方式。

## Kafka

Apache Kafka由著名职业社交公司Linkedin开发，最初是被设计用来解决LinkedIn公司内部海量日志传输问题，Kafka使用Scala语言编写，于2011年开源并进入Apache孵化器，2012年10月正式毕业，现在为Apache顶级项目

### 介绍

Kafka是一个分布式数据流平台，可以运行在单台服务器上，也可以在多台服务器上部署形成集群。它提供了发布和订阅功能，使用这可以发送数据到Kafka中，也可以从Kafka中读取数据（以便进行后续处理）。Kafka具有高吞吐、低延迟、高容错等特点。

![image-20200906213002928](images/image-20200906213002928.png)

### Kafka的架构图

![image-20200906213058075](images/image-20200906213058075.png)

- Producer:Producer即生产者，消息的产生者，是消息的入口。
- kafka cluster:kafka集群，一台或多台服务器组成
  - Broker:Broker是指部署了Kafka实例的服务器节点。每个服务器上有一个或多个kafka的实例，我们姑且认为每个broker对应一台服务器。每个kafka集群内的broker都有一个不重复的编号，如图中的broker-0、broker-1等…
  - Topic：消息的主题，可以理解为消息的分类，kafka的数据就保存在topic。在每个broker上都可以创建多个topic。实际应用中通常是一个业务线建一个topic。
  - Partition:Topic的分区，每个topic可以有多个分区，分区的作用是做负载，提高kafka的吞吐量。同一个topic在不同的分区的数据是不重复的，partition的表现形式就是一个一个的文件夹！
  - Replication：每一个分区都有多个副本，副本的作用是做备胎。当主分区（Leader）故障的时候会选择一个备胎（Follower）上位，成为Leader。在kafka中默认副本的最大数量是10个，且副本的数量不能大于Broker的数量，follower和leader绝对是在不同的机器，同一机器对同一个分区也只可能存放一个副本（包括自己）。
- Consumer：消费者，即消息的消费方，是消息的出口。

### 工作流程

我们看上面的架构图中，produce就是生产者，是数据的入口。Producer在写入数据的时候会把数据写入到Leader中，不会直接将数据写入follower！那leader怎么找呢？写入流程又是怎么样的呢？我们看下图

![image-20200906214046688](images/image-20200906214046688.png)

- 生产者从Kafka集群获取分区leader信息
- 生产者将消息发送给leader
- leader将消息写入本地磁盘
- follower从leader拉取消息数据
- follower将消息写入本地磁盘后向leader发送ACK
- leader收到所有的follower的ACK之后向生产者发送ACK

### 选择partition的原则

那在kafka中，如果某低opic有多个partition，producer又怎么知道该将数据发往哪个partition呢？
kafka中有几个原则：

- partition在写入的时候可以指定需要写入的partition，如果有指定，则写入对应的partition。
- 如果没有指定partition，但是设置了数据的key，则会根据key的值hash出一个partition。
- 如果既没指定partition，又没有设置key，则会采用轮询方式，即每次取一小段时间的数据写入某个partition，下一小段的时间写入下一个partition。

### ACK应答机制

producer在向kafka写入消息的时候，可以设置参数来确定是否确认kafka接收到数据，这个参数可设置的值为0、1、all。

- 0：代表producer往集群发送数据不需要等到集群的返回，不确保消息发送成功。安全性最低但是效I率最高。
- 1：代表producer往集群发送数据只要leader应答就可以发送下一条，只确保leader发送成功。
- all：代表producer往集群发送数据需要所有的follower都完成从leader的同步才会发送下一条，确保leader发送成功和所有的副本都完成备份。安全性最高，但是效率最低。

最后要注意的是，如果往不存在的topic写数据，kafka会自动创建topic，partition和replication的数量默认配置都是1。