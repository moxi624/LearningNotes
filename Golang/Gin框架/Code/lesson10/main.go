/** Gin框架使用Json格式返回数据
 * @Description
 * @Author 陌溪
 * @Date 2020/9/16 22:10
 **/
package main

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"net/http"
)

/**
 * @Description 定义一个消息结构体
 **/
type msg struct{
	Name string
	Message string
	Age int
}

/**
 * @Description 主函数
 * @Param
 * @return
 **/
func main() {


	// 生成默认的路由
	r := gin.Default()

	r.GET("/json", func(context *gin.Context) {
		//data := map[string]interface{}{
		//	"name": "小王子",
		//	"age": 18,
		//	"message": "hello gin json",
		//}

		// gin中的 map[string]interface{} 类型
		data := gin.H{
			"name": "小王子",
			"age": 18,
			"message": "hello gin json",
		}

		data2 := msg{
			Name: "小王子",
			Age: 10,
			Message: "hello 小王子",
		}
		fmt.Println(data2)
		// Json的序列化，默认是使用Go内部的序列化
		context.JSON(http.StatusOK, data)
	})

	// 启动Server
	r.Run(":9090")
}