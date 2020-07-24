package main

import (
	"fmt"
	"reflect"
)

func reflectFun(x interface{})  {
	v := reflect.TypeOf(x)
	fmt.Println(v)
	fmt.Println("类型名称", v.Name())
	fmt.Println("类型种类", v.Kind())
}
func main() {
	reflectFun(10)
	reflectFun(10.01)
	reflectFun("abc")
	reflectFun(true)
}
