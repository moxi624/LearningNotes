package main

import "fmt"

func main() {

	// 创建管道
	ch := make(chan int, 10)
	// 循环写入值
	for i := 0; i < 10; i++ {
		ch <- i
	}
	// 关闭管道
	close(ch)

	// for range循环遍历管道的值(管道没有key)
	for value := range ch {
		fmt.Println(value)
	}
	// 通过上述的操作，能够打印值，但是出出现一个deadlock的死锁错误，也就说我们需要关闭管道

	for i := 0; i < 10; i++ {
		fmt.Println(<- ch)
	}

}
