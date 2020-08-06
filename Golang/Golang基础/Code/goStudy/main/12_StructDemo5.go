package main
import "fmt"

// 结构体的继承
// 用户结构体
type Animal struct {
	name string
}
func (a Animal) run() {
	fmt.Printf("%v 在运动 \n", a.name)
}
// 子结构体
type Dog struct {
	age int
	// 通过结构体嵌套，完成继承
	Animal
}
func (dog Dog) wang()  {
	fmt.Printf("%v 在汪汪汪 \n", dog.name)
}

func main() {
	var dog = Dog{
		age: 10,
		Animal: Animal{
			name: "阿帕奇",
		},
	}
	dog.run();
	dog.wang();
}
