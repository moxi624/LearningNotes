# Go操作MySQL数据库

## 来源

http://moguit.cn/#/info?blogUid=3d1cc9ce434aeaf2187692eb0feea294

https://www.liwenzhou.com/posts/Go/go_mysql/

## 前言

常见的数据库有

- SqlLite
- MySQL
- SQLServer
- postgreSQL
- Oracle

MySQL主流的关系型数据库，类似的还有postgreSQL

关系型数据库：用表来存储一类的数据

表结构设计的三大范式：《漫画数据库》

## MySQL知识点

### SQL语句

DDL：操作数据库的

DML：表的增删改查

DCL：用户及权限

### 存储引擎

MySQL支持插件式的存储引擎

常见的存储引擎：MyISAM和InnoDB

#### MyISAM：

- 查询快
- 只支持表锁
- 不支持事务

#### InnoDB

- 整体速度快
- 支持表锁和行锁

### 事务

把多个SQL操作当成是一个整体

### 事务的特点

ACID就是事务的特性

- 原子性：事务要么成功要么失败，没有中间操作
- 一致性：数据库的完整性没有被破坏
- 隔离性：事务之间是相互隔离的
- 持久性：事务操作完成后，是持久化到数据库的，不会再次改变

### 索引

索引的原理是：B树和B+树

索引的类型和索引的命中

### 其它内容

分库分表

SQL注入

SQL慢查询优化

MySQL主从

MySQL读写分离

## Go操作数据库

Go语言中的`database/sql`包提供了保证SQL或类SQL数据库的泛用接口，并不提供具体的数据库驱动。使用`database/sql`包时必须注入（至少）一个数据库驱动。

我们常用的数据库基本上都有完整的第三方实现。例如：[MySQL驱动](https://github.com/go-sql-driver/mysql)

### database/sql

原生支持连接池，是并发安全的

这个标准库没有具体的实现，只是列出一些需要第三方库实现的具体内容

### 下载依赖

```bash
go get -u github.com/go-sql-driver/mysql
```

`go get`包的路径就是下载第三方的依赖，将第三方的依赖默认保存在 `$GOPATH/src`

### 使用驱动

 导入刚刚引入的包

```go
import (
	"database/sql"
	"fmt"
	_ "github.com/go-sql-driver/mysql"
)

// Go连接MySQL
func main() {
	// 连接数据库
	dsn := "root:root@tcp(127.0.0.1:3306)/dbname"

	// 连接MySQL数据库
	db, err := sql.Open("mysql", dsn)
	if err != nil {
		fmt.Printf("open %s failed, err: %v \n", dsn, err)
		return
	}
	// 注意这行代码要写在上面err判断的下面
	defer db.Close()

	// 尝试连接数据库
	err = db.Ping()
	if err != nil {
		fmt.Printf("open %s failed, err: %v, \n", dsn, err)
		return
	}
}
```

