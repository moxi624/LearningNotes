package main

import (
	"fmt"
	"sync"
)

// 想intChan中放入 1~ 120000个数
func putNum(intChan chan int)  {
	for i := 3; i < 120000; i++ {
		intChan <- i
	}
	wg.Done()
	close(intChan)
}

// cong intChan取出数据，并判断是否为素数，如果是的话，就把得到的素数放到primeChan中
func primeNum(intChan chan int, primeChan chan int, exitChan chan bool)  {
	for value := range intChan {
		var flag = true
		for i := 2; i < value; i++ {
			if  value % i == 0 {
				flag = false
				break
			}
		}
		if flag {
			// 是素数
			primeChan <- value
			break
		}
	}

	// 这里需要关闭 primeChan，因为后面需要遍历输出 primeChan
	exitChan <- true

	wg.Done()
}

// 打印素数
func printPrime(primeChan chan int)  {
	for value := range primeChan {
		fmt.Println(value)
	}
	wg.Done()
}


var wg sync.WaitGroup
func main() {
	// 写入数字
	intChan := make(chan int, 1000)

	// 存放素数
	primeChan := make(chan int, 1000)

	// 存放 primeChan退出状态
	exitChan := make(chan bool, 16)

	// 开启写值的协程
	wg.Add(1)
	go putNum(intChan)

	// 开启计算素数的协程
	for i := 0; i < 16; i++ {
		wg.Add(1)
		go primeNum(intChan, primeChan, exitChan)
	}

	// 开启打印的协程
	wg.Add(1)
	go printPrime(primeChan)

	// 匿名自运行函数
	wg.Add(1)
	go func() {
		for i := 0; i < 16; i++ {
			// 如果exitChan 没有完成16次遍历，将会等待
			<- exitChan
		}
		// 关闭primeChan
		close(primeChan)
		wg.Done()
	}()

	wg.Wait()
	fmt.Println("主线程执行完毕")
	
}
