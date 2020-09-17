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

	// 尝试连接数据库
	err = db.Ping()
	if err != nil {
		fmt.Printf("open %s failed, err: %v, \n", dsn, err)
		return
	}
	// 设置数据库连接池的最大连接数
	db.SetMaxOpenConns(10)
	// 设置最大空闲连接数
	db.SetMaxIdleConns(5)

	fmt.Println("连接数据库成功")
	return
}

type user struct {
	id   string
	name  string
	age int
}

// 查询单条数据示例
func queryRowDemo() {
	sqlStr := "select id, name, age from user where id=?"
	var u user
	// 非常重要：确保QueryRow之后调用Scan方法，否则持有的数据库链接不会被释放
	err := db.QueryRow(sqlStr, 1).Scan(&u.id, &u.name, &u.age)
	if err != nil {
		fmt.Printf("scan failed, err:%v\n", err)
		return
	}
	fmt.Printf("id:%d name:%s age:%d\n", u.id, u.name, u.age)
}

// 查询操作
func query(id int)  {
	sqlStr := "select id, name, age from user where id > ?"
	rows, err := db.Query(sqlStr, id)
	if err != nil {
		fmt.Println()
		fmt.Printf("query failed, err:%v\n", err)
		return
	}
	// 非常重要：关闭rows释放持有的数据库链接，相当于丢到池子里面
	// 其它的对象才能够从对象中获取
	defer rows.Close()

	// 循环读取结果集中的数据
	for rows.Next() {
		var u user
		// 调用Scan才会释放我们的连接
		err := rows.Scan(&u.id, &u.name, &u.age)
		if err != nil {
			fmt.Printf("scan failed, err:%v\n", err)
			return
		}
		fmt.Printf("id:%d name:%s age:%d\n", u.id, u.name, u.age)
	}
}

// 插入数据
func insertRowDemo(id int, userName string, age int) {
	sqlStr := "insert into user(id, name, age) values (?,?,?)"
	ret, err := db.Exec(sqlStr, id, userName, age)
	if err != nil {
		fmt.Printf("insert failed, err:%v\n", err)
		return
	}
	theID, err := ret.LastInsertId() // 新插入数据的id
	if err != nil {
		fmt.Printf("get lastinsert ID failed, err:%v\n", err)
		return
	}
	fmt.Printf("insert success, the id is %d.\n", theID)
}

// 更新数据
func updateRowDemo() {
	sqlStr := "update user set age=? where id = ?"
	ret, err := db.Exec(sqlStr, 39, 3)
	if err != nil {
		fmt.Printf("update failed, err:%v\n", err)
		return
	}
	n, err := ret.RowsAffected() // 操作影响的行数
	if err != nil {
		fmt.Printf("get RowsAffected failed, err:%v\n", err)
		return
	}
	fmt.Printf("update success, affected rows:%d\n", n)
}

// 删除数据
func deleteRowDemo() {
	sqlStr := "delete from user where id = ?"
	ret, err := db.Exec(sqlStr, 3)
	if err != nil {
		fmt.Printf("delete failed, err:%v\n", err)
		return
	}
	n, err := ret.RowsAffected() // 操作影响的行数
	if err != nil {
		fmt.Printf("get RowsAffected failed, err:%v\n", err)
		return
	}
	fmt.Printf("delete success, affected rows:%d\n", n)
}

// Go连接MySQL
func main() {
	err := initDB()
	if err != nil {
		fmt.Println("数据库初始化失败")
	}

	// 查询单条语句
	queryRowDemo()

	// 插入数据
	insertRowDemo(4,"王五", 23)

	// 查询多条语句
	query(0)


}
