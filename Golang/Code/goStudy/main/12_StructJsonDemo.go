package main

import (
	"encoding/json"
	"fmt"
)

// 结构体和Json互相转换

// 定义一个学生结构体，注意结构体的首字母必须大写，代表公有，否则将无法转换
type Student struct {
	ID string
	Gender string
	Name string
	Sno string
}
func main() {
	var s1 = Student{
		ID: "12",
		Gender: "男",
		Name: "李四",
		Sno: "s001",
	}
	// 结构体转换成Json
	jsonByte, _ := json.Marshal(s1)
	jsonStr := string(jsonByte)
	fmt.Println(jsonStr)

	// Json字符串转换成结构体
	var str = `{"ID":"12","Gender":"男","Name":"李四","Sno":"s001"}`
	var s2 = Student{}
	// 第一个是需要传入byte类型的数据，第二参数需要传入转换的地址
	err := json.Unmarshal([]byte(str), &s2)
	if err != nil {
		fmt.Printf("转换失败 \n")
	} else {
		fmt.Printf("%#v \n", s2)
	}
}
