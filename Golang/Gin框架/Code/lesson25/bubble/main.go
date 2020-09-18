/**
 * @Description 待办事项后台接口
 * @Author 陌溪
 * @Date 2020/9/18 16:02
 **/
package main

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/mysql"
	"net/http"
)

/**
 * @Description 定义全局的数据库对象
 **/
var (
	DB *gorm.DB
)

/**
 * @Description 实体类
 * @Param
 * @return
 **/
type Todo struct {
	ID int `json:"id"`
	Title string `json:"title"`
	Status bool `json:"status"`
}

/**
 * @Description 初始化mysql
 * @Param 
 * @return err
 **/
func initMySQL() (err error) {
	DB, err = gorm.Open("mysql", "root:root@(localhost)/mogu_demo?charset=utf8mb4&parseTime=True&loc=Local")
	if err != nil {
		fmt.Printf("connect msyql failed, err: %v \n", err)
		return
	}
	// 测试是否能够连通
	return DB.DB().Ping()
}

func main() {

	err := initMySQL()
	if err != nil {
		panic(err)
	} else {
		fmt.Println("connect mysql success")
	}
	// 延迟关闭数据库
	defer DB.Close()

	// 模型关闭 自动迁移【把结构体和数据表进行对应】
	DB.AutoMigrate(&Todo{})

	// 定义Gin默认路由
	r := gin.Default()
	// 告诉Gin框架模板文件引用的静态文件去哪里找
	r.Static("/static", "static")
	// 告诉Gin框架去哪里找模板文件
	r.LoadHTMLGlob("templates/*")

	// 访问待办事项首页
	r.GET("/", func(c *gin.Context) {
		c.HTML(http.StatusOK, "index.html", nil)
	})

	// v1  待办事项
	// 前端页面填写代办事项，点击提交就会发送请求到这里
	v1Group := r.Group("v1")
	{
		// 添加
		v1Group.POST("/todo", func(context *gin.Context) {
			// 从请求中把数据取出来
			var todo Todo
			context.BindJSON(&todo)
			// 存入数据库
			err := DB.Create(&todo).Error
			// 响应
			if err != nil {
				context.JSON(http.StatusOK, gin.H{
					"error": err.Error(),
				})
			} else {
				context.JSON(http.StatusOK, todo)
			}
		})
		// 查看【查看所有】
		v1Group.GET("/todo", func(context *gin.Context) {
			// 查询所有数据
			var todoList [] Todo
			err := DB.Find(&todoList).Error
			if err != nil {
				context.JSON(http.StatusOK, gin.H{
					"error": err.Error(),
				})
			} else {
				context.JSON(http.StatusOK, todoList)
			}
		})
		// 查看【查看某个】
		v1Group.GET("/todo/:id", func(context *gin.Context) {

		})
		// 修改
		v1Group.PUT("/todo/:id", func(context *gin.Context) {
			id, ok := context.Params.Get("id")
			if !ok {
				context.JSON(http.StatusOK, gin.H{
					"error": err.Error(),
				})
			} else {
				var todo Todo
				err := DB.Where("id=?", id).First(&todo).Error
				if err != nil {
					context.JSON(http.StatusOK, gin.H{
						"error": err.Error(),
					})
				} else {
					// 将内容进行修改
					context.BindJSON(&todo)
					err = DB.Save(&todo).Error
					if err != nil {
						context.JSON(http.StatusOK, gin.H{
							"error": err.Error(),
						})
					} else {
						context.JSON(http.StatusOK, todo)
					}
				}
			}
		})

		// 删除
		v1Group.DELETE("/todo/:id", func(context *gin.Context) {
			id, ok := context.Params.Get("id")
			if !ok {
				context.JSON(http.StatusOK, gin.H{
					"error": err.Error(),
				})
			} else {
				var todo Todo
				err := DB.Where("id=?", id).Delete(todo)
				if err != nil {
					context.JSON(http.StatusOK, gin.H{
						"error": err,
					})
				} else {
					context.JSON(http.StatusOK, todo)
				}
			}
		})
	}

	r.Run(":8080")
}
