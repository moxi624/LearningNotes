package main

import (
	"errors"
	"fmt"
	"os"
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

func printLog(lv LogLevel, format string, a ...interface{})  {

	msg := fmt.Sprintf(format, a...)
	now := time.Now().Format("2006-01-02 15:04:05")
	// 拿到第二层的函数名
	funcName, filePath, lineNo := getInfo(3)

	fileObj, err := os.OpenFile("./xx.log", os.O_APPEND | os.O_CREATE | os.O_WRONLY, 0644)
	if err != nil {
		fmt.Printf("open file failed, err : %v \n", err)
		return
	}

	fmt.Fprintf(fileObj, "[%s] [%s] [%s:%s:%d] %s \n", now, getLogLevelStr(lv),filePath, funcName, lineNo, msg)

	fmt.Printf("[%s] [%s] [%s:%s:%d] %s \n", now, getLogLevelStr(lv),filePath, funcName, lineNo, msg)

}

func (l Logger) Debug(format string, a ...interface{}) {
	if l.enable(DEBUG) {
		printLog(DEBUG, format, a)
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

	log := NewLog("DEBUG")
	for {
		log.Debug("这是一条DEBUG日志: %v", 123)
		log.Info("这是一条INFO日志")
		log.Warning("这是一条WARNING日志")
		log.Error("这是一条ERROR日志")
		log.Fatal("这是一条FATAL日志")
		fmt.Println("----------------")
		time.Sleep(time.Second)
	}
}