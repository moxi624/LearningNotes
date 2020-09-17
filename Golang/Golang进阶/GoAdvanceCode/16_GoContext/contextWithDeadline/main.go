package main

import (
	"context"
	"fmt"
	"time"
)

func main()  {
	// 设置 50毫秒返回
	d := time.Now().Add(50 * time.Millisecond)
	ctx, cancel := context.WithDeadline(context.Background(), d)

	// 尽管ctx会过期，但在任何情况下，调用它的cancel函数都是很好的实践。
	// 如果不这样做，可能会使上下文及其父类存活的时间超过必要的时间
	defer cancel()

	select {
	case <- time.After(1 * time.Second):
		fmt.Println("周琳")
	case <- ctx.Done():
		fmt.Println(ctx.Err())
	}
}
