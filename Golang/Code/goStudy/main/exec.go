package main

import "fmt"

func main() {
	fmt.Println("hello")
	fmt.Print("A", "B", "C")
	fmt.Println()
	var a = 10
	fmt.Printf("%d", a)

	var name = "zhangsan1"
	var name2 string = "zhangsan2"
	name3 := "zhangsan3"

	fmt.Println(name)
	fmt.Println(name2)
	fmt.Println(name3)
	fmt.Printf("name1=%v name2=%v name3=%v", name, name2, name3)

	var a1, a2 string
	a1 = "123"
	a2 = "123"
	fmt.Printf(a1)
	fmt.Printf(a2)

	const pi = 3.14
	const pp = 3.33333
}
