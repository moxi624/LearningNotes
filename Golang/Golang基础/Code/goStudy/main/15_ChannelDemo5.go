package main

import "fmt"

func sayHello()  {
	for i := 0; i < 10; i++ {
		fmt.Println("hello")
	}
}
func errTest()  {
	// 捕获异常
	defer func() {
		if err := recover(); err != nil {
			fmt.Println("errTest发生错误")
		}
	}()
	var myMap map[int]string
	myMap[0] = "10"
}
func main() {

	//// 定义一种可读可写的管道
	//var ch = make(chan int, 2)
	//ch <- 10
	//<- ch
	//
	//// 管道声明为只写管道，只能够写入，不能读
	//var ch2 = make(chan<- int, 2)
	//ch2 <- 10
	//
	//// 声明一个只读管道
	//var ch3 = make(<-chan int, 2)
	//<- ch3

	intChan := make(chan int, 10)
	intChan <- 10
	intChan <- 12
	intChan <- 13
	stringChan := make(chan int, 10)
	stringChan <- 20
	stringChan <- 23
	stringChan <- 24

	// 每次循环的时候，会随机中一个chan中读取，其中for是死循环
	for {
		select {
		case v:= <- intChan:
			fmt.Println("从initChan中读取数据：", v)
		case v:= <- stringChan:
			fmt.Println("从stringChan中读取数据：", v)
		default:
			fmt.Println("所有的数据获取完毕")
			return
		}
	}


	
}
