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
func reflectValue(x interface{}) {
	b,_ := x.(int)
	var num = 10 + b
	fmt.Println(num)
}
func reflectValue2(x interface{}) {
	// 通过反射来获取变量的原始值
	v := reflect.ValueOf(x)
	fmt.Println(v)
	// 获取到V的int类型
	var n = v.Int() + 12
	fmt.Println(n)

	kind := v.Kind()
	switch kind {
	case reflect.Int:
		fmt.Println("我是int类型")
	case reflect.Float64:
		fmt.Println("我是float64类型")
	default:
		fmt.Println("我是其它类型")
	}

}
func main() {
	reflectFun(10)
	reflectFun(10.01)
	reflectFun("abc")
	reflectFun(true)

	reflectValue2(10)
}
