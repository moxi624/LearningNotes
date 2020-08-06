package main

import "fmt"

/**
	定义一个人结构体
 */
type Person struct {
	name string
	age int
	sex string


}
func main() {
	// 实例化结构体
	var person Person
	person.name = "张三"
	person.age = 20
	person.sex = "男"
	fmt.Printf("%#v", person)

	// 第二种方式实例化
	var person2 = new(Person)
	person2.name = "李四"
	person2.age = 30
	person2.sex = "女"
	fmt.Printf("%#v", person2)

	// 第三种方式实例化
	var person3 = &Person{}
	person3.name = "赵四"
	person3.age = 28
	person3.sex = "男"
	fmt.Printf("%#v", person3)

	// 第四种方式初始化
	var person4 = Person{
		name: "张三",
		age: 10,
		sex: "女",
	}
	fmt.Printf("%#v", person4)

	// 第五种方式初始化
	var person5 = &Person{
		name: "孙五",
		age: 10,
		sex: "女",
	}
	fmt.Printf("%#v", person5)

	var person6 = Person{
		"张三",
		5,
		"女",
	}
	fmt.Println(person6)

}
