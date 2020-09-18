/**
 * @Description
 * @Author 陌溪
 * @Date 2020/9/18 20:41
 **/
package routers

import (
	"bubble/controller"
	"github.com/gin-gonic/gin"
)

/**
 * @Description 注册路由
 * @Param
 * @return
 **/
func SetRouter() *gin.Engine {
	// 定义Gin默认路由
	r := gin.Default()
	// 告诉Gin框架模板文件引用的静态文件去哪里找
	r.Static("/static", "static")
	// 告诉Gin框架去哪里找模板文件
	r.LoadHTMLGlob("templates/*")

	// 访问待办事项首页
	r.GET("/", controller.IndexHandler)

	// v1  待办事项
	// 前端页面填写代办事项，点击提交就会发送请求到这里
	v1Group := r.Group("v1")
	{
		// 添加
		v1Group.POST("/todo", controller.CreateTodo)
		// 查看【查看所有】
		v1Group.GET("/todo", controller.GetTodoList)
		// 修改
		v1Group.PUT("/todo/:id", controller.UpdateTodo)
		// 删除
		v1Group.DELETE("/todo/:id",controller.DeleteTodo)
	}
	return r
}
