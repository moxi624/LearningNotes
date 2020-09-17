package main

import (
	"fmt"
	"io/ioutil"
	"net/http"
)

func f1(w http.ResponseWriter, r *http.Request) {
	index, err := ioutil.ReadFile("./index.html")
	if err != nil {
		w.Write([]byte(fmt.Sprintf("%v", err)))
	}
	w.Write([]byte(index))
}

func f2(w http.ResponseWriter, r *http.Request) {
	fmt.Println(r.URL)
	fmt.Println(r.URL.Query()) // 识别URL中的参数
	queryParams := r.URL.Query()
	name := queryParams.Get("name")
	age := queryParams.Get("age")
	fmt.Println("传递来的name:", name)
	fmt.Println("传递来的age:", age)
	fmt.Println(r.Method)
	fmt.Println(ioutil.ReadAll(r.Body))
}

func main() {
	http.HandleFunc("/index", f1)
	http.HandleFunc("/query", f2)
	http.HandleFunc("/about", f1)
	http.ListenAndServe("127.0.0.1:9090", nil)
}
