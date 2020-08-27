package main
import (
	"database/sql"
	"fmt"
	_"github.com/go-sql-driver/mysql"
)

// 定义一个全局的DB，是一个连接池对象
var db *sql.DB

func initDB()(err error)  {
	// 连接数据库
	dsn := "root:root@tcp(127.0.0.1:3306)/mogu_demo"

	// 连接MySQL数据库（注意不能使用 := ）
	db, err = sql.Open("mysql", dsn)
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
	fmt.Println("连接数据库成功")
	return
}

type user struct {
	id   string
	name  string
	age int
}

// 查询操作
func query()  {
	sqlStr := "select id, name, age from user where id > ?"
	rows, err := db.Query(sqlStr, 0)
	if err != nil {
		fmt.Println()
		fmt.Printf("query failed, err:%v\n", err)
		return
	}
	// 非常重要：关闭rows释放持有的数据库链接
	defer rows.Close()

	// 循环读取结果集中的数据
	for rows.Next() {
		var u user
		err := rows.Scan(&u.id, &u.name, &u.age)
		if err != nil {
			fmt.Printf("scan failed, err:%v\n", err)
			return
		}
		fmt.Printf("id:%d name:%s age:%d\n", u.id, u.name, u.age)
	}
}

// Go连接MySQL
func main() {
	err := initDB()
	if err != nil {
		fmt.Println("数据库初始化失败")
	}

	// 查询单条记录
	query()

}
