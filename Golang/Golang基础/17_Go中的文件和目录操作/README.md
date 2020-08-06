# Go中的文件和目录操作

## 文件的读取

###  通过os.Open方法读取文件

```go
func main() {
	// 读取文件 方法1
	file, err := os.Open("./main/test.txt")
	// 关闭文件流
	defer file.Close();
	if err != nil {
		fmt.Println("打开文件出错")
	}
	// 读取文件里面的内容
	var tempSlice = make([]byte, 1024)
	var strSlice []byte
	for {
		n, err := file.Read(tempSlice)
		if err == io.EOF {
			fmt.Printf("读取完毕")
			break
		}
		fmt.Printf("读取到了%v 个字节 \n", n)
		strSlice := append(strSlice, tempSlice...)
		fmt.Println(string(strSlice))
	}
}
```

### 通过bufio的方式读取

```go
func main() {
	// 读取文件 方法2
	file, err := os.Open("./main/test.txt")
	// 关闭文件流
	defer file.Close();
	if err != nil {
		fmt.Println("打开文件出错")
	}
	// 通过创建bufio来读取
	reader := bufio.NewReader(file)
	var fileStr string
	var count int = 0
	for {
		// 相当于读取一行
		str, err := reader.ReadString('\n')
		if err == io.EOF {
			// 读取完成的时候，也会有内容
			fileStr += str
			fmt.Println("读取结束", count)
			break
		}
		if err != nil {
			fmt.Println(err)
			break
		}
		count ++
		fileStr += str
	}
	fmt.Println(fileStr)
}
```

### 通过ioutil读取

文件比较少的时候，可以通过ioutil来读取文件

```go
// 通过IOUtil读取
byteStr, _ := ioutil.ReadFile("./main/test.txt")
fmt.Println(string(byteStr))
```

## 文件的写入

文件的写入，我们首先需要通过 os.OpenFile打开文件

```go
// 打开文件
file, _ := os.OpenFile("./main/test.txt", os.O_CREATE | os.O_RDWR, 777)
```

这里有三个参数

- name：要打开的文件名
- flag：打开文件的模式
  - os.O_WRONLY：只读
  - os.O_CREATE：创建
  - os.O_RDONLY：只读
  - os.O_RDWR：读写
  - os.O_TRUNC：清空
  - os.O_APPEND：追加
- perm：文件权限，一个八进制数，r（读）04，w（写）02，x（执行）01

### 通过OpenFile打开文件写入

```go
// 打开文件
file, _ := os.OpenFile("./main/test.txt", os.O_CREATE | os.O_RDWR | os.O_APPEND, 777)
defer file.Close()
str := "啦啦啦 \r\n"
file.WriteString(str)
```

### 通过bufio写入

```go
// 打开文件
file, _ := os.OpenFile("./main/test.txt", os.O_CREATE | os.O_RDWR | os.O_APPEND, 777)
defer file.Close()
str := "啦啦啦 \r\n"
file.WriteString(str)

// 通过bufio写入
writer := bufio.NewWriter(file)
// 先将数据写入缓存
writer.WriteString("你好，我是通过writer写入的 \r\n")
// 将缓存中的内容写入文件
writer.Flush()	
```

## 通过ioutil写入

```go
// 第三种方式，通过ioutil
str2 := "hello"
ioutil.WriteFile("./main/test.txt", []byte(str2), 777)
```

## 文件复制

通过ioutil读取和复制文件

```go
// 读取文件
byteStr, err := ioutil.ReadFile("./main/test.txt")
if err != nil {
    fmt.Println("读取文件出错")
    return
}
// 写入指定的文件
ioutil.WriteFile("./main/test2.txt", byteStr, 777)
```

## 创建目录

```go
os.Mkdir("./abc", 777)
```

## 删除操作

```go
// 删除文件
os.Remove("aaa.txt")
// 删除目录
os.Remove("./aaa")
// 删除多个文件和目录
os.RemoveAll("./aaa")
```

## 重命名

```go
os.Rename("")
```

