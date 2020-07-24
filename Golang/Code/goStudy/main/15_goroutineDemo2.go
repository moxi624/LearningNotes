package main

import (
	"fmt"
	"runtime"
	"sync"
)

func test(num int)  {
	for i := 0; i < 10; i++ {
		fmt.Printf("协程（%v）打印的第%v条数据 \n", num, i)
	}
	// 协程计数器减1
	vg.Done()
}

var vg sync.WaitGroup

func main() {
	// 获取cpu个数
	npmCpu := runtime.NumCPU()
	fmt.Println("cup的个数:", npmCpu)
	// 设置允许使用的CPU数量
	runtime.GOMAXPROCS(runtime.NumCPU() - 1)

	for i := 0; i < 10; i++ {
		go test(i)
		vg.Add(1)
	}
	vg.Wait()
	fmt.Println("主线程退出")
}
