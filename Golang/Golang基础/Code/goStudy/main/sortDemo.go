package main

import (
	"fmt"
	"sort"
)

func main() {

	// 编写冒泡排序
	var numSlice = []int{9,8,4,5,1,7}
	for i := 0; i < len(numSlice); i++ {
		flag := false
		for j := 0; j < len(numSlice) - i - 1; j++ {
			if numSlice[j] > numSlice[j+1] {
				var temp = numSlice[j+1]
				numSlice[j+1] = numSlice[j]
				numSlice[j] = temp
				flag = true
			}
		}
		if !flag {
			break
		}
	}
	fmt.Println(numSlice)

	// 编写选择排序
	var numSlice2 = []int{9,8,4,5,1,7}
	for i := 0; i < len(numSlice2); i++ {
		for j := i + 1; j < len(numSlice2); j++ {
			if numSlice2[i] > numSlice2[j] {
				var temp = numSlice2[i]
				numSlice2[i] = numSlice2[j]
				numSlice2[j] = temp
			}
		}
	}
	fmt.Println(numSlice2)

	// 调用sort方法
	var numSlice3 = []int{9,8,4,5,1,7}
	sort.Ints(numSlice3)
	fmt.Println(numSlice3)

	// 逆序排列
	var numSlice4 = []int{9,8,4,5,1,7}
	sort.Sort(sort.Reverse(sort.IntSlice(numSlice4)))
	fmt.Println(numSlice4)
}
