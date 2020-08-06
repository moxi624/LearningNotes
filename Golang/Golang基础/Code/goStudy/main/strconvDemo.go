package main

import (
	"fmt"
	"strconv"
)

func main() {
	// int类型转换str类型
	var num1 int64 = 20
	s1 := strconv.FormatInt(num1, 10)
	fmt.Printf("转换：%v - %T", s1, s1)

	// float类型转换成string类型
	var num2 float64 = 3.1415926

	/*
		参数1：要转换的值
		参数2：格式化类型 'f'表示float，'b'表示二进制，‘e’表示 十进制
		参数3：表示保留的小数点，-1表示不对小数点格式化
		参数4：格式化的类型，传入64位 或者 32位
	 */
	s2 := strconv.FormatFloat(num2, 'f', -1, 64)
	fmt.Printf("转换：%v-%T", s2, s2)

	fmt.Println("字符串转换")
	str := "10"
	// 第一个参数：需要转换的数，第二个参数：进制， 参数三：32位或64位
	strconv.ParseInt(str, 10, 64)

	// 转换成float类型
	str2 := "3.141592654"
	strconv.ParseFloat(str2, 10)




}