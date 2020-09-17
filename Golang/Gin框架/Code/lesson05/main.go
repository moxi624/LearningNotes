package main

import (
	"fmt"
	"html/template"
	"net/http"
	"os"
)

//定义用户结构体
type User struct {
	Name string
	Gender string
	Age int
}

func sayHello(w http.ResponseWriter, r *http.Request) {
	// 获取项目的绝对路径
	wd, err := os.Getwd()
	if err != nil {
		fmt.Printf("get wd failed, err:%v \n", wd)
		return
	}
	fmt.Println("wd:", wd + "\\lesson05\\index2.tmpl")
	// 解析指定文件生成模板对象
	tmpl, err := template.ParseFiles( wd + "\\lesson05\\index2.tmpl")

	if err != nil {
		fmt.Println("create templates failed, err:", err)
		return
	}

	// 采用结构体
	u1 := User{
		Name: "小王子",
		Gender: "男",
		Age: 10,
	}
	fmt.Println(u1)

	// 采用一个map
	m1 := map[string]interface{}{
		"Name": "小王子",
		"Age": 18,
		"Gender": "男",
	}

	m2 := map[string]interface{}{
		"map": m1,
		"users": u1,
	}
	fmt.Println(m2)

	// 利用给定数据渲染模板，并将结果写入w
	tmpl.Execute(w, m1)
}
func main() {
	http.HandleFunc("/", sayHello)
	err := http.ListenAndServe(":9090", nil)
	if err != nil {
		fmt.Println("HTTP server failed,err:", err)
		return
	}
}
