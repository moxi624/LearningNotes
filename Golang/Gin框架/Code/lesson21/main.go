/**
 * @Description Gin框架中 使用GORM增删改查
 * @Author 陌溪
 * @Date 2020年9月17日21:38:50
 **/
package main

// 导入mysql驱动
import (
	"fmt"
	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/mysql"
)

/**
 * @Description 定义模型
 **/
type User struct {
	ID int64
	Name string
	Age int64
	// 设置默认值【注意:通过tag定义字段的默认值，在创建记录时候生成的SQL语句会排除没有值或零值的字段。在将记录插入到数据库后，Gorm会】
	Gender string `gorm:"default:'男'"`
}

func main() {

	// 关于默认表名的修改函数
	gorm.DefaultTableNameHandler = func (db *gorm.DB, defaultTableName string) string  {
		return "prefix_" + defaultTableName;
	}

	db, err := gorm.Open("mysql", "root:root@(localhost)/mogu_demo?charset=utf8mb4&parseTime=True&loc=Local")
	defer db.Close()
	if err != nil {
		fmt.Printf("connect msyql failed, err: %v \n", err)
		return
	}
	fmt.Println("connect mysql success")

	// 禁用表名的负数形式【默认会在表名后面加s】
	db.SingularTable(false)

	// 自动迁移【把结构体和数据表进行对应】
	db.AutoMigrate(&User{})

	// 在代码层面创建一个结构体对象
	u1 := User{
		Name: "陌溪",
		Age: 16,
	}

	// 创建结构体 【这里推荐传递一个指针进去，直接传结构体存在一个拷贝的问题，如果结构体比较大，可能会有时间消耗】
	fmt.Println("判断主键是否为空:", db.NewRecord(&u1))  // true 因为数据库中没有记录
	db.Create(&u1)
	fmt.Println("判断主键是否为空:", db.NewRecord(&u1))  // false 因为数据库中有记录

	// 查询数据【&u是接收查询结果的】
	var u User
	// 传入u的地址，用于修改u的内容
	db.First(&u)
	fmt.Printf("u: %#v \n", u)

	// 查询多条记录
	var users []User
	// 通过DEBUG查看执行的SQL语句
	db.Debug().Find(&users)
	fmt.Printf("users: %#v \n", users)

	// where条件查询
	var whereUsers []User
	db.Where(map[string]interface{}{
		"ID": 1,
	}).Debug().Find(&whereUsers)
	fmt.Printf("where users: %#v \n", whereUsers)


	//// 更新
	//db.Model(&u).Update("hobby", "双色球")
	//
	//// 删除
	//db.Delete(&u)

}