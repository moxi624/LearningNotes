package main

import "fmt"

func main() {
	//// 声明一个传递整型的管道
	//var ch1 chan int
	//// 声明一个传递布尔类型的管道
	//var ch2 chan bool
	//// 声明一个传递int切片的管道
	//var ch3 chan []int
	//
	//// 创建一个能存储10个int类型的数据管道
	//ch1 = make(chan int, 10)
	//// 创建一个能存储4个bool类型的数据管道
	//ch2 = make(chan bool, 4)
	//// 创建一个能存储3个[]int切片类型的管道
	//ch3 = make(chan []int, 3)


	// 创建管道
	ch := make(chan int, 3)

	// 给管道里面存储数据
	ch <- 10
	ch <- 21
	ch <- 32

	// 获取管道里面的内容
	a := <- ch
	fmt.Println("打印出管道的值：", a)
	fmt.Println("打印出管道的值：", <- ch)
	fmt.Println("打印出管道的值：", <- ch)

	// 管道的值、容量、长度
	fmt.Printf("地址：%v 容量：%v 长度：%v \n", ch, cap(ch), len(ch))

	// 管道的类型
	fmt.Printf("%T \n", ch)

	// 管道阻塞（当没有数据的时候取，会出现阻塞，同时当管道满了，继续存也会）
	<- ch  // 没有数据取，出现阻塞
	ch <- 10
	ch <- 10
	ch <- 10
	ch <- 10 // 管道满了，继续存，也出现阻塞

}
