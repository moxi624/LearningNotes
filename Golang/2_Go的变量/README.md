# Go语言中的变量和常量

## Go语言中变量的声明

Go语言变量是由字母、数字、下划线组成，其中首个字符不能为数字。Go语言中关键字和保留字都不能用作变量名

Go语言中变量需要声明后才能使用，同一作用域内不支持重复声明。并且Go语言的变量声明后必须使用。

变量声明后，没有初始化，打印出来的是空

## 如何定义变量

### 方式1：

```go
var name = "zhangsan"
```

### 方式2：带类型

```go
var name string = "zhangsan"
```

### 方式3：类型推导方式定义变量

a在函数内部，可以使用更简略的 := 方式声明并初始化变量

注意：短变量只能用于声明局部变量，不能用于全局变量声明

```go
变量名 := 表达式
```

### 方式4：声明多个变量

类型都是一样的变量

```go
var 变量名称， 变量名称 类型
```

类型不一样的变量

```go
var (
	变量名称 类型
    变量名称 类型
)
```

案例

```go
var a1, a2 string
a1 = "123"
a2 = "123"
fmt.Printf(a1)
fmt.Printf(a2)
```

### 总结

全部的定义方式

```go
package main
import "fmt"

func main() {
	fmt.Println("hello")
	fmt.Print("A", "B", "C")
	fmt.Println()
	var a = 10
	fmt.Printf( "%d", a )

	var name = "zhangsan1"
	var name2 string = "zhangsan2"
	name3 := "zhangsan3"

	fmt.Println(name)
	fmt.Println(name2)
	fmt.Println(name3)
	fmt.Printf("name1=%v name2=%v name3=%v \n", name, name2, name3)
}
```

## 如何定义常量

相对于变量，常量是恒定不变的值，多用于定义程序运行期间不会改变的那些值。常量的声明和变量声明非常类似，只是把var换成了const，常量在定义的时候必须赋值。

```go
// 定义了常量，可以不用立即使用
const pi = 3.14

// 定义两个常量
const(
    A = "A"
    B = "B"
)

// const同时声明多个常量时，如果省略了值表示和上面一行的值相同
const(
    A = "A"
    B
    C
)
```

## Const常量结合iota的使用

iota是golang 语言的常量计数器，只能在常量的表达式中使用

iota在const关键字出现时将被重置为0（const内部的第一行之前），const中每新增一行常量声明将使iota计数一次（iota可理解为const语句块中的行索引）。

每次const出现，都会让iota初始化为0【自增长】

```go
const a = iota // a = 0
const (
	b = iota // b=0
    c        // c = 1
    d        // d = 2
)
```

const  iota使用_跳过某些值

```go
const (
	b = iota // b=0
    _
    d        // d = 2
)
```

