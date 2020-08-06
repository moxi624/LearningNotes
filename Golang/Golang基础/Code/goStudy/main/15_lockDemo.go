package main

import (
	"fmt"
	"sync"
	"time"
)

var count = 0
var wg sync.WaitGroup
var mutex sync.Mutex

func test()  {
	// 加锁
	mutex.Lock()
	count++
	fmt.Println("the count is : ", count)
	time.Sleep(time.Millisecond)
	wg.Done()
	// 解锁
	mutex.Unlock()
}
func main() {
	for i := 0; i < 20; i++ {
		wg.Add(1)
		go test()
	}
	time.Sleep(time.Second * 10)
}
