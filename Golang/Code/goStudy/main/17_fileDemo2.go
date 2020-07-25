package main

import (
	"bufio"
	"fmt"
	"io"
	"io/ioutil"
	"os"
)

func main() {
	// 读取文件 方法2
	file, err := os.Open("./main/test.txt")
	// 关闭文件流
	defer file.Close();
	if err != nil {
		fmt.Println("打开文件出错")
	}
	// 通过创建bufio来读取
	reader := bufio.NewReader(file)
	var fileStr string
	var count int = 0
	for {
		// 相当于读取一行
		str, err := reader.ReadString('\n')
		if err == io.EOF {
			// 读取完成的时候，也会有内容
			fileStr += str
			fmt.Println("读取结束", count)
			break
		}
		if err != nil {
			fmt.Println(err)
			break
		}
		count ++
		fileStr += str
	}
	fmt.Println(fileStr)


	// 通过IOUtil读取
	byteStr, _ := ioutil.ReadFile("./main/test.txt")
	fmt.Println(string(byteStr))
}
