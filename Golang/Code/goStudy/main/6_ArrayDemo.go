package main

import "fmt"

func main() {
	// 数组的长度是类型的一部分
	var arr1 [3]int
	var arr2 [4]string
	fmt.Printf("%T, %T \n", arr1, arr2)

	// 数组的初始化 第一种方法
	var arr3 [3]int
	arr3[0] = 1
	arr3[1] = 2
	arr3[2] = 3
	fmt.Println(arr3)

	// 第二种初始化数组的犯法
	var arr4 = [4]int {10, 20, 30, 40}
	fmt.Println(arr4)

	// 第三种数组初始化方法，自动推断数组长度
	var arr5 = [...]int{1, 2}
	fmt.Println(arr5)

	// 第四种初始化数组的方法，指定下标
	a := [...]int{1:1, 3:5}
	fmt.Println(a)

	for i := 0; i < len(a); i++ {
		fmt.Print(a[i], " ")
	}

	for _, value := range a {
		fmt.Print(value, " ")
	}

	fmt.Println()
	// 值类型 引用类型
	// 基本数据类型和数组都是值类型
	var aa = 10
	bb := aa
	aa = 20
	fmt.Println(aa, bb)

	// 数组
	var array1 = [...]int {1, 2, 3}
	array2 := array1
	array2[0] = 3
	fmt.Println(array1, array2)

	// 切片定义
	var array3 = []int{1,2,3}
	array4 := array3
	array4[0] = 3
	fmt.Println(array3, array4)

	// 二维数组
	var array5 = [...][2]int{{1,2},{2,3}}
	for i := 0; i < len(array5); i++ {
		for j := 0; j < len(array5[0]); j++ {
			fmt.Println(array5[i][j])
		}
	}

	for _, item := range array5 {
		for _, item2 := range item {
			fmt.Println(item2)
		}
	}
}
