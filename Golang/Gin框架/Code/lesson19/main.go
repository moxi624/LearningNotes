/**
 * @Description Gin框架中 使用GORM
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
 * @Description UserInfo 用户信息
 **/
type UserInfo struct {
	ID uint
	Name string
	Gender string
	Hobby string
}

func main() {
	db, err := gorm.Open("mysql", "root:root@(localhost)/mogu_demo?charset=utf8mb4&parseTime=True&loc=Local")
	defer db.Close()
	if err != nil {
		fmt.Printf("connect msyql failed, err: %v \n", err)
		return
	}
	fmt.Println("connect mysql success")

	// 自动迁移【把结构体和数据表进行对应】
	db.AutoMigrate(&UserInfo{})

	// 创建记录
	u1 := UserInfo{
		ID: 1,
		Name: "陌溪",
		Gender: "男",
		Hobby: "看书",
	}
	db.Create(&u1)

	// 查询数据
	var u UserInfo
	db.First(&u)
	fmt.Printf("u: %#v \n", u)

	// 更新
	db.Model(&u).Update("hobby", "双色球")

	// 删除
	db.Delete(&u)

}