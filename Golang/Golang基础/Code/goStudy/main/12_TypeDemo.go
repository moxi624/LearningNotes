package main

import "fmt"

type myInt int
func fun(x int, y int)int {
	return x + y
}
func (m myInt) PrintInfo()  {
	fmt.Println("我是自定义类型里面的自定义方法")
}
func main() {
	var a myInt = 10
	fmt.Printf("%v %T \n", a, a)
	a.PrintInfo()
}
