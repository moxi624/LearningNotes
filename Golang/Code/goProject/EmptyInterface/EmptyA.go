package EmptyInterface

import "fmt"

// 空接口表示没有任何约束，任意的类型都可以实现空接口
type EmptyA interface {

}

func main() {
	var a EmptyA
	var str = "你好golang"
	// 让字符串实现A接口
	a = str
	fmt.Println(a)
}