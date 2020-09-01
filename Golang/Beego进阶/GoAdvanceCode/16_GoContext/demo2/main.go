package main

import (
	"fmt"
	"sync"
	"time"
)

// 利用通道来通知退出

var wg sync.WaitGroup
var exitChan chan bool = make(chan bool, 1)
func f()  {
	defer wg.Done();
	notify := false
	for {
		if notify {
			break
		}
		fmt.Println("测试")
		// 睡眠500毫秒
		time.Sleep(500 * time.Millisecond)
		// 多路复用
		select {
		case <- exitChan:
			notify = true
			break
		default:
		}
	}
}

func main() {
	wg.Add(1);
	go f()
	time.Sleep(5 * time.Second)
	exitChan <- true
	// 等待wg执行Done方法
	wg.Wait()
	fmt.Println("main结束")
}
