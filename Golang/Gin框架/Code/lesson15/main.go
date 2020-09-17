/**
 * @Description Gin框架中的 文件上传
 * @Author 陌溪
 * @Date 2020年9月17日17:12:04
 **/
package main

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"net/http"
	"os"
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

	// 找到模板路径
	wd, err := os.Getwd()
	if err != nil {
		fmt.Printf("get wd failed, err:%v \n", wd)
		return
	}
	// 加载模板
	r.LoadHTMLFiles(wd + "/lesson15/index.tmpl")

	/**
	 * @Description 跳转到文件上传页面
	 * @Param 
	 * @return 
	 **/
	r.GET("/index", func(context *gin.Context) {
		context.HTML(http.StatusOK, "index.tmpl", nil)
	})
	
	/**
	 * @Description 图片上传接口
	 * @Param 
	 * @return 
	 **/
	r.POST("/upload", func(context *gin.Context) {
		// 从请求中读取文件【和请求中获取携带的参数是一样的】
		fileObj, err := context.FormFile("f1")
		if err != nil {
			context.JSON(http.StatusBadRequest, gin.H{
				"error": err.Error(),
			})
		} else {
			// 将读取的文件保存到本地【服务端本地】
			dst := fmt.Sprintf("./%s", fileObj.Filename)
			context.SaveUploadedFile(fileObj, dst)
			context.JSON(http.StatusOK, gin.H{
				"success": "上传成功",
			})
		}
	})

	// 启动Server
	r.Run(":9090")
}