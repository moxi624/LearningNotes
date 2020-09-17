/** Gin框架渲染博客
 * @Description
 * @Author 陌溪
 * @Date 2020/9/16 22:10
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
	r := gin.Default()
	// 模板解析

	// 使用全局匹配规则
	r.LoadHTMLGlob(wd + "/lesson09-2/templates/*")

	r.Static("/static", wd + "/lesson09-2/static")

	// 博客日记 模板渲染
	r.GET("/bkrj", func(context *gin.Context) {
		// HTTP请求
		context.HTML(http.StatusOK, "bkrj.tmpl", nil)
	})

	// 关于博主 模板渲染
	r.GET("/gybz", func(context *gin.Context) {
		// HTTP请求
		context.HTML(http.StatusOK, "gybz.tmpl", nil)
	})

	// 首页 模板渲染
	r.GET("/index", func(context *gin.Context) {
		// HTTP请求
		context.HTML(http.StatusOK, "index.tmpl", nil)
	})

	// 详情页 模板渲染
	r.GET("/info", func(context *gin.Context) {
		// HTTP请求
		context.HTML(http.StatusOK, "info.tmpl", nil)
	})

	// 时间轴 模板渲染
	r.GET("/sjz", func(context *gin.Context) {
		// HTTP请求
		context.HTML(http.StatusOK, "sjz.tmpl", nil)
	})

	// 碎言碎语 模板渲染
	r.GET("/sysy", func(context *gin.Context) {
		// HTTP请求
		context.HTML(http.StatusOK, "sysy.tmpl", nil)
	})

	// 我的相册 模板渲染
	r.GET("/wdxc", func(context *gin.Context) {
		// HTTP请求
		context.HTML(http.StatusOK, "wdxc.tmpl", nil)
	})

	// 启动Server
	r.Run(":8080")
}