/**
 * @Description 修改模板引擎的默认标识符
 * @author 陌溪
 * @date 2020年9月16日21:41:43
 **/
package main

import (
	"fmt"
	"html/template"
	"net/http"
	"os"
)

/**
 * @Description f1函数
 * @Param 
 * @return
 **/
func f1(w http.ResponseWriter, r *http.Request) {
	// 定义模板
	// 解析模板

	// 获取项目的绝对路径
	wd, err := os.Getwd()
	if err != nil {
		fmt.Printf("get wd failed, err:%v \n", wd)
		return
	}

	// 创建一个名字为f的模板对象。注意，这个名字一定要和模板的名字对应上
	tmpl := template.New("hello.tmpl")

	// 修改默认的标识符
	tmpl = tmpl.Delims("{[", "]}")

	// 解析模板
	_, err = tmpl.ParseFiles( wd + "\\lesson08\\hello.tmpl")

	if err != nil {
		fmt.Printf("parse templates failed, err:%v \n", err)
		return
	}

	// 采用一个map
	m1 := map[string]interface{}{
		"Name": "小王子",
		"Age": 18,
		"Gender": "男",
	}

	// 渲染模板
	tmpl.Execute(w, m1)
}

func xss(w http.ResponseWriter, r *http.Request) {
	// 定义模板

	// 解析模板
	wd, err := os.Getwd()
	if err != nil {
		fmt.Printf("get wd failed, err:%v \n", wd)
		return
	}
	// 创建一个名字为f的模板对象。注意，这个名字一定要和模板的名字对应上
	tmpl := template.New("xss.tmpl")

	// 定义一个安全的函数，让模板对 传递过去的数据不进行转义，直接通过html格式显示
	tmpl = tmpl.Funcs(template.FuncMap{
		"safe": func(str string)template.HTML {
			return template.HTML(str)
		},
	})

	// 解析模板
	_, err = tmpl.ParseFiles( wd + "\\lesson08\\xss.tmpl")
	if err != nil {
		fmt.Printf("parse templates failed, err:%v \n", err)
		return
	}
	// 渲染模板
	str := "<script>alert(1);</script>"
	tmpl.Execute(w, str)
}
func main() {
	http.HandleFunc("/hello", f1)
	http.HandleFunc("/xss", xss)
	err := http.ListenAndServe(":9090", nil)
	if err != nil {
		fmt.Println("HTTP server failed,err:", err)
		return
	}
}
