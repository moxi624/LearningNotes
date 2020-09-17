/**
 * @Description Gin框架中的 Gin路由 和 路由组
 * @Author 陌溪
 * @Date 2020年9月17日18:36:05
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
	 * @Description 访问/index的GET请求会走这一条处理逻辑【】
	 * @Param 
	 * @return 
	 **/
	r.GET("/index", func(context *gin.Context) {
		context.JSON(http.StatusOK, gin.H{
			"code": "success",
			"message": "GET",
		})
	})

	r.POST("/index", func(context *gin.Context) {
		context.JSON(http.StatusOK, gin.H{
			"code": "success",
			"message": "POST",
		})
	})

	/**
	 * @Description PUT是更新操作
	 * @Param 
	 * @return 
	 **/
	r.PUT("/index", func(context *gin.Context) {
		context.JSON(http.StatusOK, gin.H{
			"code": "success",
			"message": "PUT",
		})
	})

	/**
	 * @Description DELETE是删除操作
	 * @Param
	 * @return
	 **/
	r.DELETE("/index", func(context *gin.Context) {
		context.JSON(http.StatusOK, gin.H{
			"code": "success",
			"message": "DELETE",
		})
	})

	/**
	 * @Description 当所有的路由都没匹配，返回404页面
	 * @Param
	 * @return 
	 **/
	r.NoRoute(func(context *gin.Context) {
		context.JSON(http.StatusNotFound, gin.H{
			"code": "404",
			"message": "NoRoute",
		})
	})

	// 路由组，把共用的前缀提取出来，创建一个路由组
	videoGroup := r.Group("/video")
	{
		videoGroup.GET("/index", func(context *gin.Context) {
			context.JSON(http.StatusOK, gin.H{
				"msg": "/video/index",
			})
		})
		videoGroup.GET("/about", func(context *gin.Context) {
			context.JSON(http.StatusOK, gin.H{
				"msg": "/video/about",
			})
		})
		videoGroup.GET("/home", func(context *gin.Context) {
			context.JSON(http.StatusOK, gin.H{
				"msg": "/video/home",
			})
		})
	}


	// 启动Server
	r.Run(":8080")
}