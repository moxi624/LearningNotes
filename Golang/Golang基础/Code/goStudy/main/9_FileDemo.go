package main

import (
	"errors"
	"fmt"
)

func readFile(fileName string) error {
	if fileName == "main.go" {
		return nil
	} else {
		return errors.New("读取文件失败")
	}
}

func myFn () {
	defer func() {
		e := recover()
		if e != nil {
			fmt.Println("给管理员发送邮件")
		}
	}()
	err := readFile("XXX.go")
	if err != nil {
		panic(err)
	}
}

func main() {
	myFn()
}