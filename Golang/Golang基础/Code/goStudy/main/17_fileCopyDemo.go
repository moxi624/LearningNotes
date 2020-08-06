package main

import (
	"fmt"
	"io/ioutil"
)

func main() {
	// 读取文件
	byteStr, err := ioutil.ReadFile("./main/test.txt")
	if err != nil {
		fmt.Println("读取文件出错")
		return
	}
	// 写入指定的文件
	ioutil.WriteFile("./main/test2.txt", byteStr, 777)
}
