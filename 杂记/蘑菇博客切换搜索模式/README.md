# 蘑菇博客切换搜索模式

## 前言

起初蘑菇博客使用的Solr作为全文检索的，然后在代码开源后，发现留言区有些小伙伴更偏向于使用ElasticSearch作为检索工具，因此特意将蘑菇博客改成支持ElasticSearch检索的，但是有些小伙伴又还是想保留原来的Solr，后面我就想了个方法，也就是将同时支持多种搜索模式，Solr、ElasticSearch、SQL语句

## 切换SQL语句搜索

SQL语句即使用like关键字去匹配博客的title和summary，然后返回结果，实现起来比较简单，同时我们不需要额外的启动全文检索服务，因此能够更加节省服务器的内存消耗，适用于博客部署在入门级的1核2G服务器

SQL搜索集成在mogu-web中，因此我们不需要启动mogu-search服务，只需要修改前端搜索请求接口即可

接口位置：

```
mogu_web\src\main\java\com\moxi\mogublog\web\restapi\SearchRestApi.java
```

修改前端接口

```
vue_mogu_web\src\api\search.js
```

把通过SQL搜索博客的方法开启，把Solr和ElasticSearch的方法注释

```
/**
 * 通过SQL搜索博客
 * @param params
 */
export function searchBlog (params) {
  return request({
    url: process.env.WEB_API + '/search/sqlSearchBlog',
    method: 'get',
    params
  })
}

```

## 切换Solr搜索

Solr是一个高性能，采用Java开发，SolrSolr基于Lucene的全文搜索服务器。同时对其进行了扩展，提供了比Lucene更为丰富的查询语言，同时实现了可配置、可扩展并对查询性能进行了优化，并且提供了一个完善的功能管理界面，是一款非常优秀的全文搜索引擎。

关于CentOS下Solr的安装可以参考：[CentOS下Solr的安装和部署](http://www.moguit.cn/#/info?blogUid=7c7404c456904be5b7736238f28d2515)

关于Window下安装，最近很多小伙伴说solr不好配置，所以我特意把solr的上传到百度云了，小伙伴只需要下载后，放到tomcat的webapps目录下，然后修改一下solrhome的配置即可：

```
链接：https://pan.baidu.com/s/1gpKs7oixT8RBn8zuDSiEGQ 
提取码：ditj 
```

我们打开刚刚的解压的目录下的这个文件

```
solr\WEB-INF\web.xml
```

修改里面的地址，把路径改成你的solr_home目录即可

```
    <env-entry>
       <env-entry-name>solr/home</env-entry-name>
       <env-entry-value>E:\Software\xampp\tomcat\webapps\solr\solr_home</env-entry-value>
       <env-entry-type>java.lang.String</env-entry-type>
    </env-entry>
```

下面我们开始将solr接入到mogu-search服务中，并提供全文检索服务

首先我们找到下面的接口位置，在这里面定义了一些 全文搜索、更新索引、删除索引、添加索引、初始化索引等方法，其中有些方法提供给其它服务消费的，比如更新索引、删除索引、添加索引都是在mogu-admin中博客的增删改操作后，通过rabbitmq发送消息到mogu-sms，然后mogu-sms根据对应的博客操作，调用mogu-search中对应的接口。

```
mogu_search\src\main\java\com\moxi\blog\elasticsearch\restapi\SolrRestApi.java
```

修改前端接口，打开下面的文件

```
vue_mogu_web\src\api\search.js
```

把通过Solr搜索博客的方法开启，把SQL和ElasticSearch的方法注释

```
/**
 * 通过solr搜索博客
 * @param params
 */
export function searchBlog (params) {
  return request({
    url: process.env.ELASTICSEARCH + '/search/solrSearchBlog',
    method: 'get',
    params
  })
}
```

然后，打开mogu-search的pom.xml，添加solr相关的依赖，同时把ElasticSearch的依赖注释

```
<!-- 引入Solr相关依赖 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-solr</artifactId>
</dependency>
```

在application.yml文件中，添加solr的配置，同时把elasticSearch的配置注释掉

```
  data:
    solr:
      host: http://localhost:8080/solr
      core: collection1
      repositories:
        enabled: true
```

然后在 mogu-search中config文件文件夹下的SolrConfig注释打开即可

最后到mogu-sms服务下，找到下面的类

```
mogu_sms\src\main\java\com\moxi\mogublog\sms\listener\BlogListener.java
```

把里面的solr注释全部打开，然后ElasticSearch相关接口注释

```
 // 删除Solr博客索引
 searchFeignClient.deleteSolrIndexByUids(uid);
 
// 增加solr索引
searchFeignClient.addSolrIndexByUid(uid);

// 更新Solr索引
searchFeignClient.updateSolrIndexByUid(uid);

// 删除Solr索引
searchFeignClient.deleteSolrIndexByUid(uid);
```



## 切换ElasticSearch搜索

ElasticSearch是一个基于Lucene的搜索服务器。它提供了一个分布式多用户能力的全文搜索引擎，基于RESTful web接口。Elasticsearch是用Java语言开发的，并作为Apache许可条款下的开放源码发布，是一种流行的企业级搜索引擎。ElasticSearch用于云计算中，能够达到实时搜索，稳定，可靠，快速，安装使用方便。官方客户端在Java、.NET（C#）、PHP、Python、Apache Groovy、Ruby和许多其他语言中都是可用的。根据DB-Engines的排名显示，Elasticsearch是最受欢迎的企业搜索引擎，其次是Apache Solr，也是基于Lucene。

和刚刚介绍的Solr一样，首先我们找到下面的接口位置，在这里面定义了一些 全文搜索、更新索引、删除索引、添加索引、初始化索引等方法。这里的功能和方法和solr是一直的，目的就是为了降低我们切换搜索时候繁杂的操作。

首先我们需要在pom文件引入对应的依赖，因为我提前引入了，如果该依赖已经被注释，只需要删除注释即可，同时我们再把solr的依赖注释掉

```
<!--引入ElasticSearch相关依赖-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
</dependency>
```

下面是ElasticSearch对应的接口位置

```
mogu_search\src\main\java\com\moxi\blog\elasticsearch\restapi\ElasticSearchRestApi.java
```

同时我们需要把下面几个类的注释全部打开

- ElasticSearchRestApi.java
- ElasticSearchService.java
- BlogResposity
- EsBlogIndex

同时我们把solr相关代码注释

- SolrRestApi.java
- SolrSearchService
- SolrIndex
- SolrConfig

然后修改application.yml文件，引入elasticsearch配置，同时注释掉下方的solr配置

```
  data:
    elasticsearch:
      cluster-name: elasticsearch
      cluster-nodes: localhost:9300
```

最后修改前端接口，打开下面的文件

```
vue_mogu_web\src\api\search.js
```

把把ElasticSearch搜索博客的方法开启，把SQL和Solr的方法注释

```
/**
 * 通过ElasticSearch搜索博客
 * @param params
 */
export function searchBlog (params) {
  return request({
    url: process.env.ELASTICSEARCH + '/search/elasticSearchBlog',
    method: 'get',
    params
  })
}
```

最后到mogu-sms服务下，找到下面的类

```
mogu_sms\src\main\java\com\moxi\mogublog\sms\listener\BlogListener.java
```

把里面的solr注释全部打开，然后ElasticSearch相关接口注释

```
// 删除ElasticSearch博客索引
searchFeignClient.deleteElasticSearchByUids(uid);
 
// 增加ES索引
searchFeignClient.addElasticSearchIndexByUid(uid);

// 更新ES索引
searchFeignClient.addElasticSearchIndexByUid(uid);

// 删除ES索引
searchFeignClient.deleteElasticSearchByUid(uid);
```





