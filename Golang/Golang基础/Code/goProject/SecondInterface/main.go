package SecondInterface

import "fmt"

// 定义一个Animal的接口，Animal中定义了两个方法，分别是setName 和 getName，分别让DOg结构体和Cat结构体实现
type Animal1 interface {
	SetName(string)
}

// 接口2
type Animal2 interface {
	GetName()string
}

type Animal interface {
	Animal1
	Animal2
}

type Dog struct {
	Name string
}

func (d *Dog) SetName(name string)  {
	d.Name = name
}
func (d Dog)GetName()string {
	return d.Name
}

func main() {
	var dog = &Dog{
		"小黑",
	}
	// 同时实现两个接口
	var d Animal = dog
	d.SetName("小鸡")
	fmt.Println(d.GetName())
}
