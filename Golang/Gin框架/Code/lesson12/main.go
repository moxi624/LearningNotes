/**
 * @Description Gin框架 接收 Form表单提交的参数
 * @Author 陌溪
 * @Date 2020年9月17日15:12:32
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

	r.LoadHTMLFiles(wd + "/lesson12/login.html", wd + "/lesson12/home.html", wd + "/lesson12/index.tmpl")

	r.GET("/login", func(context *gin.Context) {
		context.HTML(http.StatusOK, "login.html", nil)
	})

	r.POST("/login", func(context *gin.Context) {

		username := context.PostForm("username")
		password := context.PostForm("password")

		if username == "admin" && password == "admin" {
			context.HTML(http.StatusOK, "home.html", username)
		} else {
			context.HTML(http.StatusOK, "index.tmpl", nil)
		}

	})



	// 启动Server
	r.Run(":9090")
}