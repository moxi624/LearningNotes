## 为什么要做数据库版本管理

平时是否遇到下列的问题

- 该服务器上的数据库处于什么状态？
- 此SQL脚本是否已经应用？谁执行的？
- 生产中的快速修复是否已在测试中应用？和项目版本匹配吗？
- 如何设置新的数据库实例？

## 什么是liquibase

**liquibase** 是一个数据库变更的版本控制工具。项目中通过 **liquibase** 解析用户编写的 **liquibase** 的配置文件,生成 **SQL** 语句，并执行和记录。

执行是根据记录确定 **SQL** 语句是否曾经执行过，和配置文件里的预判断语句确定 **SQL** 是否执行。

> 官方文档：http://www.liquibase.org/documentation/index.html

**liquibase** 开源版使用 **Apache 2.0** 协议。

## liquibase的优点

- 配置文件支持 **SQL**、**XML**、**JSON** 或者 **YAML**
- 版本控制按序执行
- 可以用上下文控制 **SQL** 在何时何地如何执行。
- 支持 **schmea** 的变更
- 根据配置文件自动生成 **SQL** 语句用于预览
- 可重复执行迁移
- 可插件拓展
- 可回滚
- 支持多种运行方式，如命令行、Spring集成、Maven插件、Gradle插件等。
- 可兼容 **14** 种主流数据库如 **Oracle**，**MySQL**，**PG**  等，支持平滑迁移
- 支持 **schema** 方式的多租户（**multi-tenant**）

## liquibase的基本概念

- **changeSet** 执行 **SQL** 的并记录、版本控制的最小单元。即每条 **changeSet** 生成1条执行记录，版本控制是基于执行记录的。
- changelog 即执行记录。由changeSet执行后产生的记录。记录默认保存在databasechangelog表中，此表由liquibase自动生成。包含id,author,filename,dateexcuted,orderexcuted,exectype,md5sum等字段。
- databasechangeloglock。liquibase的锁表。liquibase在执行前更新此表的locked为true，执行完liquibase的工作，将locked更新为false，适合集群使用。

###### 官网地址

## SpringBoot集成 liquibase

### 首先引入 **liquibase** 依赖

```xml
<dependency>
  <groupId>org.liquibase</groupId>
  <artifactId>liquibase-core</artifactId>
</dependency>
```

##### 2.2 添加配置类(也可以在`application.yml`中配置)

1、 在代码中添加`LiquibaseConfig`类,用于`liquibase`的基本配置

```java
import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LiquibaseConfig {

  @Bean
  public SpringLiquibase liquibase(DataSource dataSource) {
    SpringLiquibase liquibase = new SpringLiquibase();
    liquibase.setDataSource(dataSource);
    //指定changelog的位置，这里使用的一个master文件引用其他文件的方式
    liquibase.setChangeLog("classpath:liquibase/master.xml");
    liquibase.setContexts("development,test,production");
    liquibase.setShouldRun(true);
    return liquibase;
  }

}
```

1、 在`application.yml`中进行基本配置

```yaml
 # liquibase配置
liquibase:
    enabled: true  # 开启liquibase 对数据库的管理功能
    change-log: "classpath:/db/changelog/db.changelog-master.yaml"  #主配置文件的路径
    contexts: dev # 引用立秋脚本的上下文,如果存在多个开发环境的话[生产\开发\测试\]
    check-change-log-location: true # 检查changlog的文件夹是否存在
    rollback-file:  classPath:/data/backup.sql # 执行更新的时候写入回滚的SQL文件
```

##### 2.3 添加`liquibase` 核心文件

`master.xml`是主配置文件,用于加载日志文件或者是原有的系统数据库文件

```xml
<databaseChangeLog
        xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
         http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.1.xsd">

    <!--includeAll 标签可以把一个文件夹下的所有 changelog 都加载进来。如果单个加载可以用 include。
    includeAll 标签里有两个属性：path 和 relativeToChangelogFile。

    <includeAll path="liquibase/changelogs/" relativeToChangelogFile="false"/>
    -->

    <include file="liquibase/changelogs/jdbc.sql" relativeToChangelogFile="false"/>
    <include file="liquibase/changelogs/changelog-1.0.xml" relativeToChangelogFile="false"/>
</databaseChangeLog>
```

`changelog`文件

```xml
<databaseChangeLog
        xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
         http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.1.xsd">

    <!-- 创建表 -->
    <changeSet id="20200508001" author="xc">
        <createTable tableName="project_info">
            <column name="project_id" type="varchar(64)" encoding="utf8" remarks="项目id">
                <constraints primaryKey="true" nullable="false"/>
            </column>
            <column name="project_name" type="varchar(255)" encoding="utf8" remarks="项目名字">
                <!-- 是否可以为空 -->
                <constraints  nullable="false"/>
            </column>
            <column name="project_difficulty" type="float" encoding="utf8" remarks="项目难度"/>
            <column name="category_id" type="varchar(64)" encoding="utf8" remarks="项目类型类目编号"/>
            <column name="project_status" type="int(11)" encoding="utf8" remarks="项目状态, 0招募中，1 进行中，2已完成，3失败，4延期，5删除"/>
            <column name="project_desc" type="varchar(512)" encoding="utf8" remarks="项目简介"/>
            <column name="project_creater_id" type="varchar(64)" encoding="utf8" remarks="项目创建者id"/>
            <column name="team_id" type="varchar(64)" encoding="utf8" remarks="项目所属团队id"/>
            <column name="create_time" type="bigint(64)" encoding="utf8" remarks="创建时间"/>
            <column name="update_time" type="bigint(64)" encoding="utf8" remarks="更新时间"/>
        </createTable>
    </changeSet>
    <!-- 添加字段 -->
    <changeSet id="20200508002" author="xc"> 
        <addColumn tableName="project_info"> 
            <column name="phonenumber" type="varchar(255)" encoding="utf-8" remarks="项目负责人联系电话"/> 
        </addColumn> 
    </changeSet>
    <!-- 删除字段 -->
    <changeSet id="20200508003" anthor="xc">
        <dropColumn tableName="project_info" columnName="phonenumber"/>
    </changeSet>

    <!-- 操作数据 -->
    <changeSet id="3" author="xc"> 
        <code type="section" width="100%"> 
        <insert tableName="project_info"> 
            <column name="id" valueNumeric="3"/> 
            <column name="project_name" value="Manassas Beer Company"/> 
        </insert> 
        <insert tableName="project_info"> 
            <column name="id" valueNumeric="4"/> 
            <column name="project_name" value="Harrisonburg Beer Distributors"/> 
        </insert> 
    </changeSet>
    <!-- 引入sql脚本文件 -->
    <changeSet id="6" author="xc"> 
        <sqlFile path="insert-distributor-data.sql"/> 
    </changeSet>
    <changeSet id="000000000000044" author="hc">
        <sqlFile dbms="mysql"  endDelimiter=";;" encoding="UTF-8"
            path="config/liquibase/changelog/functions.sql"/>
    </changeSet>

    <changeSet>
        <!-- 外键、索引的创建语句会影响到本语句的执行，所以将其都放到另外的changeSet中单独去执行 -->
        <modifySql dbms="mysql">
            <append value="ENGINE=INNODB CHARSET=UTF8 COLLATE utf8_unicode_ci"/>
        </modifySql>
    </changeSet>


</databaseChangeLog>
```