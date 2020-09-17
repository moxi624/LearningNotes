/**
 * @Description Gin框架中的 参数绑定，也就是将结构体和我们的参数进行绑定
 * @Author 陌溪
 * @Date 2020年9月17日17:12:04
 **/
package main

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"net/http"
)

type UserInfo struct {
	Username string `form:"username" json:"username" binding:"required"`
	Password string `form:"password" json:"password" binding:"required"`
}
/**
 * @Description 主函数
 * @Param
 * @return
 **/
func main() {
	// 找到模板路径
	// 生成默认的路由
	r := gin.Default()

	// 请求方式   http://localhost:9090/user?username=zhangsan&password=123
	r.GET("/user", func(context *gin.Context) {
		//username := context.Param("username")
		//password := context.Param("password")
		//u := UserInfo{
		//	UserName: username,
		//	Password: password,
		//}

		var userInfo UserInfo
		// 传递的是指针，将参数绑定到UserInfo中
		err := context.ShouldBind(&userInfo)
		fmt.Println(userInfo)
		if err != nil {
			context.JSON(http.StatusBadRequest, gin.H{
				"error": err.Error(),
			})
		} else {
			context.JSON(http.StatusOK, gin.H{
				"status": "ok",
				"userInfo": userInfo,
			})
		}
	})

	r.GET("/form", func(context *gin.Context) {
		var userInfo UserInfo
		// 传递的是指针，将参数绑定到UserInfo中
		err := context.ShouldBind(&userInfo)
		fmt.Println(userInfo)
		if err != nil {
			context.JSON(http.StatusBadRequest, gin.H{
				"error": err.Error(),
			})
		} else {
			context.JSON(http.StatusOK, gin.H{
				"status": "ok",
				"userInfo": userInfo,
			})
		}
	})
	
	/**
	 * @Description 前端发送Json格式的请求
	 * @Param
	 * @return
	 **/
	r.POST("/json", func(context *gin.Context) {

		var userInfo UserInfo
		// 传递的是指针，将参数绑定到UserInfo中
		err := context.ShouldBind(&userInfo)
		fmt.Println(userInfo)
		if err != nil {
			context.JSON(http.StatusBadRequest, gin.H{
				"error": err.Error(),
			})
		} else {
			context.JSON(http.StatusOK, gin.H{
				"status": "ok",
				"userInfo": userInfo,
			})
		}
	})


	// 启动Server
	r.Run(":9090")
}