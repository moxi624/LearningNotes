package main
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
