# 单元测试

## 内容

- context
- 单元测试（给函数做单元测试）
- pprof调试工具（go语言内置工具）
  - 可以看到代码的cpu和运行时的一些信息
  - 能看到一些图表信息，如内存占用、cpu占用等

## 内容回顾

### 锁

sync.Mutex，底层是一个结构体，是值类型。给参数传递参数的时候，要传指针

两个方法

```go
var lock sync.Mutex
lock.lock() // 加锁
lock.unlock() //解锁
```

为什么要上锁？？

防止多个goroutine同一时刻操作同一个资源。

### 读写互斥锁

应用场景：适用于读多写少的场景下，也就是支持并发读，单个写

特点

- 读的goroutine来了获取的是读锁，后续的goroutine能读不能写
- 写的goroutine来了获取的是写锁，后续的goroutine不管是读还是写都要等待获取获取锁

使用

```go
var rwLock sync.RWMutex
rwLock.RLock() // 获取读锁
rwLock.Runlock() // 释放锁

rwLock.Lock() // 获取写锁
rwLock.unLock() // 释放写锁
```



### 等待组

`sync.waitgroup`，用来等goroutine执行完在继续