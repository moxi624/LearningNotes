package main

import (
	"fmt"
	"github.com/hpcloud/tail"
	"time"
)

// tailf的用法
func main() {
	// 需要记录的日志文件
	fileName := "./my.log"

	//
	config := tail.Config{
		ReOpen: true, // 重新打开，日志文件到了一定大小，就会分裂
		Follow: true, // 是否跟随
		Location: &tail.SeekInfo{Offset: 0, Whence: 2}, // 从文件的哪个位置开始读
		MustExist: false, // 是否必须存在，如果不存在是否报错
		Poll: true, //
	}

	tails, err := tail.TailFile(fileName, config)
	if err != nil {
		fmt.Println("tail file failed, err:", err)
		return
	}

	var(
		line *tail.Line
		ok bool
	)
	for {
		// 从tails中一行一行的读取
		line, ok = <- tails.Lines
		if !ok {
			fmt.Println("tail file close reopen, filename:%s\n", tails.Filename)
			time.Sleep(time.Second)
			continue
		}
		fmt.Println("line", line.Text)
	}
}
