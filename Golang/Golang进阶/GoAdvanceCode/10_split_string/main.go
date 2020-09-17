package main

import (
	"fmt"
	"strings"
)

// 切割字符串
func Split(str string, seq string)[]string  {

	var ret [] string
	index := strings.Index(str, seq)
	for index > 0 {
		ret = append(ret, str[:index])
		str = str[index+1:]
		index = strings.Index(str, seq)
	}
	ret = append(ret, str)
	return ret
}
func main() {
	fmt.Println(Split("abcabcdba", "b"))
}
