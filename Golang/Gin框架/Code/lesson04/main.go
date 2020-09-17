package main

import (
	"fmt"
	"html/template"
	"net/http"
	"os"
)

func sayHello(w http.ResponseWriter, r *http.Request) {
	// 获取项目的绝对路径
	wd, err := os.Getwd()
	if err != nil {
		fmt.Printf("get wd failed, err:%v \n", wd)
		return
	}
	fmt.Println("wd:", wd + "\\lesson04\\index2.tmpl")
	// 解析指定文件生成模板对象
	tmpl, err := template.ParseFiles( wd + "\\lesson04\\index2.tmpl")

	if err != nil {
		fmt.Println("create templates failed, err:", err)
		return
	}
	// 利用给定数据渲染模板，并将结果写入w
	tmpl.Execute(w, "沙河小王子")
}
func main() {
	http.HandleFunc("/", sayHello)
	err := http.ListenAndServe(":9090", nil)
	if err != nil {
		fmt.Println("HTTP server failed,err:", err)
		return
	}
}
