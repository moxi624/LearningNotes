package main

import (
	"fmt"
	"reflect"
)

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
func main() {

	student := Student4{
		"张三",
		18,
		95,
	}
	PrintStructField(student)
	PrintStructFn(&student)
}
