package taillog

import (
	"fmt"
	"github.com/hpcloud/tail"
)

// 定义全局对象
var (
	// 声明一个全局连接kafka的生产者
	tailObj *tail.Tail
)

// 专门从日志文件收集日志的模块
func Init(fileName string)(err error ){

	// 定义配置文件
	config := tail.Config{
		ReOpen: true, // 重新打开，日志文件到了一定大小，就会分裂
		Follow: true, // 是否跟随
		Location: &tail.SeekInfo{Offset: 0, Whence: 2}, // 从文件的哪个位置开始读
		MustExist: false, // 是否必须存在，如果不存在是否报错
		Poll: true, //
	}

	tailObj, err = tail.TailFile(fileName, config)
	if err != nil {
		fmt.Println("tail file failed, err:", err)
		return
	}
	return err
}

// 读取日志，返回一个只读的chan
func ReadChan() <-chan *tail.Line {
	return tailObj.Lines
}
