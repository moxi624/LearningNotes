package main

import (
	"fmt"
	"time"
)

func hello(i int)  {
	fmt.Println("hello ", i)
}

// 程序启动之后，会创建一个主goroutine去执行
func main() {
	// 开启一个单独的goroutine去执行hello函数（函数）
	for i := 0; i < 10; i++ {
		go func(i int) {
			// 闭包问题，需要在外面传递一个进去
			fmt.Println(i)
		}(i)
	}

	fmt.Println("main")
	time.Sleep(time.Second)
}