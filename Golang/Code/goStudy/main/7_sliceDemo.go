package main

import "fmt"

func main() {
	// 声明切片，把长度去除就是切片
	var slice = []int{1,2,3}
	fmt.Println(slice)

	var slice2 [] int
	fmt.Println(slice2 == nil)

	for i := 0; i < len(slice); i++ {
		fmt.Print(slice[i], " ")
	}
	fmt.Println()
	// 基于数组定义切片
	a := [5]int {55,56,57,58,59}
	// 获取数组所有值，返回的是一个切片
	b := a[:]
	// 从数组获取指定的切片
	c := a[1:4]
	// 获取 下标3之前的数据（不包括3）
	d := a[:3]
	// 获取下标3以后的数据（包括3）
	e := a[3:]

	fmt.Println(a)
	fmt.Println(b)
	fmt.Println(c)
	fmt.Println(d)
	fmt.Println(e)

	// 长度和容量
	s := []int {2,3,5,7,11,13}
	fmt.Printf("长度%d 容量%d\n", len(s), cap(s))

	ss := s[2:]
	fmt.Printf("长度%d 容量%d\n", len(ss), cap(ss))

	sss := s[2:4]
	fmt.Printf("长度%d 容量%d\n", len(sss), cap(sss))

	// make()函数创建切片
	fmt.Println()
	var slices = make([]int, 4, 8)
	//[0 0 0 0]
	fmt.Println(slices)
	// 长度：4, 容量8
	fmt.Printf("长度：%d, 容量%d \n", len(slices), cap(slices))

	// 切片扩容
	slices2 := []int{1,2,3,4}
	slices2 = append(slices2, 5)
	fmt.Println(slices2)

	// 合并切片
	slices3 := []int{6,7,8}
	slices2 = append(slices2, slices3...)
	fmt.Println(slices2)

	// copy函数，我们知道切片
	var slices4 = []int{1,2,3,4}
	var slices5 = make([]int, len(slices4), len(slices4))
	copy(slices5, slices4)
	slices5[0] = 4
	fmt.Println(slices4)
	fmt.Println(slices5)

	// 删除切片中的值
	var slices6 = []int {0,1,2,3,4,5,6,7,8,9}
	// 删除下标为1的值
	slices6 = append(slices6[:1], slices6[2:]...)
	fmt.Println(slices6)

}