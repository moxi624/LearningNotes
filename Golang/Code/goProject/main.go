package main

import (
	"fmt"
	"goProject/SecondInterface"
	"goProject/interfaceDemo"
)

// 定义一个方法，可以传入任意数据类型，然后根据不同类型实现不同的功能
func Print(x interface{})  {
	if _,ok := x.(string); ok {
		fmt.Println("传入参数是string类型")
	} else if _, ok := x.(int); ok {
		fmt.Println("传入参数是int类型")
	} else {
		fmt.Println("传入其它类型")
	}
}

func Print2(x interface{})  {
	switch x.(type) {
	case int:
		fmt.Println("int类型")
	case string:
		fmt.Println("string类型")
	case bool:
		fmt.Println("bool类型")
	default:
		fmt.Println("其它类型")
	}
}

func main() {
	var camera interfaceDemo.Camera = interfaceDemo.Camera{
		"佳能",
	}
	var phone interfaceDemo.Phone = interfaceDemo.Phone{
		"苹果",
	}

	var computer interfaceDemo.Computer = interfaceDemo.Computer{}
	computer.Startup(camera)
	computer.Startup(phone)
	computer.Shutdown(camera)
	computer.Shutdown(phone)

	// 定义一个值为空接口类型
	var studentInfo = make(map[string]interface{})
	studentInfo["userName"] = "张三"
	studentInfo["age"] = 15
	studentInfo["isWork"] = true
	fmt.Printf("%#v \n", studentInfo)

	// 定义一个空接口类型的切片
	var slice = make([]interface{}, 4, 4)
	slice[0] = "张三"
	slice[1] = 1
	slice[2] = true

	// 类型断言
	var a interface{}
	a = "132"
	value, isString := a.(string)
	if isString {
		fmt.Println("是String类型, 值为：", value)
	} else {
		fmt.Println("断言失败")
	}

	Print2("a")

	var dog SecondInterface.Animal = &SecondInterface.Dog{
		"小黑",
	}
	fmt.Println(dog.GetName())
	dog.SetName("阿帕奇")
	fmt.Println(dog.GetName())

	// golang中空接口和类型断言
	var userInfo = make(map[string]interface{})
	userInfo["userName"] = "zhangsan"
	userInfo["age"] = 10
	userInfo["hobby"] = []string{"吃饭", "睡觉"}
	fmt.Println(userInfo["userName"])
	fmt.Println(userInfo["age"])
	fmt.Println(userInfo["hobby"])
	// 但是我们空接口如何获取数组中的值？发现 userInfo["hobby"][0]  这样做不行
	// fmt.Println(userInfo["hobby"][0])

	// 这个时候我们就可以使用类型断言了
	hobbyValue,ok := userInfo["hobby"].([]string)
	if ok {
		fmt.Println(hobbyValue[0])
	}

}
