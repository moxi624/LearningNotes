/**
 * @Description 控制器
 * @Author 陌溪
 * @Date 2020/9/18 20:06
 **/
package controller

import (
	"bubble/entity"
	"github.com/gin-gonic/gin"
	"net/http"
)



/** url -> controller -> service -> dao -> entity 【请求来了 -> 控制器 -> 业务逻辑 -> 模型层的增删改查】
 * @Description 访问首页
 * @Param
 * @return
 **/
func IndexHandler(context *gin.Context) {
	context.HTML(http.StatusOK, "index.html", nil)
}

/**
 * @Description 创建Todo
 * @Param
 * @return
 **/
func CreateTodo(context *gin.Context) {
	// 从请求中把数据取出来
	var todo entity.Todo
	context.BindJSON(&todo)
	// 存入数据库
	err := entity.CreateTodo(&todo)
	// 响应
	if err != nil {
		context.JSON(http.StatusOK, gin.H{
			"error": err.Error(),
		})
	} else {
		context.JSON(http.StatusOK, todo)
	}
}

/**
 * @Description 查看所有代表事项
 * @Param
 * @return
 **/
func GetTodoList(context *gin.Context) {
	// 查询所有数据
	todoList, err := entity.GetTodoList()
	if err != nil {
		context.JSON(http.StatusOK, gin.H{
			"error": err.Error(),
		})
	} else {
		context.JSON(http.StatusOK, todoList)
	}
}

/**
 * @Description 更新代办事项
 * @Param
 * @return
 **/
func UpdateTodo(context *gin.Context) {
	id, ok := context.Params.Get("id")
	if !ok {
		context.JSON(http.StatusOK, gin.H{
			"error": "err",
		})
	} else {

		todo, err := entity.GetTodoById(id)
		if err != nil {
			context.JSON(http.StatusOK, gin.H{
				"error": err.Error(),
			})
		} else {
			// 将内容进行修改
			context.BindJSON(&todo)
			err = entity.UpdateTodo(todo)
			if err != nil {
				context.JSON(http.StatusOK, gin.H{
					"error": err.Error(),
				})
			} else {
				context.JSON(http.StatusOK, todo)
			}
		}
	}
}

/**
 * @Description 删除代办事项
 * @Param
 * @return
 **/
func DeleteTodo(context *gin.Context) {
	id, ok := context.Params.Get("id")
	if !ok {
		context.JSON(http.StatusOK, gin.H{
			"error": "err",
		})
	} else {
		var todo entity.Todo
		err := entity.DeleteTodo(id)
		if err != nil {
			context.JSON(http.StatusOK, gin.H{
				"error": err,
			})
		} else {
			context.JSON(http.StatusOK, todo)
		}
	}
}
