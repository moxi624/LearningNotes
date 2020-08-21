package main

import (
	"flag"
	"fmt"
	"os"
)

func main() {
	// os.Args 获取命令行参数
	fmt.Println(os.Args)
	params0 := os.Args[0]
	params1 := os.Args[1]
	fmt.Println(params0, params1)

	// 创建一个标志位参数
	name := flag.String("name", "张三", "请输入名字")
	age := flag.Int("age", 18, "请输入年龄")

	// 使用flag
	flag.Parse()

	// 根据地址获取信息
	fmt.Println(*name)
	fmt.Println(*age)
}
