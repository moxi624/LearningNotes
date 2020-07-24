package main

import (
	"fmt"
	"strings"
)

func main() {
	// 创建方式1
	var userInfo = make(map[string]string)
	userInfo["userName"] = "zhangsan"
	userInfo["age"] = "20"
	userInfo["sex"] = "男"
	fmt.Println(userInfo)
	fmt.Println(userInfo["userName"])

	// 创建方式2，map也支持声明的时候填充元素
	var userInfo2 = map[string]string{
		"username": "张三",
		"age":      "21",
		"sex":      "女",
	}
	fmt.Println(userInfo2)

	// 遍历map
	for key, value := range userInfo2 {
		fmt.Println("key:", key, " value:", value)
	}

	// 判断是否存在,如果存在  ok = true，否则 ok = false
	value, ok := userInfo2["username2"]
	fmt.Println(value, ok)

	// 删除map数据里面的key，以及对应的值
	delete(userInfo2, "sex")
	fmt.Println(userInfo2)

	// 切片在中存放map
	var userInfoList = make([]map[string]string, 3, 3)
	var user = map[string]string{
		"userName": "张安",
		"age": "15",
	}
	var user2 = map[string]string{
		"userName": "张2",
		"age": "15",
	}
	var user3 = map[string]string{
		"userName": "张3",
		"age": "15",
	}
	userInfoList[0] = user
	userInfoList[1] = user2
	userInfoList[2] = user3
	fmt.Println(userInfoList)

	for _, item := range userInfoList {
		fmt.Println(item)
	}

	// 将map类型的值
	var userinfo = make(map[string][]string)
	userinfo["hobby"] = []string {"吃饭", "睡觉", "敲代码"}
	fmt.Println(userinfo)

	// 写一个程序，统计一个字符串中每个单词出现的次数。比如 "how do you do"
	var str = "how do you do"
	array := strings.Split(str, " ")
	fmt.Println(array)
	countMap := make(map[string]int)
	for _, item := range array {
		countMap[item]++
	}
	fmt.Println(countMap)


}
