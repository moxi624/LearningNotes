# Go中的接口

## 接口的介绍

现实生活中手机、相机、U盘都可以和电脑的USB接口建立连接。我们不需要关注usb卡槽大小是否一样，因为所有的USB接口都是按照统一的标准来设计的。

![image-20200722201435128](images/image-20200722201435128.png)

Golang中的接口是一种抽象数据类型，Golang中接口定义了对象的行为规范，只定义规范不实现。接口中定义的规范由具体的对象来实现。

通俗的讲接口就一个标准，它是对一个对象的行为和规范进行约定，约定实现接口的对象必须得按照接口的规范。

## Go接口的定义

在Golang中接口（interface）是一种类型，一种抽象的类型。接口（interface）是一组函数method的集合，Golang中的接口不能包含任何变量。

在Golang中接口中的所有方法都没有方法体，接口定义了一个对象的行为规范，只定义规范不实现。接口体现了程序设计的多态和高内聚低耦合的思想N Golang中的接口也是一种数据类型，不需要显示实现。只需要一个变量含有接口类型中的所有方法，那么这个变量就实现了这个接口。

Golang中每个接口由数个方法组成，接口的定义格式如下：

```go
type 接口名 interface {
    方法名1 (参数列表1) 返回值列表1
    方法名2 (参数列表2) 返回值列表2
}
```

**其中**

- **接口名**：使用type将接口定义为自定义的类型名。Go语言的接口在命名时，一般会在单词后面添加er，如有写操作的接口叫Writer，有字符串功能的接口叫Stringer等，接口名最好突出该接口的类型含义。
- **方法名**：当方法名首字母是大写且这个接口类型名首字母也是大写时，这个方法可以被接口所在的包（package）之外的代码访问。
- **参数列表、返回值列表**：参数列表和返回值列表中的参数变量名是可以省略

演示：定义一个Usber接口让Phone 和 Camera结构体实现这个接口

首先我们定义一个Usber接口，接口里面就定义了两个方法

```
// 定义一个Usber接口
type Usber interface {
	start()
	stop()
}
```

然后我们在创建一个手机结构体

```go
// 如果接口里面有方法的话，必须要通过结构体或自定义类型实现这个接口

// 使用结构体来实现 接口
type Phone struct {
	Name string
}
// 手机要实现Usber接口的话，必须实现usb接口的所有方法
func (p Phone) Start()  {
	fmt.Println(p.Name, "启动")
}
func (p Phone) Stop()  {
	fmt.Println(p.Name, "关闭")
}
```

然后我们在创建一个Phone的结构体，来实现这个接口

```go
// 如果接口里面有方法的话，必须要通过结构体或自定义类型实现这个接口

// 使用结构体来实现 接口
type Phone struct {
	Name string
}
// 手机要实现Usber接口的话，必须实现usb接口的所有方法
func (p Phone) start()  {
	fmt.Println(p.Name, "启动")
}
func (p Phone) stop()  {
	fmt.Println(p.Name, "关闭")
}
func main() {
	var phone Usber = Phone{
		"三星手机",
	}
	phone.start()
	phone.stop()
}
```

我们在创建一个Camera结构体

```go
// 使用相机结构体来实现 接口
type Camera struct {
	Name string
}
// 相机要实现Usber接口的话，必须实现usb接口的所有方法
func (p Camera) start()  {
	fmt.Println(p.Name, "启动")
}
func (p Camera) stop()  {
	fmt.Println(p.Name, "关闭")
}
func main() {
	var camera Usber = Camera{
		"佳能",
	}
	camera.start()
	camera.stop()
}
```

我们创建一个电脑的结构体，电脑的结构体就是用于接收两个实现了Usber的结构体，然后让其工作

```go
// 电脑
type Computer struct {

}

// 接收一个实现了Usber接口的 结构体
func (computer Computer) Startup(usb Usber)  {
	usb.start()
}

// 关闭
func (computer Computer) Shutdown (usb Usber)  {
	usb.stop()
}
```

最后我们在main中调用方法

```go
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
}
```

运行结果如下所示：

```bash
佳能 启动
苹果 启动
佳能 关闭
苹果 关闭
```

## 空接口（Object类型）

Golang中的接口可以不定义任何方法，没有定义任何方法的接口就是空接口。空接口表示没有任何约束，因此任何类型变量都可以实现空接口。

空接口在实际项目中用的是非常多的，用空接口可以表示任意数据类型。

```go
// 空接口表示没有任何约束，任意的类型都可以实现空接口
type EmptyA interface {

}

func main() {
	var a EmptyA
	var str = "你好golang"
	// 让字符串实现A接口
	a = str
	fmt.Println(a)
}
```

同时golang中空接口也可以直接当做类型来使用，可以表示任意类型。相当于Java中的Object类型

```go
var a interface{}
a = 20
a = "hello"
a = true
```

空接口可以作为函数的参数，使用空接口可以接收任意类型的函数参数

```go
// 空接口作为函数参数
func show(a interface{}) {
    fmt.println(a)
}
```

### map的值实现空接口

使用空接口实现可以保存任意值的字典

```go
// 定义一个值为空接口类型
var studentInfo = make(map[string]interface{})
studentInfo["userName"] = "张三"
studentInfo["age"] = 15
studentInfo["isWork"] = true
```

### slice切片实现空接口

```go
// 定义一个空接口类型的切片
var slice = make([]interface{}, 4, 4)
slice[0] = "张三"
slice[1] = 1
slice[2] = true
```

## 类型断言

一个接口的值（简称接口值）是由一个具体类型和具体类型的值两部分组成的。这两部分分别称为接口的动态类型和动态值。

如果我们想要判断空接口中值的类型，那么这个时候就可以使用类型断言，其语法格式：

```bash
x.(T)
```

其中：

- X：表示类型为interface{}的变量
- T：表示断言x可能是的类型

该语法返回两个参数，第一个参数是x转化为T类型后的变量，第二个值是一个布尔值，若为true则表示断言成功，为false则表示断言失败

```go
// 类型断言
var a interface{}
a = "132"
value, isString := a.(string)
if isString {
    fmt.Println("是String类型, 值为：", value)
} else {
    fmt.Println("断言失败")
}
```

或者我们可以定义一个能传入任意类型的方法

```go
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
```

上面的示例代码中，如果要断言多次，那么就需要写很多if，这个时候我们可以使用switch语句来实现：

**注意：** 类型.(type) 只能结合switch语句使用

```go
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
```

## 结构体接收者

### 值接收者

如果结构体中的方法是值接收者，那么实例化后的结构体值类型和结构体指针类型都可以赋值给接口变量

## 结构体实现多个接口

实现多个接口的话，可能就同时用两个接口进行结构体的接受

```go
// 定义一个Animal的接口，Animal中定义了两个方法，分别是setName 和 getName，分别让DOg结构体和Cat结构体实现
type Animal interface {
	SetName(string)
}

// 接口2
type Animal2 interface {
	GetName()string
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
	var d1 Animal = dog
	var d2 Animal2 = dog
	d1.SetName("小鸡")
	fmt.Println(d2.GetName())
}
```

## 接口嵌套

在golang中，允许接口嵌套接口，我们首先创建一个 Animal1 和 Animal2 接口，然后使用Animal接受刚刚的两个接口，实现接口的嵌套。

```go
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
```

## Golang中空接口和类型断言

```go
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
```

也就是我们的空接口，无法直接通过索引获取数组中的内容，因此这个时候就需要使用类型断言了

```go
// 这个时候我们就可以使用类型断言了
hobbyValue,ok := userInfo["hobby"].([]string)
if ok {
    fmt.Println(hobbyValue[0])
}
```

通过类型断言返回来的值，我们就能够直接通过角标获取了。

