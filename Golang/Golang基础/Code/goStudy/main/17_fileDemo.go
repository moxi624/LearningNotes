package main

import (
	"fmt"
	"io"
	"os"
)

func main() {
	// 读取文件 方法1
	file, err := os.Open("./main/test.txt")
	// 关闭文件流
	defer file.Close();
	if err != nil {
		fmt.Println("打开文件出错")
	}
	// 读取文件里面的内容
	var tempSlice = make([]byte, 1024)
	var strSlice []byte
	for {
		n, err := file.Read(tempSlice)
		if err == io.EOF {
			fmt.Printf("读取完毕")
			break
		}
		fmt.Printf("读取到了%v 个字节 \n", n)
		strSlice := append(strSlice, tempSlice...)
		fmt.Println(string(strSlice))
	}
}
