package main

import (
	"fmt"
	"math"
	"unsafe"
)

func main() {
	var num int = 10
	fmt.Printf("num = %v num 是 %T", num, num)
	fmt.Println()
	var num2 = 12
	fmt.Println(unsafe.Sizeof(num2))

	var a1 int16 = 10
	var a2 int32 = 12
	var a3 = int32(a1) + a2
	fmt.Println(a3)

	var n1 int16 = 130
	fmt.Println(int8(n1))

	fmt.Println("不同类型的输出")
	var number = 17
	// 原样输出
	fmt.Printf("%v\n", number)
	// 十进制输出
	fmt.Printf("%d\n", number)
	// 以八进制输出
	fmt.Printf("%o\n", number)
	// 以二进制输出
	fmt.Printf("%b\n", number)
	// 以十六进制输出
	fmt.Printf("%x\n", number)

	fmt.Println("浮点类型")
	var pi = math.Pi
	// 打印浮点类型，默认小数点6位
	fmt.Printf("%f\n", pi)
	// 打印浮点类型，打印小数点后2位
	fmt.Printf("%.2f\n", pi)

}
