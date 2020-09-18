/**
 * @Description 待办事项后台接口
 * @Author 陌溪
 * @Date 2020/9/18 16:02
 **/
package main

import (
	"bubble/dao"
	"bubble/entity"
	"bubble/routers"
	"fmt"
	_ "github.com/jinzhu/gorm/dialects/mysql"
)

func main() {
	err := dao.InitMySQL()
	if err != nil {
		panic(err)
	} else {
		fmt.Println("connect mysql success")
	}
	// 延迟关闭数据库
	defer dao.DB.Close()
	// 模型关闭 自动迁移【把结构体和数据表进行对应】
	dao.DB.AutoMigrate(&entity.Todo{})
	// 注册路由
	r := routers.SetRouter()
	r.Run(":8090")
}
