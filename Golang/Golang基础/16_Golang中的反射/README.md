# Go中的反射

## 反射

有时我们需要写一个函数，这个函数有能力统一处理各种值类型，而这些类型可能无法共享同一个接口，也可能布局未知，也有可能这个类型在我们设计函数时还不存在，这个时候我们就可以用到反射。

空接口可以存储任意类型的变量，那我们如何知道这个空接口保存数据的类型是什么？
值是什么呢？

- 可以使用类型断言
- 可以使用反射实现，也就是在程序运行时动态的获取一个变量的类型信息和值信息。

把结构体序列化成json字符串，自定义结构体Tab标签的时候就用到了反射

后面所说的ORM框架，底层就是用到了反射技术

ORM：对象关系映射（Object Relational Mapping，简称 ORM）是通过使用描述对象和数据库之间的映射的元数据，将面向对象语言程序中的对象自动持久化到关系数据库中。

## 反射的基本介绍

反射是指在程序运行期间对程序本身进行访问和修改的能力。正常情况程序在编译时，变量被转换为内存地址，变量名不会被编译器写入到可执行部分。在运行程序时，程序无法获取自身的信息。支持反射的语言可以在程序编译期将变量的反射信息，如字段名称、类型信息、结构体信息等整合到可执行文件中，并给程序提供接口访问反射信息，这样就可以在程序运行期获取类型的反射信息，并且有能力修改它们。

## Go可以实现的功能

- 反射可以在程序运行期间动态的获取变量的各种信息，比如变量的类型类别
- 如果是结构体，通过反射还可以获取结构体本身的信息，比如结构体的字段、结构体的方法。
- 通过反射，可以修改变量的值，可以调用关联的方法

Go语言中的变量是分为两部分的：

- 类型信息：预先定义好的元信息。
- 值信息：程序运行过程中可动态变化的。

在Go语言的反射机制中，任何接口值都由是一个具体类型和具体类型的值两部分组成的。

在Go语言中反射的相关功能由内置的reflect包提供，任意接口值在反射中都可以理解为由 reflect.Type 和 reflect.Value两部分组成，并且reflect包提供了reflect.TypeOf和reflect.ValueOf两个重要函数来获取任意对象的Value 和 Type

## reflect.TypeOf()获取任意值的类型对象

在Go 语言中，使用reflect.TypeOf（）函数可以接受任意interface}参数，可以获得任意值的类型对象（reflect.Type），程序通过类型对象可以访问任意值的类型信息。

通过反射获取空接口的类型

```go
func reflectFun(x interface{})  {
	v := reflect.TypeOf(x)
	fmt.Println(v)
}
func main() {
	reflectFun(10)
	reflectFun(10.01)
	reflectFun("abc")
	reflectFun(true)
}
```

## type name 和 type Kind

在反射中关于类型还划分为两种：类型（Type）和种类（Kind）。因为在Go语言中我们可以使用type关键字构造很多自定义类型，而种类（Kid）就是指底层的类型，但在反射中，当需要区分指针、结构体等大品种的类型时，就会用到种类（Kind）。举个例子，我们定义了两个指针类型和两个结构体类型，通过反射查看它们的类型和种类。

Go 语言的反射中像数组、切片、Map、指针等类型的变量，它们的.Name（）都是返回空。

```go
v := reflect.TypeOf(x)
fmt.Println("类型 ", v)
fmt.Println("类型名称 ", v.Name())
fmt.Println("类型种类 ", v.Kind())
```

我们之前可以通过类型断言来实现空接口类型的数相加操作

```go
func reflectValue(x interface{}) {
	b,_ := x.(int)
	var num = 10 + b
	fmt.Println(num)
}
```

到现在的话，我们就可以使用reflect.TypeOf来实现了

```go
func reflectValue2(x interface{}) {
	// 通过反射来获取变量的原始值
	v := reflect.ValueOf(x)
	fmt.Println(v)
	// 获取到V的int类型
	var n = v.Int() + 12
	fmt.Println(n)
}
```

同时我们还可以通过switch来完成

```go
// 通过反射来获取变量的原始值
v := reflect.ValueOf(x)
// 获取种类
kind := v.Kind()
switch kind {
    case reflect.Int:
    fmt.Println("我是int类型")
    case reflect.Float64:
    fmt.Println("我是float64类型")
    default:
    fmt.Println("我是其它类型")
}
```

## reflect.ValueOf

reflect.ValueOf() 返回的是reflect.Value类型，其中包含了原始值的值信息，reflect.Value与原始值之间可以互相转换

reflect.value类型提供的获取原始值的方法如下

| 方法            | 说明                                                         |
| --------------- | ------------------------------------------------------------ |
| interface{}     | 将值以interface{}类型返回，可以通过类型断言转换为指定类型    |
| Int() int64     | 将值以int类型返回，所有有符号整型均可以此方式返回            |
| Uint() uint64   | 将值以uint类型返回，所有无符号整型均可以以此方式返回         |
| Float() float64 | 将值以双精度(float 64)类型返回，所有浮点数(float 32、float64)均可以以此方式返回 |

## 结构体反射

### 与结构体相关的方法

任意值通过reflect.Typeof）获得反射对象信息后，如果它的类型是结构体，可以通过反射值对象（reflect.Type）的NumField（）和Field（）方法获得结构体成员的详细信息。

reflect.Type中与获取结构体成员相关的的方法如下表所示。

| 方法                                        | 说明                                         |
| ------------------------------------------- | -------------------------------------------- |
| Field(i int)StructField                     | 根据索引，返回索引对应的结构体字段的信息     |
| NumField() int                              | 返回结构体成员字段数量                       |
| FieldByName(name string)(StructField, bool) | 根据给定字符串返回字符串赌赢的结构体字段信息 |
| FieldByIndex(index []int)StructField        | 多层成员访问时，根据[] int 提供的每个结构    |
|                                             |                                              |

示例代码，如下所示 我们修改结构体中的字段和类型

```go
// 学生结构体
type Student4 struct {
	Name string `json: "name"`
	Age int `json: "age"`
	Score int `json: "score"`
}

func (s Student4)GetInfo()string  {
	var str = fmt.Sprintf("姓名：%v 年龄：%v 成绩：%v", s.Name, s.Age, s.Score)
	return str
}
func (s *Student4)SetInfo(name string, age int, score int)  {
	s.Name = name
	s.Age = age
	s.Score = score
}
func (s Student4)PrintStudent()  {
	fmt.Println("打印学生")
}
// 打印结构体中的字段
func PrintStructField(s interface{})  {
	t := reflect.TypeOf(s)
	// 判断传递过来的是否是结构体
	if t.Kind() != reflect.Struct && t.Elem().Kind() != reflect.Struct {
		fmt.Println("请传入结构体类型!")
		return
	}

	// 通过类型变量里面的Field可以获取结构体的字段
	field0 := t.Field(0) // 获取第0个字段
	fmt.Printf("%#v \n", field0)
	fmt.Println("字段名称:", field0.Name)
	fmt.Println("字段类型:", field0.Type)
	fmt.Println("字段Tag:", field0.Tag.Get("json"))

	// 通过类型变量里面的FieldByName可以获取结构体的字段中
	field1, ok := t.FieldByName("Age")
	if ok {
		fmt.Println("字段名称:", field1.Name)
		fmt.Println("字段类型:", field1.Type)
		fmt.Println("字段Tag:", field1.Tag)
	}

	// 通过类型变量里面的NumField获取该结构体有几个字段
	var fieldCount = t.NumField()
	fmt.Println("结构体有：", fieldCount, " 个属性")

	// 获取结构体属性对应的值
	v := reflect.ValueOf(s)
	nameValue := v.FieldByName("Name")
	fmt.Println("nameValue:", nameValue)

}
func main() {

	student := Student4{
		"张三",
		18,
		95,
	}
	PrintStructField(student)
}
```

下列代码是获取结构体中的方法，然后调用

```go
// 打印执行方法
func PrintStructFn(s interface{})  {
	t := reflect.TypeOf(s)
	// 判断传递过来的是否是结构体
	if t.Kind() != reflect.Struct && t.Elem().Kind() != reflect.Struct {
		fmt.Println("请传入结构体类型!")
		return
	}
	// 通过类型变量里面的Method，可以获取结构体的方法
	method0 := t.Method(0)
	// 获取第一个方法， 这个是和ACSII相关
	fmt.Println(method0.Name)

	// 通过类型变量获取这个结构体有多少方法
	methodCount := t.NumMethod()
	fmt.Println("拥有的方法", methodCount)

	// 通过值变量 执行方法（注意需要使用值变量，并且要注意参数）
	v := reflect.ValueOf(s)
	// 通过值变量来获取参数
	v.MethodByName("PrintStudent").Call(nil)

	// 手动传参
	var params []reflect.Value
	params = append(params, reflect.ValueOf("张三"))
	params = append(params, reflect.ValueOf(23))
	params = append(params, reflect.ValueOf(99))
	// 执行setInfo方法
	v.MethodByName("SetInfo").Call(params)

	// 通过值变量来获取参数
	v.MethodByName("PrintStudent").Call(nil)
}
```

