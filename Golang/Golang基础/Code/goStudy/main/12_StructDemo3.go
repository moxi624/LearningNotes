package main

import "fmt"

/**
	定义一个人结构体
 */
type Person struct {
	name string
	age int
	hobby []string
	mapValue map[string]string
}

func main() {
	// 结构体的匿名字段
	var person = Person{}
	person.name = "张三"
	person.age = 10

	// 给切片申请内存空间
	person.hobby = make([]string, 4, 4)
	person.hobby[0] = "睡觉"
	person.hobby[1] = "吃饭"
	person.hobby[2] = "打豆豆"

	// 给map申请存储空间
	person.mapValue = make(map[string]string)
	person.mapValue["address"] = "北京"
	person.mapValue["phone"] = "123456789"

	// 加入#打印完整信息
	fmt.Printf("%#v", person)
}
