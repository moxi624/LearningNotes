package main

import (
	"fmt"
	"html/template"
	"net/http"
	"os"
)

/**
 * @Description 首页
 * @Param 
 * @return
 **/
func index(w http.ResponseWriter, r *http.Request) {
	// 定义模板
	// 解析模板
	// 获取项目的绝对路径
	wd, err := os.Getwd()
	if err != nil {
		fmt.Printf("get wd failed, err:%v \n", wd)
		return
	}

	// 创建一个名字为f的模板对象。注意，这个名字一定要和模板的名字对应上
	tmpl := template.New("index.tmpl")

	// 解析模板
	_, err = tmpl.ParseFiles( wd + "\\lesson07\\index.tmpl")

	if err != nil {
		fmt.Printf("parse templates failed, err:%v \n", err)
		return
	}

	msg := "小公主"

	// 渲染模板
	tmpl.Execute(w, msg)
}

/**
 * @Description 主页
 * @Param
 * @return
 **/
func home(w http.ResponseWriter, r *http.Request) {
	// 定义模板
	// 解析模板
	// 获取项目的绝对路径
	wd, err := os.Getwd()
	if err != nil {
		fmt.Printf("get wd failed, err:%v \n", wd)
		return
	}

	// 创建一个名字为f的模板对象。注意，这个名字一定要和模板的名字对应上
	tmpl := template.New("home.tmpl")

	// 解析模板
	_, err = tmpl.ParseFiles( wd + "\\lesson07\\home.tmpl")

	if err != nil {
		fmt.Printf("parse templates failed, err:%v \n", err)
		return
	}

	msg := "小王子"

	// 渲染模板
	tmpl.Execute(w, msg)
}


/**
 * @Description 首页
 * @Param
 * @return
 **/
func index2(w http.ResponseWriter, r *http.Request) {
	// 定义模板(使用的模板继承的方式)
	// 解析模板
	// 获取项目的绝对路径
	wd, err := os.Getwd()
	if err != nil {
		fmt.Printf("get wd failed, err:%v \n", wd)
		return
	}

	// 解析模板
	t, err := template.ParseFiles( wd + "\\lesson07\\templates\\base.tmpl", wd + "\\lesson07\\templates\\index2.tmpl")

	if err != nil {
		fmt.Printf("parse templates failed, err:%v \n", err)
		return
	}

	msg := "小公主"

	// 渲染模板
	t.ExecuteTemplate(w, "index2.tmpl", msg)
}

/**
 * @Description 主页
 * @Param
 * @return
 **/
func home2(w http.ResponseWriter, r *http.Request) {
	// 定义模板(使用的模板继承的方式)
	// 解析模板
	// 获取项目的绝对路径
	wd, err := os.Getwd()
	if err != nil {
		fmt.Printf("get wd failed, err:%v \n", wd)
		return
	}

	// 解析模板
	t, err := template.ParseFiles( wd + "\\lesson07\\templates\\base.tmpl", wd + "\\lesson07\\templates\\home2.tmpl")

	if err != nil {
		fmt.Printf("parse templates failed, err:%v \n", err)
		return
	}

	msg := "小公主"

	// 渲染模板
	t.ExecuteTemplate(w, "home2.tmpl", msg)
}

func main() {
	http.HandleFunc("/index", index)
	http.HandleFunc("/home", home)
	http.HandleFunc("/index2", index2)
	http.HandleFunc("/home2", home2)
	err := http.ListenAndServe(":9090", nil)
	if err != nil {
		fmt.Println("HTTP server failed,err:", err)
		return
	}
}
