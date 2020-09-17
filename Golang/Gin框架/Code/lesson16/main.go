/**
 * @Description Gin框架中的 请求重定向
 * @Author 陌溪
 * @Date 2020年9月17日17:12:04
 **/
package main

import (
	"github.com/gin-gonic/gin"
	"net/http"
)

/**
 * @Description 主函数
 * @Param
 * @return
 **/
func main() {
	// 找到模板路径
	// 生成默认的路由
	r := gin.Default()


	/**
	 * @Description 请求重定向
	 * @Param 
	 * @return 
	 **/
	r.GET("/baidu", func(context *gin.Context) {
		context.Redirect(http.StatusMovedPermanently, "http://www.baidu.com")
	})

	/**
	 * @Description 请求重定向【返回的是/b的数据】
	 * @Param
	 * @return
	 **/
	r.GET("/a", func(context *gin.Context) {
		// 把请求的URI修改
		context.Request.URL.Path = "/b"
		// 继续后续的处理，重新请求 /b
		r.HandleContext(context)
	})

	r.GET("/b", func(context *gin.Context) {
		context.JSON(http.StatusOK, gin.H{
			"success": "我是/b",
		})
	})

	// 启动Server
	r.Run(":9090")
}