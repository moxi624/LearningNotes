# Golang map详解

## map的介绍

map是一种无序的基于key-value的数据结构，Go语言中的map是引用类型，必须初始化才能使用。

Go语言中map的定义语法如下：

```go
map[KeyType]ValueType
```

其中：

- KeyType：表示键的类型
- ValueType：表示键对应的值的类型

map类型的变量默认初始值为nil，需要使用make()函数来分配内存。语法为：

make：用于slice、map和channel的初始化

示例如下所示：

```go
// 方式1初始化
var userInfo = make(map[string]string)
userInfo["userName"] = "zhangsan"
userInfo["age"] = "20"
userInfo["sex"] = "男"
fmt.Println(userInfo)
fmt.Println(userInfo["userName"])
```

```go
// 创建方式2，map也支持声明的时候填充元素
var userInfo2 = map[string]string {
    "username":"张三",
    "age":"21",
    "sex":"女",
}
fmt.Println(userInfo2)
```

## 遍历map

使用for range遍历

```go
// 遍历map
for key, value := range userInfo2 {
    fmt.Println("key:", key, " value:", value)
}
```

## 判断map中某个键值是否存在

我们在获取map的时候，会返回两个值，也可以是返回的结果，一个是是否有该元素

```go
// 判断是否存在,如果存在  ok = true，否则 ok = false
value, ok := userInfo2["username2"]
fmt.Println(value, ok)
```

## 使用delete()函数删除键值对

使用delete()内建函数从map中删除一组键值对，delete函数的格式如下所示

```bash
delete(map 对象, key)
```

其中：

- map对象：表示要删除键值对的map对象
- key：表示要删除的键值对的键

示例代码如下

```go
// 删除map数据里面的key，以及对应的值
delete(userInfo2, "sex")
fmt.Println(userInfo2)
```

## 元素为map类型的切片

我们想要在切片里面存放一系列用户的信息，这时候我们就可以定义一个元素为map类型的切片

```go
// 切片在中存放map
var userInfoList = make([]map[string]string, 3, 3)
var user = map[string]string{
    "userName": "张安",
    "age": "15",
}
var user2 = map[string]string{
    "userName": "张2",
    "age": "15",
}
var user3 = map[string]string{
    "userName": "张3",
    "age": "15",
}
userInfoList[0] = user
userInfoList[1] = user2
userInfoList[2] = user3
fmt.Println(userInfoList)

for _, item := range userInfoList {
    fmt.Println(item)
}
```

## 值为切片类型的map

我们可以在map中存储切片

```go
// 将map类型的值
var userinfo = make(map[string][]string)
userinfo["hobby"] = []string {"吃饭", "睡觉", "敲代码"}
fmt.Println(userinfo)
```

## 示例

统计字符串中单词出现的次数

```go
// 写一个程序，统计一个字符串中每个单词出现的次数。比如 "how do you do"
var str = "how do you do"
array := strings.Split(str, " ")
fmt.Println(array)
countMap := make(map[string]int)
for _, item := range array {
    countMap[item]++
}
fmt.Println(countMap)
```

