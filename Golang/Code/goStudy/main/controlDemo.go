package main

import "fmt"

func main() {
	var num = 10
	if num == 10 {
		fmt.Println("hello == 10")
	} else if(num > 10) {
		fmt.Println("hello > 10")
	} else {
		fmt.Println("hello < 10")
	}

	if num2:= 10; num2>=10 {
		fmt.Println("hello >=10")
	}

	for i := 0; i < 10; i++ {
		fmt.Printf("%v ", i+1)
	}

	// 打印所有的偶数
	for i := 0; i < 50; i++ {
		if i%2 == 0 {
			fmt.Print(i , "\t")
		}
	}
	fmt.Println()
	sum := 0
	for i := 0; i < 100; i++ {
		sum += i
	}
	fmt.Println("1+...+100：", sum)

	var str = "你好golang"
	for key, value := range str {
		fmt.Printf("%v - %c ", key, value)
	}
	fmt.Println()
	var array = []string{"php", "java", "node", "golang"}
	for index, value := range array {
		fmt.Printf("%v %s ", index, value)
	}

	fmt.Println()
	extname := ".txt"
	switch extname {
		case ".html": {
			fmt.Println(".html")
			break
		}
		case ".txt",".doc": {
			fmt.Println("传递来的是文档")
			break
		}
		case ".js": {
			fmt.Println(".js")
		}
		default: {
			fmt.Println("其它后缀")
		}
	}

	var i = 0
	for  {
		if i == 10{
			fmt.Println("跳出循环")
			break
		}
		i++
		fmt.Println(i)
	}

	var n = 20
	if n > 24 {
		fmt.Println("成年人")
	} else {
		goto lable3
	}

	fmt.Println("aaa")
	fmt.Println("bbb")
lable3:
	fmt.Println("ccc")
	fmt.Println("ddd")

}
