package main

import (
	"fmt"
	"sync"
	"time"
)

// 为什么需要Go context，下面的方式不是非常的优雅，我们有没有什么更好的可以优雅的退出go routine

var wg sync.WaitGroup
var notify bool
func f()  {
	defer wg.Done();
	for {
		fmt.Println("测试")
		// 睡眠500毫秒
		time.Sleep(500 * time.Millisecond)
		if notify {
			break;
		}
	}
}

func main() {
	wg.Add(1);
	go f()
	time.Sleep(5 * time.Second)
	notify = true
	// 等待wg执行Done方法
	wg.Wait()
	fmt.Println("main结束")
}
