# Go并发编程

## 参考

https://www.liwenzhou.com/posts/Go/14_concurrence/

## 并行与并发

并发：同一时间段内执行多个任务（你在用微信和两个女朋友聊天）

并行：同一时刻执行多个任务（你和你朋友都在用微信和女朋友聊天）

Go语言的并发通过goroutine 实现。goroutine类似于线程，属于用户态的线程，我们可以根据需要创建成千上万个goroutine 并发工作。goroutine 是由Go语言的运行时（runtime）调度完成，而线程是由操作系统调度完成。
Go语言还提供channel 在多个goroutine间进行通信。goroutine和channel 是Go语言秉承CSP（Communicating Sequential Process）并发模式的重要实现基础。

> 用户态：表示程序执行用户自己写的程序时
>
> 内核态：表示程序执行操作系统层面的程序时
>
> 我们学习go的并发，就是学习goroutine 和 channel

## Goroutine

在java/c++中我们要实现并发编程的时候，我们通常需要自己维护一个线程池，并且需要自己去包装一个又一个的任务，同时需要自己去调度线程执行任务并维护上下文切换，这一切通常会耗费程序员大量的心智。那么能不能有一种机制，程序员只需要定义很多个任务，让系统去帮助我们把这些任务分配到CPU上实现并发执行呢？

Go语言中的goroutine就是一种机制，goroutine的概念类似于线程，但goroutine是由Go的运行时（runtime）调度和管理的。Go程序会智能地将goroutine中的任务合理分配给每个CPU，Go语言之所以被称为现代化的编程语言，就是因为它在语言层面已经内置了调度和上下文切换的机制。

在Go语言编程中你不需要去自己写进程、线程、协程，你的技能包里只有一个技能- goroutine，当你需要让某个任务并发执行的时候，你只需要把这个任务包装成一个函数、开启一个goroutine去执行这个函数就可以了，就是这么简单粗暴。

### 使用Goroutine

Go语言中goroutine非常简单，只需要在调用函数的时候，在前面加上go关键字，就可以为一个函数创建一个goroutine

一个goroutine必定对应一个函数，可以创建多个goroutine去执行相同的函数

### 启动goroutine

启动goroutine的方式非常简单，只需要在调用的函数（普通函数和匿名函数）前面加上一个`go`关键字。

举个例子如下：

```go
func hello() {
	fmt.Println("Hello Goroutine!")
}
func main() {
	hello()
	fmt.Println("main goroutine done!")
}
```

当main()函数返回的时候该`goroutine`就结束了，所有在`main()`函数中启动的`goroutine`会一同结束，`main`函数所在的`goroutine`就像是权利的游戏中的夜王，其他的`goroutine`都是异鬼，夜王一死它转化的那些异鬼也就全部GG了

所以我们要想办法让main函数等一等hello函数，最简单粗暴的方式就是`time.Sleep`了

```go
func main() {
	go hello() // 启动另外一个goroutine去执行hello函数
	fmt.Println("main goroutine done!")
	time.Sleep(time.Second)
}
```

### 启动多个goroutine

在Go语言中实现并发就是这样简单，我们还可以启动多个`goroutine`。让我们再来一个例子:（这里使用了`sync.WaitGroup`来实现goroutine的同步）

```go
var wg sync.WaitGroup

func hello(i int) {
	defer wg.Done() // goroutine结束就登记-1
	fmt.Println("Hello Goroutine!", i)
}
func main() {

	for i := 0; i < 10; i++ {
		wg.Add(1) // 启动一个goroutine就登记+1
		go hello(i)
	}
	wg.Wait() // 等待所有登记的goroutine都结束
}
```

### goroutine什么时候结束？

 goroutine对应的函数结束了，goroutine就结束了吗，也就是说当我们的main函数执行结束了，那么main函数对应的goroutine也结束了。

## goroutine与线程

### 可增长的栈

OS线程（操作系统线程）一般都有固定的栈内存（通常为2MB）,一个`goroutine`的栈在其生命周期开始时只有很小的栈（典型情况下2KB），`goroutine`的栈不是固定的，他可以按需增大和缩小，`goroutine`的栈大小限制可以达到1GB，虽然极少会用到这么大。所以在Go语言中一次创建十万左右的`goroutine`也是可以的

### Goroutine调度

`GPM`是Go语言运行时（runtime）层面的实现，是go语言自己实现的一套调度系统。区别于操作系统调度OS线程。

- `G`很好理解，就是个goroutine的，里面除了存放本goroutine信息外 还有与所在P的绑定等信息。
- `P`管理着一组goroutine队列，P里面会存储当前goroutine运行的上下文环境（函数指针，堆栈地址及地址边界），P会对自己管理的goroutine队列做一些调度（比如把占用CPU时间较长的goroutine暂停、运行后续的goroutine等等）当自己的队列消费完了就去全局队列里取，如果全局队列里也消费完了会去其他P的队列里抢任务。
- `M（machine）`是Go运行时（runtime）对操作系统内核线程的虚拟， M与内核线程一般是一一映射的关系， 一个groutine最终是要放到M上执行的；

单从线程调度讲，Go语言相比起其他语言的优势在于OS线程是由OS内核来调度的，`goroutine`则是由Go运行时（runtime）自己的调度器调度的，这个调度器使用一个称为m:n调度的技术（复用/调度m个goroutine到n个OS线程）。 其一大特点是goroutine的调度是在用户态下完成的， 不涉及内核态与用户态之间的频繁切换，包括内存的分配与释放，都是在用户态维护着一块大的内存池， 不直接调用系统的malloc函数（除非内存池需要改变），成本比调度OS线程低很多。 另一方面充分利用了多核的硬件资源，近似的把若干goroutine均分在物理线程上， 再加上本身goroutine的超轻量，以上种种保证了go调度方面的性能。