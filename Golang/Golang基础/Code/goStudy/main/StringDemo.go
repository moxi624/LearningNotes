package main

import (
	"fmt"
	"strings"
)

func main() {
	var str = "hello world"
	var length = len(str) // 长度为11
	fmt.Println("字符串的长度为：" , length)

	// 中文占三个字符，所以长度为6
	var str2 = "你好"
	fmt.Println("文字的长度为：", len(str2))

	fmt.Println("拼接字符串~~~")
	str3 := "你好"
	str4 := "golang"
	str5 := fmt.Sprintf("%v-%v", str3, str4)
	fmt.Println(str5)

	fmt.Println("切割字符串")
	str6 := "hello world"
	arr := strings.Split(str6, " ")
	fmt.Println(arr)

	fmt.Println("拼接字符串")
	newStr := strings.Join(arr, "*")
	fmt.Println(newStr)

	fmt.Println("判断字符串是否包含某个字符串")
	var str7 = "abcdefg"
	var str8 = "abc"
	var isContains bool = strings.Contains(str7, str8)
	fmt.Println(isContains)

	fmt.Println("前缀/后缀判断")
	isPrefix := strings.HasPrefix(str7, "abc")
	fmt.Println("是否包含指定前缀判断", isPrefix)

	isSuffix := strings.HasSuffix(str8, "bcd")
	fmt.Println("是否包含指定后缀", isSuffix)

	fmt.Println("测试Index的位置")
	index := strings.Index(str7, "abc")
	fmt.Println("得到的位置:", index)

}
