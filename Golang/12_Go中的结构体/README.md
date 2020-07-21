# Go中的结构体

## 关于结构体

Golang中没有“类”的概念，Golang中的结构体和其他语言中的类有点相似。和其他面向对象语言中的类相比，Golang中的结构体具有更高的扩展性和灵活性。

Golang中的基础数据类型可以装示一些事物的基本属性，但是当我们想表达一个事物的全部或部分属性时，这时候再用单一的基本数据类型就无法满足需求了，Golang提供了一种自定义数据类型，可以封装多个基本数据类型，这种数据类型叫结构体，英文名称struct。也就是我们可以通过struct来定义自己的类型了。

## Type关键字

Golang中通过type关键词定义一个结构体

### 自定义类型

在Go语言中有一些基本的数据类型，如string、整型、浮点型、布尔等数据类型，Go语言中可以使用type关键字来定义自定义类型。

```go
type myInt int
```

上面代码表示：将mylnt定义为int类型，通过type关键字的定义，mylnt就是一种新的类型，它具有int的特性。

示例：如下所示，我们定义了一个myInt类型

```
type myInt int
func main() {
	var a myInt = 10
	fmt.Printf("%v %T", a, a)
}
```

输出查看它的值以及类型，能够发现该类型就是myInt类型

```go
10 main.myInt
```

除此之外，我们还可以定义一个方法类型

```go
func fun(x int, y int)int {
	return x + y
}
func main() {
	var fn myFn = fun
	fmt.Println(fn(1, 2))
}
```

然后调用并输出

```bash
3
```

### 类型别名

Golang1.9版本以后添加的新功能

类型别名规定：TypeAlias只是Type的别名，本质上TypeAlias与Type是同一个类型。就像一个孩子小时候有大名、小名、英文名，但这些名字都指的是他本人

```go
type TypeAlias = Type
```

我们之前见过的rune 和 byte 就是类型别名，他们的底层代码如下

```go
type byte = uint8
type rune = int32
```

## 结构体定义和初始化

### 结构体的定义

使用type  和 struct关键字来定义结构体，具体代码格式如下所示：

```go
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
}
```

> 注意：结构体首字母可以大写也可以小写，大写表示这个结构体是公有的，在其它的包里面也可以使用，小写表示结构体属于私有的，在其它地方不能使用

例如：

```go
type Person struct {
	Name string
	Age int
	Sex string
}
```

### 实例化结构体

刚刚实例化结构体用到了：var person Person

```go
// 实例化结构体
var person Person
person.name = "张三"
person.age = 20
person.sex = "男"
```

### 实例化结构体2

我们下面使用另外一个方式来实例化结构体，通过new关键字来实例化结构体，得到的是结构体的地址，格式如下

```go
var person2 = new(Person)
person2.name = "李四"
person2.age = 30
person2.sex = "女"
fmt.Printf("%#v", person2)
```

输出如下所示，从打印结果可以看出person2是一个结构体指针

```bash
&main.Person{name:"李四", age:30, sex:"女"}
```

需要注意：在Golang中支持对结构体指针直接使用，来访问结构体的成员

```go
person2.name = "李四"
// 等价于
(*person2).name = "李四"
```

### 实例化结构体3

使用&对结构体进行取地址操作，相当于对该结构体类型进行了一次new实例化操作

```go
// 第三种方式实例化
var person3 = &Person{}
person3.name = "赵四"
person3.age = 28
person3.sex = "男"
fmt.Printf("%#v", person3)
```

### 实例化结构体4

使用键值对的方式来实例化结构体，实例化的时候，可以直接指定对应的值

```go
// 第四种方式初始化
var person4 = Person{
    name: "张三",
    age: 10,
    sex: "女",
}
fmt.Printf("%#v", person4)
```

### 实例化结构体5

第五种和第四种差不多，不过是用了取地址，然后返回的也是一个地址

```go
// 第五种方式初始化
var person5 = &Person{
    name: "孙五",
    age: 10,
    sex: "女",
}
fmt.Printf("%#v", person5)
```

### 实例化结构体6

第六种方式是可以简写结构体里面的key

```go
var person6 = Person{
    "张三",
    5,
    "女",
}
fmt.Println(person6)
```

