# Go中的结构体

## 关于结构体

Golang中没有“类”的概念，Golang中的结构体和其他语言中的类有点相似。和其他面向对象语言中的类相比，Golang中的结构体具有更高的扩展性和灵活性。

Golang中的基础数据类型可以装示一些事物的基本属性，但是当我们想表达一个事物的全部或部分属性时，这时候再用单一的基本数据类型就无法满足需求了，Golang提供了一种自定义数据类型，可以封装多个基本数据类型，这种数据类型叫结构体，英文名称struct。也就是我们可以通过struct来定义自己的类型了。

## Type关键字

Golang中通过type关键词定义一个结构体，需要注意的是，数组和结构体都是值类型，在这个和Java是有区别的

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

## 结构体方法和接收者

在go语言中，没有类的概念但是可以给类型（结构体，自定义类型）定义方法。所谓方法就是定义了接收者的函数。接收者的概念就类似于其他语言中的this 或者self。

方法的定义格式如下：

```go
func (接收者变量 接收者类型) 方法名(参数列表)(返回参数) {
    函数体
}
```

**其中**

- 接收者变量：接收者中的参数变量名在命名时，官方建议使用接收者类型名的第一个小写字母，而不是self、this之类的命名。例如，Person类型的接收者变量应该命名为p，Connector类型的接收者变量应该命名为c等。、
- 接收者类型：接收者类型和参数类似，可以是指针类型和非指针类型。
  - 非指针类型：表示不修改结构体的内容
  - 指针类型：表示修改结构体中的内容
- 方法名、参数列表、返回参数：具体格式与函数定义相同

如果示例所示：

```go
/**
	定义一个人结构体
 */
type Person struct {
	name string
	age int
	sex string
}

// 定义一个结构体方法
func (p Person) PrintInfo() {
	fmt.Print(" 姓名: ", p.name)
	fmt.Print(" 年龄: ", p.age)
	fmt.Print(" 性别: ", p.sex)
	fmt.Println()
}
func (p *Person) SetInfo(name string, age int, sex string)  {
	p.name = name
	p.age = age
	p.sex = sex
}

func main() {
	var person = Person{
		"张三",
		18,
		"女",
	}
	person.PrintInfo()
	person.SetInfo("李四", 18, "男")
	person.PrintInfo()
}
```

运行结果为：

```bash
 姓名: 张三 年龄: 18 性别: 女
 姓名: 李四 年龄: 18 性别: 男
```



注意，因为结构体是值类型，所以我们修改的时候，因为是传入的指针

```go
func (p *Person) SetInfo(name string, age int, sex string)  {
	p.name = name
	p.age = age
	p.sex = sex
}
```

## 给任意类型添加方法

在Go语言中，接收者的类型可以是任何类型，不仅仅是结构体，任何类型都可以拥有方法。

举个例子，我们基于内置的int类型使用type关键字可以定义新的自定义类型，然后为我们的自定义类型添加方法。

```go
type myInt int
func fun(x int, y int)int {
	return x + y
}
func (m myInt) PrintInfo()  {
	fmt.Println("我是自定义类型里面的自定义方法")
}
func main() {
	var a myInt = 10
	fmt.Printf("%v %T \n", a, a)
	a.PrintInfo()
}
```

## 结构体的匿名字段

结构体允许其成员字段在声明时没有字段名而只有类型，这种没有名字的字段就被称为匿名字段

匿名字段默认采用类型名作为字段名，结构体要求字段名称必须唯一，因此一个结构体中同种类型的匿名字段只能一个

```go
/**
	定义一个人结构体
 */
type Person struct {
	string
	int
}

func main() {
	// 结构体的匿名字段
	var person = Person{
		"张三",
		18
	}
}
```

结构体的字段类型可以是：基本数据类型，也可以是切片、Map 以及结构体

如果结构体的字段类似是：指针、slice、和 map 的零值都是nil，即还没有分配空间

如果需要使用这样的字段，需要先make，才能使用

```go
/**
	定义一个人结构体
 */
type Person struct {
	name string
	age int
	hobby []string
	mapValue map[string]string
}

func main() {
	// 结构体的匿名字段
	var person = Person{}
	person.name = "张三"
	person.age = 10

	// 给切片申请内存空间
	person.hobby = make([]string, 4, 4)
	person.hobby[0] = "睡觉"
	person.hobby[1] = "吃饭"
	person.hobby[2] = "打豆豆"

	// 给map申请存储空间
	person.mapValue = make(map[string]string)
	person.mapValue["address"] = "北京"
	person.mapValue["phone"] = "123456789"

	// 加入#打印完整信息
	fmt.Printf("%#v", person)
}
```

同时我们还支持结构体的嵌套，如下所示

```go
// 用户结构体
type User struct {
	userName string
	password string
	sex string
	age int
	address Address // User结构体嵌套Address结构体
}

// 收货地址结构体
type Address struct {
	name string
	phone string
	city string
}

func main() {
	var u User
	u.userName = "moguBlog"
	u.password = "123456"
	u.sex = "男"
	u.age = 18
	
	var address Address
	address.name = "张三"
	address.phone = "110"
	address.city = "北京"
	u.address = address
	fmt.Printf("%#v", u)
}
```

## 嵌套结构体的字段名冲突

嵌套结构体内部可能存在相同的字段名，这个时候为了避免歧义，需要指定具体的内嵌结构体的字段。（例如，父结构体中的字段 和 子结构体中的字段相似）

默认会从父结构体中寻找，如果找不到的话，再去子结构体中在找

如果子类的结构体中，同时存在着两个相同的字段，那么这个时候就会报错了，因为程序不知道修改那个字段的为准。

## 结构体的继承

结构体的继承，其实就类似于结构体的嵌套，如下所示，我们定义了两个结构体，分别是Animal 和 Dog，其中每个结构体都有各自的方法，然后通过Dog结构体 继承于 Animal结构体

```go
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
```

运行后，发现Dog拥有了父类的方法

```bash
阿帕奇 在运动 
阿帕奇 在汪汪汪
```

## Go中的结构体和Json相互转换

JSON（JavaScript Object Notation）是一种轻量级的数据交换格式。易于人阅读和编写。同时也易于机器解析和生成。RESTfull Api接口中返回的数据都是json数据。

```json
{
    "name": "张三",
    "age": 15
}
```

比如我们Golang要给App或者小程序提供Api接口数据，这个时候就需要涉及到结构体和Json之间的相互转换
Golang JSON序列化是指把结构体数据转化成JSON格式的字符串，Golang JSON的反序列化是指把JSON数据转化成Golang中的结构体对象

Golang中的序列化和反序列化主要通过“encoding/json”包中的 json.Marshal() 和 son.Unmarshal()

```go
// 定义一个学生结构体，注意结构体的首字母必须大写，代表公有，否则将无法转换
type Student struct {
	ID string
	Gender string
	Name string
	Sno string
}
func main() {
	var s1 = Student{
		ID: "12",
		Gender: "男",
		Name: "李四",
		Sno: "s001",
	}
	// 结构体转换成Json（返回的是byte类型的切片）
	jsonByte, _ := json.Marshal(s1)
	jsonStr := string(jsonByte)
	fmt.Printf(jsonStr)
}
```

将字符串转换成结构体类型

```go
// 定义一个学生结构体，注意结构体的首字母必须大写，代表公有，否则将无法转换
type Student struct {
	ID string
	Gender string
	Name string
	Sno string
}
func main() {
	// Json字符串转换成结构体
	var str = `{"ID":"12","Gender":"男","Name":"李四","Sno":"s001"}`
	var s2 = Student{}
	// 第一个是需要传入byte类型的数据，第二参数需要传入转换的地址
	err := json.Unmarshal([]byte(str), &s2)
	if err != nil {
		fmt.Printf("转换失败 \n")
	} else {
		fmt.Printf("%#v \n", s2)
	}
}

```

### 注意

我们想要实现结构体转换成字符串，必须保证结构体中的字段是公有的，也就是首字母必须是大写的，这样才能够实现结构体 到 Json字符串的转换。

## 结构体标签Tag

Tag是结构体的元信息，可以在运行的时候通过反射的机制读取出来。Tag在结构体字段的后方定义，由一对反引号包裹起来，具体的格式如下：

```json
key1："value1" key2："value2"
```

结构体tag由一个或多个键值对组成。键与值使用冒号分隔，值用双引号括起来。同一个结构体字段可以设置多个键值对tag，不同的键值对之间使用空格分隔。

注意事项：为结构体编写Tag时，必须严格遵守键值对的规则。结构体标签的解析代码的容错能力很差，一旦格式写错，编译和运行时都不会提示任何错误，通过反射也无法正确取值。例如不要在key和value之间添加空格。

如下所示，我们通过tag标签，来转换字符串的key

```go
// 定义一个Student体，使用结构体标签
type Student2 struct {
	Id string `json:"id"` // 通过指定tag实现json序列化该字段的key
	Gender string `json:"gender"`
	Name string `json:"name"`
	Sno string `json:"sno"`
}
func main() {
	var s1 = Student2{
		Id: "12",
		Gender: "男",
		Name: "李四",
		Sno: "s001",
	}
	// 结构体转换成Json
	jsonByte, _ := json.Marshal(s1)
	jsonStr := string(jsonByte)
	fmt.Println(jsonStr)

	// Json字符串转换成结构体
	var str = `{"Id":"12","Gender":"男","Name":"李四","Sno":"s001"}`
	var s2 = Student2{}
	// 第一个是需要传入byte类型的数据，第二参数需要传入转换的地址
	err := json.Unmarshal([]byte(str), &s2)
	if err != nil {
		fmt.Printf("转换失败 \n")
	} else {
		fmt.Printf("%#v \n", s2)
	}
}
```

## 嵌套结构体和Json序列化反序列化

和刚刚类似，我们同样也是使用的是 json.Marshal()

```go
// 嵌套结构体 到 Json的互相转换

// 定义一个Student结构体
type Student3 struct {
	Id int
	Gender string
	Name string
}

// 定义一个班级结构体
type Class struct {
	Title string
	Students []Student3
}

func main() {
	var class = Class{
		Title: "1班",
		Students: make([]Student3, 0),
	}
	for i := 0; i < 10; i++ {
		s := Student3{
			Id: i + 1,
			Gender: "男",
			Name: fmt.Sprintf("stu_%v", i + 1),
		}
		class.Students = append(class.Students, s)
	}
	fmt.Printf("%#v \n", class)

	// 转换成Json字符串
	strByte, err := json.Marshal(class)
	if err != nil {
		fmt.Println("打印失败")
	} else {
		fmt.Println(string(strByte))
	}
}
```



