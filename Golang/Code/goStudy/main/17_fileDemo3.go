package main

import (
	"bufio"
	"io/ioutil"
	"os"
)

func main() {
	// 打开文件
	file, _ := os.OpenFile("./main/test.txt", os.O_CREATE | os.O_RDWR | os.O_APPEND, 777)
	defer file.Close()
	str := "啦啦啦 \r\n"
	file.WriteString(str)

	// 通过bufio写入
	writer := bufio.NewWriter(file)
	// 先将数据写入缓存
	writer.WriteString("你好，我是通过writer写入的 \r\n")
	// 将缓存中的内容写入文件
	writer.Flush()

	// 第三种方式，通过ioutil
	str2 := "hello"
	ioutil.WriteFile("./main/test.txt", []byte(str2), 777)

}
