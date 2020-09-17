/**
 * @Description Gin框架中的 中间件
 * @Author 陌溪
 * @Date 2020年9月17日20:54:58
 **/
package main

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"net/http"
	"time"
)

/**
 * @Description 定义一个中间件
 * @Param context
 * @return
 **/
func indexHandle(context *gin.Context) {
	fmt.Println("index")
	context.JSON(http.StatusOK, gin.H{
		"code": "success",
		"message": "GET",
	})
}

/**
 * @Description 定义第一个中间件
 * @Param context
 * @return
 **/
func timeHandle(context *gin.Context) {
	fmt.Println("index2")
	// 获取开始时间
	start := time.Now()
	time.Sleep(time.Second)
	context.JSON(http.StatusOK, gin.H{
		"code": "success",
		"message": "GET",
	})

	// 调用该请求的剩余处理程序
	context.Next()

	// 计算耗时
	cost := time.Since(start)
	fmt.Println("消耗的时间:", cost)
}

/**
 * @Description 鉴权中间件
 * @Param doCheck 是否开启校验
 * @return
 **/
func authMiddleware(doCheck bool)gin.HandlerFunc {
	// 连接数据库
	// 或者一些其它工作
	return func(context *gin.Context) {
		// 存放具体的逻辑
		// 是否登录的判断
		// if是登录用户
		// c.Next()
		// else
		// c.Abort()
	}
}

/**
 * @Description 主函数
 * @Param
 * @return
 **/
func main() {
	// 找到模板路径
	// 生成默认的路由【默认包含了Logger() 和 Recovery() 中间件】
	r := gin.Default()

	// 全局注册中间件【全局注册后，就不需要在其它地方在写一次了】
	r.Use(timeHandle)

	/**
	 * @Description index方法
	 * @Param 
	 * @return 
	 **/
	// r.GET("/index", indexHandle, timeHandle)

	r.GET("/index", indexHandle)

	r.GET("/home", indexHandle)

	r.GET("/about", indexHandle)


	// 启动Server
	r.Run(":8080")
}