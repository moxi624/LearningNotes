最近，在给蘑菇博客升级了 **ElasticSearch** 版本

下面来讲讲，**ElasticSearch** 最新版本 **8.4** 的安装指南

首先，需要创建以下几个目录

```bash
mkdir -p  /data/es
mkdir -p  /data/es/data
mkdir -p  /data/es/config
mkdir -p  /data/es/plugins
```

创建完成后，给数据目录添加权限，不然 ElasticSearch 在启动的时候会报错，导致启动失败

```bash
chmod 777 /data/es
chmod 777 /data/es/data
chmod 777 /data/es/config
chmod 777 /data/es/plugins
```

编写配置文件 **elasticsearch.conf**

```yml
# 集群名称
cluster.name: mogu-es
# 节点名称
node.name: es-node-1
# 绑定host，0.0.0.0代表当前节点的ip
network.host: 0.0.0.0
# 设置其它节点和该节点交互的ip地址，如果不设置它会自动判断，值必须是个真实的ip地址(本机ip)，修改成自己ip
network.publish_host: 172.16.0.2
# 设置对外服务的http端口，默认为9200
http.port: 9200
# 设置节点间交互的tcp端口，默认是9300
# transport.tcp.port: 9300
# 是否支持跨域，默认为false
http.cors.enabled: true
# 当设置允许跨域，默认为*,表示支持所有域名，如果我们只是允许某些网站能访问，那么可以使用正则表达式。比如只允许本地地址。 /https?:\/\/localhost(:[0-9]+)?/
http.cors.allow-origin: "*"
# 表示这个节点是否可以充当主节点
# node.master: true
# 是否充当数据节点
# node.data: true
# 所有主从节点ip:port 
# discovery.seed_hosts: ["172.16.0.2:9300"]  #本地只有一个节点,无法正常启动,先注释
# 这个参数决定了在选主过程中需要 有多少个节点通信  预防脑裂 N/2+1
# discovery.zen.minimum_master_nodes: 1
#初始化主节点
#cluster.initial_master_nodes: ["es-node-1"]
# 内存交换
bootstrap.memory_lock: true
# 关闭认证授权
xpack.security.enabled: false
```

然后，编写 docker-compose.yml 编排文件

```yml
version: '3.9'
services:
  elasticsearch:
    image: elasticsearch:8.2.0
    container_name: es
    hostname: es
    privileged: true
    tty: true
    restart: unless-stopped
    environment:
      - TZ=Asia/Shanghai
      - "ES_JAVA_OPTS=-Xms512m -Xmx512m"
      - discovery.type=single-node
    ports:
      - "9200:9200"
      - "9300:9300"
    networks:
      - es_network
    volumes:
      - /etc/localtime:/etc/timezone
      - /etc/localtime:/etc/localtime
      - /data/es/data:/usr/share/elasticsearch/data
      - /data/es/plugins:/usr/share/elasticsearch/plugins
      - /data/es/config/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml
  es-head:
    image: mobz/elasticsearch-head:5
    container_name: es-head
    hostname: es-head
    restart: always
    privileged: true
    tty: true
    environment:
      - TZ=Asia/Shanghai
    ports:
      - "9100:9100"
    volumes:
      - /etc/localtime:/etc/timezone
      - /etc/localtime:/etc/localtime
  cerebro:
    image: lmenezes/cerebro:latest
    container_name: cerebro
    hostname: cerebro
    restart: always
    privileged: true
    tty: true
    environment:
      - TZ=Asia/Shanghai
      - AUTH_TYPE=basic
      - BASIC_AUTH_USER=your username
      - BASIC_AUTH_PWD= your password
    ports:
      - "9000:9000"
    volumes:
      - /etc/localtime:/etc/timezone
      - /etc/localtime:/etc/localtime
    command:
      - -Dhosts.0.host=http://elasticsearch:9200
    networks:
      - es_network
  kibana:
    image: kibana:8.2.0
    container_name: kibana
    hostname: kibana
    restart: always
    privileged: true
    tty: true
    volumes:
      - /etc/localtime:/etc/timezone
      - /etc/localtime:/etc/localtime
    environment:
      - TZ=Asia/Shanghai
      - I18N_LOCALE=zh-CN
      - XPACK_GRAPH_ENABLED=true
      - TIMELION_ENABLED=true
      - XPACK_MONITORING_COLLECTION_ENABLED="true"
    ports:
      - "5601:5601"
    links:
      - elasticsearch:elasticsearch
    depends_on:
      - elasticsearch
    networks:
      - es_network
networks:
  es_network:
    driver: bridge
```

## 安装 IK 分词器

1. 下载与 es 版本一致的 ik 分词器插件 ，由于本次安装 es 版本为 8.2.0 版本，故下载此版本

 [https://github.com/medcl/elasticsearch-analysis-ik/releases/tag/v8.2.0](https://www.oschina.net/action/GoToLink?url=https%3A%2F%2Fgithub.com%2Fmedcl%2Felasticsearch-analysis-ik%2Freleases%2Ftag%2Fv8.2.0)

1. 在宿主机上 `/data/es/plugins` 目录下创建 ik 文件目录

2. 复制文件到宿主机 `/data/es/plugins/ik` 目录下，进行解压文件即可，命令如下：

   ```bash
   unzip elasticsearch-analysis-ik-8.2.0.zip 
   ```

## 启动服务

首次启动建议使用控制输出方式

```
docker-compose up
```

上述操作后服务能正常启动后，下次就可以后台进程方式启动服务

```
docker-compose up -d
```

# 验证服务可用

- Es head

  [http://ip 或者域名：9100](https://www.oschina.net/action/GoToLink?url=http%3A%2F%2Fip或者域名%3A9100)

- Cerebro

  [http://ip 或者域名：9000](https://www.oschina.net/action/GoToLink?url=http%3A%2F%2Fip或者域名%3A9000)

- Kibana

  [http://ip 或者域名：5601](https://www.oschina.net/action/GoToLink?url=http%3A%2F%2Fip或者域名%3A5601)

如果服务正常启动，无法访问请在防火墙开放相关端口。