package main

import "fmt"

func fn4(x int) {
	x = 10
}
func fn5(x *int) {
	*x = 20
}
func main() {
	x := 5
	fn4(x)
	fmt.Println(x)
	fn5(&x)
	fmt.Println(x)

	// 引用数据类型map、slice等，必须使用make分配空间，才能够使用
	var userInfo = make(map[string]string)
	userInfo["userName"] = "zhangsan"
	fmt.Println(userInfo)

	var array = make([]int, 4, 4)
	array[0] = 1
	fmt.Println(array)

	// 指针变量初始化
	//var a *int
	//*a = 100
	//fmt.Println(a)

	// 使用new关键字创建指针
	aPoint := new(int)
	bPoint := new(bool)
	fmt.Printf("%T \n", aPoint)
	fmt.Printf("%T \n", bPoint)
	fmt.Println(*aPoint)
	fmt.Println(*bPoint)

}
