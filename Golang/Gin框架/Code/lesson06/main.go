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

	// 定义一个自定义函数
	// 要么只有一个返回值，要么有两个返回值，第二个返回值必须是error类型
	kua := func(name string)(string, error) {
		return name + "年轻又帅气!", nil
	}

	// 创建一个名字为f的模板对象。注意，这个名字一定要和模板的名字对应上
	tmpl := template.New("index2.tmpl")

	// 告诉模板引擎，我现在多了一个自定义的函数kua
	tmpl.Funcs(template.FuncMap{
		"kua": kua,
	})

	// 解析模板
	_, err = tmpl.ParseFiles( wd + "\\lesson06\\index2.tmpl")

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
func main() {
	http.HandleFunc("/hello", f1)
	err := http.ListenAndServe(":9090", nil)
	if err != nil {
		fmt.Println("HTTP server failed,err:", err)
		return
	}
}
