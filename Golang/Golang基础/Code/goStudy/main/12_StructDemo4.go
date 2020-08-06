package main

import "fmt"

// 结构体嵌套

// 用户结构体
type User struct {
	userName string
	password string
	sex string
	age int
	address Address // User结构体嵌套Address结构体
}

// 收货地址结构体
type Address struct {
	name string
	phone string
	city string
}

func main() {
	var u User
	u.userName = "moguBlog"
	u.password = "123456"
	u.sex = "男"
	u.age = 18

	var address Address
	address.name = "张三"
	address.phone = "110"
	address.city = "北京"
	u.address = address
	fmt.Printf("%#v", u)
}
