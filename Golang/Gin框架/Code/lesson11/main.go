/**
 * @Description Gin框架使用 QueryString，从前台接收参数
 * @Author 陌溪
 * @Date 2020年9月17日14:52:33
 **/
package main

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"net/http"
)

/**
 * @Description 主函数
 * @Param
 * @return
 **/
func main() {

	// 生成默认的路由
	r := gin.Default()

	// GET请求 URL ?后面是querystring参数
	r.GET("/web", func(context *gin.Context) {

		// 获取浏览器那边发请求携带的query string参数
		// http://localhost:9090/web?keyword=123   ->  这样就能获取到keyword
		keyword := context.Query("keyword")

		// 获取UserName，如果不存在就取默认值
		userName := context.DefaultQuery("userName", "default user")
		fmt.Println(userName)

		// 取不到就返回false
		name, ok := context.GetQuery("name")
		if ok {
			fmt.Println("没有获取到name")
			return
		}
		fmt.Print(name)


		// gin中的 map[string]interface{} 类型
		data := gin.H{
			"name": "小王子",
			"age": 18,
			"message": "hello gin json",
			"keyword": keyword,
		}

		// Json的序列化，默认是使用Go内部的序列化
		context.JSON(http.StatusOK, data)
	})

	// 启动Server
	r.Run(":9090")
}