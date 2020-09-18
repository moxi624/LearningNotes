/**
 * @Description Gin框架中 GORM更新操作
 * @Author 陌溪
 * @Date 2020年9月18日10:57:57
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
	gorm.Model
	Name string
	Age int64
	// 用户是否激活
	Active bool
}

func main() {

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

	// 创建记录
	u1 := User{Name: "moxi", Age: 18, Active: true}
	db.Create(&u1)

	u2 := User{Name: "jinzhou", Age: 20, Active: true}
	db.Create(&u2)

	// 查询
	var user User
	db.First(&user)
	fmt.Printf("user: %v \n", user)

	// 修改
	user.Name = "qimi"
	user.Age = 99
	// 默认会修改全部的字段
	db.Debug().Save(&user)

	// 更新指定的字段
	db.Debug().Model(&user).Update("name", "小王子")

	// 忽略某个字段 【通过omit忽略active字段】
	m1 := map[string]interface{}{
		"name": "小溪",
		"age": 28,
		"active": true,
	}
	db.Model(&user).Omit("active").Updates(m1)




}