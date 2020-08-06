package main

import "fmt"

func main() {
	var a = 10
	fmt.Printf("a的值: %v，a的类型: %T，a的地址: %p \n", a, a, &a)

	var b = 10
	// p的值就是b的地址
	var p = &b
	fmt.Println(b, " ", p)

	// 指针取值
	var c = 20
	var d = &c
	fmt.Println(*d)
	// c对应地址的值，改成30
	*d = 30
	// c已经变成30了
	fmt.Println(c)


}
