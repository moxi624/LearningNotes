/**
 * @Description Gin框架 接收 URL Path上的参数 【注意，返回的都是string类型】
 * @Author 陌溪
 * @Date 2020年9月17日16:26:43
 **/
package main

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"net/http"
	"os"
)

/**
 * @Description 主函数
 * @Param
 * @return
 **/
func main() {
	// 找到模板路径
	wd, err := os.Getwd()
	if err != nil {
		fmt.Printf("get wd failed, err:%v \n", wd)
		return
	}

	// 生成默认的路由
	r := gin.Default()

	r.LoadHTMLFiles(wd + "/lesson13/index.tmpl")

	r.GET("/user/:name/:age", func(context *gin.Context) {
		name := context.Param("name")
		age := context.Param("age")
		data := gin.H{
			"name": name,
			"age": age,
		}
		context.HTML(http.StatusOK, "index.tmpl", data)
	})

	r.GET("/posts/:name/:age", func(context *gin.Context) {
		name := context.Param("name")
		age := context.Param("age")
		data := gin.H{
			"name": name,
			"age": age,
		}
		context.HTML(http.StatusOK, "index.tmpl", data)
	})


	// 启动Server
	r.Run(":9090")
}