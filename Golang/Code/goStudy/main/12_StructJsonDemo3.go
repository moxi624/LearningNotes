package main

import (
	"encoding/json"
	"fmt"
)

// 嵌套结构体 到 Json的互相转换

// 定义一个Student结构体
type Student3 struct {
	Id int
	Gender string
	Name string
}

// 定义一个班级结构体
type Class struct {
	Title string
	Students []Student3
}

func main() {
	var class = Class{
		Title: "1班",
		Students: make([]Student3, 0),
	}
	for i := 0; i < 10; i++ {
		s := Student3{
			Id: i + 1,
			Gender: "男",
			Name: fmt.Sprintf("stu_%v", i + 1),
		}
		class.Students = append(class.Students, s)
	}
	fmt.Printf("%#v \n", class)

	// 转换成Json字符串
	strByte, err := json.Marshal(class)
	if err != nil {
		fmt.Println("打印失败")
	} else {
		fmt.Println(string(strByte))
	}
}
