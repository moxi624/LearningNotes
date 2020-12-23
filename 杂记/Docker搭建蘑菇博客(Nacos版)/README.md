# 使用Docker快速搭建蘑菇博客（Nacos版）

最近几天一直在研究怎么样才能够快速搭建蘑菇博客项目，对了，我的服务器是阿里云购买的云服务器ECS，配置是 1核2G ，学生优惠价，100多一年。。。 嗯，这应该是搭建蘑菇博客的最低配置了，内存少于2G的话，可能会启动不起来，本来2G也是不够用的，我是把所有的微服务和solr都放到一个tomcat中，才勉强跑起来的。 目前为了更加方便大家的部署，我已经修改成jar包的方式进行部署启动了，tomcat仅用于作为solr启动的web容器。

如果你的服务器内存也是2G的话，请务必先配置一下交换内存：[CentOS如何增加虚拟内存](http://www.moguit.cn/#/info?blogUid=36ee5efa56314807a9b6f1c1db508871)

如果你也拥有对应的域名并且备案了的话，可以给蘑菇博客配置域名的方式访问：[蘑菇博客配置域名解析](http://moguit.cn/#/info?blogUid=06565868c0e86fe8125a9d55430cd266)

如果你的服务器带宽只有1M，可以使用免费的百度云加速，加快页面渲染速度：[如何使用百度云加速提升网站访问速度](http://www.moguit.cn/#/info?blogUid=af053959672343f8a139ec27fd534c6c)

> tip：特别注意，因为镜像中的代码可能不是最新版本，因此推荐在按照本篇博客，安装好docker环境后，需要在参考 [蘑菇博客部署阿里云(Nacos版)](http://www.moguit.cn/#/info?blogUid=ecde4ce178bdc1a241e9f9ddd9052013) 这篇博客，重新将前后端代码都重新部署一遍，同时也记得把doc中的三个SQL文件也重新导入，确保服务器为最新代码

如果你之前安装好了蘑菇博客的docker环境，修改的博客的源码，想要重新发布到自己服务器上：[蘑菇博客如何部署到阿里云服务器(Nacos版)](http://www.moguit.cn/#/info?blogUid=ecde4ce178bdc1a241e9f9ddd9052013)

如果想使用Docker Compose 一键完成蘑菇博客的部署：参考 [DockerCompose一键部署蘑菇博客(Nacos版)](http://www.moguit.cn/#/info?blogOid=565)

因为配置那些环境比较麻烦，（主要包括Nginx，Solr，Redis，Tomcat，Mysql，RabbitMQ）当然如果小伙伴喜欢自己配置的话，也可以不使用我搭建好的镜像，可以参考下面几篇博客哦，希望你也能够配置成功的~！（想直接通过Docker部署的，可以忽略下面几步..）

1、[CentOS下如何安装Nginx](http://www.moguit.cn/#/info?blogUid=e8d3e38ba35b4765ae128256eb44e341)

2、[CentOS下Rdeis的安装和部署](http://www.moguit.cn/#/info?blogUid=d0e2c4337d7a4a85b176834c8c674fdf)

3、[CentOS下Solr的安装和部署](http://www.moguit.cn/#/info?blogUid=7c7404c456904be5b7736238f28d2515)（可选）

4、[CentOS下Mariadb的安装和部署](http://www.moguit.cn/#/info?blogUid=d5b6dff48e5d42b1afcbf6ab591bdab1)

5、[CentOS7下RabbitMQ的安装步骤](http://www.moguit.cn/#/info?blogUid=f346267364024e78844d459be2f6f528)

6、[CentOS下ElasticSearch的安装和部署](http://moguit.cn/#/info?blogUid=ee342088a5d0f5b96bcb4582d9b563aa)（可选）

7、[Elasticsearch介绍和安装](http://moguit.cn/#/info?blogUid=ee342088a5d0f5b96bcb4582d9b563aa)（可选）

8、[CentOS下安装Nacos](http://moguit.cn/#/info?blogUid=7a7ad19472d53147150eb7fdb0978bb2)（Nacos分支需要）

9、[CentOS下安装Sentinel](http://moguit.cn/#/info?blogUid=b100fde21ac0b61414dbaa74d2db7192)（Nacos分支需要）

好了。下面我介绍的是用Docker快速搭建蘑菇博客。话不多说，下面我就直接进入正题。

## 注册Docker账号

首先大家先去DockerHub注册账号，用于拉取Docker镜像和存储镜像

注意：注册DockerHub的目的，是为了能够方便以后大家把自己的镜像上传上去，如果DockerHub无法访问，或者不想上传镜像的话，可以忽略这一步，同时在3步 也忽略 docker login，直接进行Docker pull 拉取我的镜像即可

DockerHub官网：[点我传送](https://hub.docker.com/)

关于更多docker命令和介绍，可以看这篇博客：[Docker常用命令](http://www.moguit.cn/#/info?blogUid=8974a6ce5bae4bf68f1aa37f07c96d0f)

## Docker安装和启动

注册成功后，进入我们的CentOS系统中（如果是Ubuntu的话，可能安装docker的方式不同，请自行百度安装）

下面介绍的是使用yum方式安装docker

### 配置docker的阿里云yum源

```
cat >>/etc/yum.repos.d/docker.repo<<EOF
[docker-ce-edge]
name=Docker CE Edge - \$basearch
baseurl=https://mirrors.aliyun.com/docker-ce/linux/centos/7/\$basearch/edge
enabled=1
gpgcheck=1
gpgkey=https://mirrors.aliyun.com/docker-ce/linux/centos/gpg
EOF
```

###  安装docker

```
# yum安装
yum -y install docker-ce

#查看docker版本
docker --version  

# 设置开机自启
systemctl enable docker

# 启动docker
systemctl start docker
```

启动完成后，因为需要到Dockerhub拉取镜像，而DockerHub部署在国外，大家还可以配置一下阿里的Docker国内源，这样可以达到加速的效果~

## Docker login登录

使用Docker login命令登录，需要输入刚刚注册的账号和密码（ps：如果不想发布镜像到dockerhub，可以忽略）

```
# 登录dockerhub
docker login
```

## 拉取蘑菇博客的镜像

Nacos版本使用下面命令：

```bash
docker pull moxi/mogu_blog_nacos
```

因为镜像有点大，所以拉取的话，可能会有点慢，所以耐下等待下

如果拉取失败，或者出现超时的情况、或者拉取过慢，可以尝试使用下面的方法： [CentOS7中Docker拉取镜像失败的解决方法](http://www.moguit.cn/#/info?blogUid=5296cfe28b35caa808a5387ff95734c7)

如果还是拉取速度很慢的话，推荐在早上的时候拉取镜像，因为DockerHub是国外的网站，早上的时候，他们美国这边都已经到了晚间了，基本不占用太多带宽，拉取速度会更快一些~

## 查看镜像

拉取成功后，使用命令查看已经拉取的镜像

```
docker images
```

![image-20200903160432721](images/image-20200903160432721.png)

好了，能看到刚刚拉取的镜像，大概有3.97G，因为这里面包含了项目完整的运行环境。

## 制作蘑菇博客docker容器

Nacos版本使用下面的命令：

```bash
docker run --privileged -d -it -h mogu_blog_2 --name mogu_blog_2 -v /etc/localtime:/etc/localtime:ro -p 11122:22 -p 15672:15672 -p 8600:8600 -p 9527:9527 -p 9528:9528 -p 6379:6379 -p 3306:3306 -p 80:80 -p 8080:8080 -p 8601:8601 -p 8602:8602 -p 8603:8603 -p 8604:8604 -p 8605:8605 -p 8606:8606 -p 8607:8607 -p 5601:5601 -p 9411:9411 -p 465:465 -p 8848:8848 -p 8070:8070 moxi/mogu_blog_nacos /usr/sbin/init
```

使用下面的命令，就能够制作成一个docker容器了，他会将上面写的一些端口号都映射到宿主机中，所以宿主机那些端口号不能别占用了哦。

当然同时，宿主机的那些端口号也必须开放，如果是使用阿里云的小伙伴，必须在 阿里云的官网，配置相应的安全组，不然外面是没办法访问的。

关于安全组的配置，在云服务器ECS的管理页面

![image-20200209125847329](images/image-20200209125847329.png)

在点击配置规则

![image-20200209125905430](images/image-20200209125905430.png)

然后点击右上角按钮，把需要用到的端口号都导入进去

![image-20200209125919324](images/image-20200209125919324.png)

安装下面的规则，把每一个添加进去即可, 需要添加的端口号有：

```bash
蘑菇Docker内部容器SSH连接：11122
RabbitMQ消息队列：15672
Zipkin链路追踪: 9411
发Email端口：465   
图片资源：8600   
前端Web页面:9527    
后端Admin页面：9528  
Redis:6379   
Mysql:3306   
Tomcat(里面部署的solr):8080
HTTP端口：80
Kibana端口：5601
mogu_admin端口：8601
mogu_picture端口：8602
mogu_web端口：8603
mogu_sms端口：8604
mogu_search端口：8605
mogu_monitor端口：8606
mogu_gateway端口：8607
nacos端口: 8848
sentinel端口: 8070
```

![image-20200209125938397](images/image-20200209125938397.png)

## 查看容器状态

好了，回到刚刚的内容，我们在执行第六步的时候，已经制作好了容器了，使用下面的命令，查看容器状态

```
# 查看容器状态
docker ps -a
```

![image-20200209125953803](images/image-20200209125953803.png)



## 打开XShell，连接

![image-20200209130011043](images/image-20200209130011043.png)

输入用户名： root

![image-20200209130023427](images/image-20200209130023427.png)

输入密码：mogu2018

![image-20200209130036402](images/image-20200209130036402.png)

成功进入系统，下面我们就需要把对应的服务都开启

注意：该密码是docker镜像的初始密码，如果需要更改的话，可以使用下列命令更改密码

```
passwd
```

## 启动对应的服务

### 启动Nginx

```
# 进入nginx的安装目录下
cd /soft/nginx/sbin/

# 启动nginx
./nginx
```

好吧，启动报错

![image-20200209130104979](images/image-20200209130104979.png)

看问题需要创建一个目录，那么就开始创建吧

```
mkdir -p /var/run/nginx
```

再次使用启动命令，启动成功

![image-20200209130124155](images/image-20200209130124155.png)

我们在使用命令 ，查看已经启动的端口号

```
netstat -tunlp
```

我们已经看到了，现在已经开机自启了 RabbitMQ的 5672 15672 ， mysql的 3306， 其他的一些就是项目的端口，现在我们还需要启动 redis的 6379 和 tomcat的 8080

![image-20200209130139403](images/image-20200209130139403.png)

注意：如果我们查看端口号没有RabbitMQ，我们需要手动启动对应的服务

新开一个xshell连接，启动rabbitmq：

```
# 后台启动RabbitMQ
rabbitmq-server -detached
```

### 启动redis

```
# 进入redis的安装目录
cd /soft/redis/bin/

# 后台启动redis
./redis-server redis.conf

# 查看启动端口号
netstat -tunlp
```

 我们看到redis已经正常启动了

![image-20200209130156442](images/image-20200209130156442.png)

### 启动tomcat中的solr【非必须】

如果没有安装Solr的话，参考 [CentOS下Solr的安装和部署](http://www.moguit.cn/#/info?blogUid=7c7404c456904be5b7736238f28d2515)

如果想开启其它全文检索方式【ElasticSearch】：参考 [蘑菇博客切换搜索模式](http://www.moguit.cn/#/info?blogUid=4042b4f4088e4e37e95d9fc75d97298b)

tip：如果配置了Solr作为全文检索，那么需要启动Solr，否则可以忽略这一步（默认使用的是SQL搜索）

```
# 进入tomcat目录
cd /soft/tomcat/bin

# 启动
./startup.sh

# 查看启动信息
tail -f ../logs/catalina.out
```

### 启动Nacos

Nacos是由SpringCloudAlibaba开发的服务注册和配置中心，关于更多的操作可以查看这两篇博客

- [【SpringCloud】使用Nacos实现服务注册发现以及配置中心等功能](http://moguit.cn/#/info?blogUid=e6e619349d31dded928c9265c5a9c672)
- [CentOS下安装Nacos](http://moguit.cn/#/info?blogUid=7a7ad19472d53147150eb7fdb0978bb2)

下面我们只需要进入Nacos的目录下，然后启动即可

```bash
# 进入nacos目录
cd /soft/nacos/bin
# 启动Nacos
./startup.sh -m standalone
```

启动后，会有如下提示

![image-20200903161239748](images/image-20200903161239748.png)

我们可以通过查看日志进行判断nacos是否启动成功

![image-20200903161406977](images/image-20200903161406977.png)

如果没有报错信息，说明Nacos已经启动成功了，下面我们可以进入到Nacos的图形化管理页面

```bash
http://your_ip:8848/nacos
```

打开后，输入默认账号密码：nacos  nacos，即可进入系统，查看到项目的配置

![image-20200903161619843](images/image-20200903161619843.png)

如果需要更改配置，以后到这里完成就可以了，修改配置后，重启服务即可生效

### 启动Sentinel（可选）

Sentinel是用来做服务的熔断、雪崩、限流，相当于原来的Hystrix，但是提供了更加强大的功能，如果想要了解Sentinel的更多操作，可以参考这两篇博客

- [【SpringCloud】使用Sentinel实现熔断和限流](http://moguit.cn/#/info?blogUid=408e9c889ebf96a66af2adfdc258ba5f)
- [CentOS下安装Sentinel](http://moguit.cn/#/info?blogUid=b100fde21ac0b61414dbaa74d2db7192)

首先进入到Sentinel的启动目录下进行启动

```bash
# 进入到sentinel目录
cd /soft/sentinel
# 启动Sentinel
./startup.sh
# 查看日志
```

然后进入到Sentinel的图形化界面

```bash
http://your_ip:8070
```

输入默认账号密码：sentinel  sentinel，进入到管理界面，更多关于Sentinel的操作，可以查看[这篇博客](http://moguit.cn/#/info?blogUid=408e9c889ebf96a66af2adfdc258ba5f)

![image-20200903162631281](images/image-20200903162631281.png)

### 启动微服务

启动 mogu_picture & mogu_sms & mogu_admin & mogu_web

```
#进入到项目目录
cd /home/mogu_blog
```

我们查看项目结构，有以下几个文件夹

![image-20200903163514966](images/image-20200903163514966-1599124136538.png)

下面说明每个文件夹的作用

```
mogu_admin：admin端API接口服务
mogu_data：存在蘑菇博客的图片资源
mogu_monitor：服务监控
mogu_picture: 图片服务器，用于图片上传和下载
mogu_sms: 消息发送服务器，用于邮件和短信发送
mogu_web：web端API接口服务
mogu_zipkin：链路追踪模块
vue_mogu_admin：VUE的后台管理页面
vue_mogu_web：VUE的门户网站
```

#### 启动Admin后台服务

首先进入mogu_admin目录下

我们查看一下目录结构

![image-20200209130224724](images/image-20200209130224724-1599124105899.png)

```
./startup.sh  #启动脚本
./shutdown.sh #关闭脚本
mogu_admin***.jar #springboot打包的可执行jar包
/config #外部配置文件
catalina.out #启动脚本后，生成的日志文件
```

然后我们使用下面的命令进行启动

```
# 进入mogu_admin目录
cd mogu_admin
# 启动项目
./startup.sh
```

> tip：因为镜像中的代码可能不是最新版本，因此推荐在按照本篇博客，安装好docker环境后，需要在参考下面蘑菇博客部署阿里云这篇博客，重新将前后端代码都重新部署一遍，同时也记得把doc中的三个SQL文件也重新导入，确保服务器为最新代码~

### mogu_web修改配置

我们进入到nacos配置文件管理界面，找到的 mogu_web_prod.yaml文件

![image-20200903164514073](images/image-20200903164514073-1599124105900.png)

我们需要将下面的域名，改成自己的

```
data:
  # 门户页面
  webSite:
    url: http://101.132.122.175/:9527/#/
    # 有域名可以改成如下
    # url: http://www.moguit.cn/#/
    
  # mogu_web网址，用于第三方登录回调
  web:
    url: http://101.132.122.175/:8603
```

同时在配置文件的最下面，还需要修改第三方注册需要的 clientId 和 ClientSecret：如果不清楚如何获取的小伙伴，可以查看我的这篇博客，在后面部分对ID的获取有相关介绍：[SpringBoot+Vue如何集成第三方登录JustAuth](http://moguit.cn/#/info?blogUid=8cbadb54967257f12d6cc7eb1a58a361)

```yml
# 第三方登录
justAuth:
  clientId:
    gitee: XXXXXXXXXXXXXXXXXXXXXX
    github: XXXXXXXXXXXXXXXXXXXXXX
  clientSecret:
    gitee: XXXXXXXXXXXXXXXXXXXXXX
    github: XXXXXXXXXXXXXXXXXXXXXX
```

修改完成后，启动项目

```bash
# 进入mogu_web目录
cd mogu_web
# 启动项目
./startup.sh
```

###  mogu_sms修改配置

我们进入到nacos配置文件管理界面，找到的 mogu_sms_prod.yaml文件

![image-20200903164316451](images/image-20200903164316451-1599124105900.png)

在mogu_sms中，主要修改的就是邮箱的配置，我们将发送邮件的信息改成自己的

```yml
#mail
mail:
    username: XXXXXXX@163.com
    password: XXXXXXX #授权码开启SMTP服务里设置
```

注意，上面的password是授权码，授权码不是密码，以163邮箱为例，我们需要开启SMTP服务，然后设置授权码

![image-20200722090457339](images/image-20200722090457339-1599124105900.png)



修改完成后，我们启动对应的项目即可，最终我们需要启动的项目有： mogu_picture, mogu_sms, mogu_admin, mogu_web、mogu_gateway

**tip:（用于以后使用图形化客户端进行连接）**

mysql的账号和密码是 root  mogu2018

redis的密码是 mogu2018

## 验证是否后台是否启动成功

等服务器都启动完成后，下面我们验证一下后台是否正常启动，回到我们的Nacos管理界面

```
http://your_ip:8848/nacos
```

如果我们看到下面五个服务都注册到Nacos中，那说明启动成功

- mogu_picture
- mogu_sms
- mogu_admin
- mogu_web
- mogu_gateway

如下图所示

![image-20201212144805069](images/image-20201212144805069.png)

我们在通过访问下列swagger接口，测试接口是否正常

```
http://your_ip:8601/swagger-ui/index.html
http://your_ip:8603/swagger-ui/index.html
```

如果能够进入下面页面的话，说明后台是没有问题的了，下面我们可以验证一下接口

![img](images/f7aac7c1d46e41fb88cce5918318f509)

验证登录

![img](images/84ed060923214f7cb8df77f0b6bc512a)

然后执行完成后，复制到token

![img](images/ec60e235b7264864a404abc8cd24248f)

然后在swagger页面的右上角，有一个authorize的按钮，点击后，将token粘贴进去，即可操作全部接口进行测试了~

![img](images/03c6697dfd3148888215e2f38e99b775)

## 修改前端项目配置

下面我们需要修改前端地址，如果不修改的话，默认是请求的是蘑菇演示环境的后台接口【！！所以这里切记】

![image-20201130110943750](images/image-20201130110943750.png)

我们现在需要修改两个地方的配置，分别是：vue_mogu_admin 和 vue_mogu_web 目录下

### 修改vue_mogu_admin配置

```bash
# 进入dist目录
cd vue_mogu_admin/dist
# 找到index.html【为了方便，可以复制到windows下面修改】
vim index.html
```

然后把里面的ip地址，改成自己的 ip 即可

> 文件是被压缩的，可以使用在线格式化工具：[html在线格式化](https://tool.oschina.net/codeformat/html/)，优化后在进行编辑

![image-20201130105850074](images/image-20201130105850074.png)

注意，上面 `BLOG_WEB_URL` 地址的修改的时候，如果你拥有域名的话，就不要使用IP了

```bash
// 有域名
"BLOG_WEB_URL":"http://demoweb.moguit.cn"
// 没有域名
"BLOG_WEB_URL":"http://120.78.126.96:9527"
```

修改完成后，在把修改后的文件，替换服务器上的 index.html 即可

### 修改vue_mogu_web配置

修改 vue_mogu_web的过程和上面一致

```bash
# 进入dist目录
cd vue_mogu_web/dist
# 找到index.html【为了方便，可以复制到windows下面修改】
vim index.html
```

然后把里面的ip地址，改成自己的 ip 即可

![image-20201130110112326](images/image-20201130110112326.png)

注意，上面 `VUE_MOGU_WEB` 地址的修改的时候，如果你拥有域名的话，就不要使用IP了

```bash
// 有域名
"BLOG_WEB_URL":"http://demoweb.moguit.cn"
// 没有域名
"BLOG_WEB_URL":"http://120.78.126.96:9527"
```

修改完成后，在把修改后的文件，替换服务器上的 index.html 即可

## 访问蘑菇博客项目

### 访问前端项目

例如： http://youip:9527 

![image-20201110155005003](images/image-20201110155005003.png)

tip：需要注意的是，如果图片无法正常显示，请先登录后台管理页面，然后修改对应的域名

关于具体的配置，参考这篇博客：[蘑菇博客配置七牛云存储](http://www.moguit.cn/#/info?blogUid=735ed389c4ad1efd321fed9ac58e646b)

![image-20200903170244575](images/image-20200903170244575-1599124105901.png)

### 访问后端项目

120.78.126.96:9528       用户名和密码是： admin mogu2018 【如果登录不进去，请F12检查，请求的IP地址是否是自己的服务器，如果不是，那么参考前面修改前端项目配置，改成自己的服务器IP】

![image-20200209130547785](images/image-20200209130547785-1599124105901.png)

## 总结

好了，到目前为止，蘑菇博客已经搭建完成。当然小伙伴并不是拉取来就能直接用的， 如果ip地址不一样的话，是不能直接使用的，后面的话，需要拉取源码后，修改对应的配置信息后，然后在打包部署，才能够使用的。