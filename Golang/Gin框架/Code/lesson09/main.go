/** Gin框架的使用
 * @Description
 * @Author 陌溪
 * @Date 2020/9/16 22:10
 **/
package main

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"html/template"
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
	r := gin.Default()
	// 模板解析
	//r.LoadHTMLFiles(wd + "/lesson09/templates/users/index.tmpl", wd + "/lesson09/templates/posts/index.tmpl")

	// Gin框架中添加自定义的函数
	r.SetFuncMap(template.FuncMap{
		"safe": func(str string) template.HTML{
			return template.HTML(str)
		},
	})

	// 使用全局匹配规则
	r.LoadHTMLGlob(wd + "/lesson09/templates/**/*")

	r.Static("/xxx", wd + "/lesson09/static")

	// 模板渲染
	r.GET("/posts/index", func(context *gin.Context) {
		// HTTP请求
		context.HTML(http.StatusOK, "posts/index.tmpl", gin.H{
			"title": "<a href='http://www.moguit.cn/#/'>www.moguit.cn</a>",
		})
	})

	// 模板渲染
	r.GET("/users/index", func(context *gin.Context) {
		// HTTP请求
		context.HTML(http.StatusOK, "users/index.tmpl", gin.H{
			"title": "admin.moguit.cn",
		})
	})

	// 启动Server
	r.Run(":9090")
}