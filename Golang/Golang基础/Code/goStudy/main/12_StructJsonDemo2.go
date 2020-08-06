package main

import (
	"encoding/json"
	"fmt"
)

// 结构体标签

// 定义一个Student体，使用结构体标签
type Student2 struct {
	Id string `json:"id"` // 通过指定tag实现json序列化该字段的key
	Gender string `json:"gender"`
	Name string `json:"name"`
	Sno string `json:"sno"`
}
func main() {
	var s1 = Student2{
		Id: "12",
		Gender: "男",
		Name: "李四",
		Sno: "s001",
	}
	// 结构体转换成Json
	jsonByte, _ := json.Marshal(s1)
	jsonStr := string(jsonByte)
	fmt.Println(jsonStr)

	// Json字符串转换成结构体
	var str = `{"Id":"12","Gender":"男","Name":"李四","Sno":"s001"}`
	var s2 = Student2{}
	// 第一个是需要传入byte类型的数据，第二参数需要传入转换的地址
	err := json.Unmarshal([]byte(str), &s2)
	if err != nil {
		fmt.Printf("转换失败 \n")
	} else {
		fmt.Printf("%#v \n", s2)
	}
}
