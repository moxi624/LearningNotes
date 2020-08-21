# 日志库

## 来源

https://www.liwenzhou.com/posts/Go/go_log/

## 介绍

无论是软件开发的调试阶段还是软件上线之后的运行阶段，日志一直都是非常重要的一个环节，我们也应该养成在程序中记录日志的好习惯。

Go语言内置的`log`包实现了简单的日志服务。本文介绍了标准库`log`的基本使用。

## 使用Logger

log包定义了Logger类型，该类型提供了一些格式化输出的方法。本包也提供了一个预定义的“标准”logger，可以通过调用函数`Print系列`(Print|Printf|Println）、`Fatal系列`（Fatal|Fatalf|Fatalln）、和`Panic系列`（Panic|Panicf|Panicln）来使用，比自行创建一个logger对象更容易使用。

例如，我们可以像下面的代码一样直接通过`log`包来调用上面提到的方法，默认它们会将日志信息打印到终端界面

```go
package main

import (
	"log"
)

func main() {
	log.Println("这是一条很普通的日志。")
	v := "很普通的"
	log.Printf("这是一条%s日志。\n", v)
	log.Fatalln("这是一条会触发fatal的日志。")
	log.Panicln("这是一条会触发panic的日志。")
}
```

编译并执行上面的代码会得到如下输出：

```bash
2017/06/19 14:04:17 这是一条很普通的日志。
2017/06/19 14:04:17 这是一条很普通的日志。
2017/06/19 14:04:17 这是一条会触发fatal的日志
```

logger会打印每条日志信息的日期、时间，默认输出到系统的标准错误。Fatal系列函数会在写入日志信息后调用os.Exit(1)。Panic系列函数会在写入日志信息后panic。

## 日志输出到文件中

我们正常的日志文件，是存储在文件中的，因此我们可以使用以下的方式，将日志存储在文件中

```go
func main() {
	fileObj, err := os.OpenFile("./xx.log", os.O_APPEND | os.O_CREATE | os.O_WRONLY, 0644)
	if err != nil {
		fmt.Printf("open file failed, err : %v \n", err)
		return
	}
    // 设置log的输出路径
	log.SetOutput(fileObj)
	for {
		log.Println("这是一条测试日志")
		time.Sleep(time.Second * 3)
	}
}
```

## 日志库的简单实现

- 支持往不同的地方输出日志
- 日志分级别
  - debug
  - Trace
  - info
  - warning
  - Error
  - Fatal：严重错误
- 日志要支持开关控制，比如说开发的时候什么级别都能输出，但是上线之后只有INFO级别往下才能输出
- 日志要有时间、行号、文件名、日志级别、日志信息
- 日志文件要切割
- 

```go
package main

import (
	"errors"
	"fmt"
	"path"
	"runtime"
	"strings"
	"time"
)

// 往终端写日志相关内容

type LogLevel uint16

// 定义日志级别
const(
	UNKNOWN LogLevel = iota   // 0
	DEBUG
	TRACE
	INFO
	WARNING
	ERROR
	FATAL
)

// Logger日志结构体
type Logger struct {
	Level LogLevel
}

func parseLogLevel(s string) (LogLevel, error) {
	s = strings.ToLower(s)
	switch s {
	case "debug":
		return DEBUG, nil
	case "trace":
		return TRACE, nil
	case "info":
		return INFO, nil
	case "warning":
		return WARNING, nil
	case "error":
		return ERROR, nil
	case "fatal":
		return FATAL, nil
	default:
		err := errors.New("无效的日志级别")
		return UNKNOWN, err
	}

}

func getLogLevelStr(logLevel LogLevel) (string) {
	switch logLevel {
	case DEBUG:
		return "debug"
	case TRACE:
		return "trace"
	case INFO:
		return "info"
	case WARNING:
		return "warning"
	case ERROR:
		return "error"
	case FATAL:
		return "fatal"
	default:
		return "unknown"
	}
}

// 获取函数名、文件名、行号
// skip表示隔了几层
func getInfo(skip int)(funcName string, fileName string, lineNo int)  {
	// pc：函数信息
	// file：文件
	// line：行号，也就是当前行号
	pc, file, line, ok := runtime.Caller(skip)
	if !ok {
		fmt.Printf("runtime.Caller() failed, err:%v \n")
		return
	}
	funName := runtime.FuncForPC(pc).Name()

	return funName, path.Base(file), line
}

// Logger构造方法
func NewLog(levelStr string) Logger {
	level, err := parseLogLevel(levelStr)
	if err != nil {
		panic(err)
	}
	// 构造了一个Logger对象
	return Logger{
		Level: level,
	}
}

// 判断啥级别的日志可以输出是否输出
func (l Logger) enable(logLevel LogLevel) bool {
	return logLevel >= l.Level
}

func printLog(lv LogLevel, msg string)  {
	now := time.Now().Format("2006-01-02 15:04:05")
	// 拿到第二层的函数名
	funcName, filePath, lineNo := getInfo(3)
	fmt.Printf("[%s] [%s] [%s:%s:%d] %s \n", now, getLogLevelStr(lv),filePath, funcName, lineNo, msg)

}

func (l Logger) Debug(msg string) {
	if l.enable(DEBUG) {
		printLog(DEBUG, msg)
	}
}

func (l Logger) TRACE(msg string) {
	if l.enable(TRACE) {
		printLog(TRACE, msg)
	}
}

func (l Logger) Info(msg string) {
	if l.enable(INFO) {
		printLog(INFO, msg)
	}
}

func (l Logger) Warning(msg string) {

	if l.enable(WARNING) {
		printLog(WARNING, msg)
	}
}

func (l Logger) Error(msg string) {
	if l.enable(ERROR) {
		printLog(ERROR, msg)
	}
}

func (l Logger) Fatal(msg string) {
	if l.enable(FATAL) {
		printLog(FATAL, msg)
	}
}

func main() {
	log := NewLog("ERROR")
	for {
		log.Debug("这是一条DEBUG日志")
		log.Info("这是一条INFO日志")
		log.Warning("这是一条WARNING日志")
		log.Error("这是一条ERROR日志")
		log.Fatal("这是一条FATAL日志")
		fmt.Println("----------------")
		time.Sleep(time.Second)
	}
}
```

