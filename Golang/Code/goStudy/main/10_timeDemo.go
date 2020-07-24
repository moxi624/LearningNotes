package main

import (
	"fmt"
	"time"
)

func main() {
	timeObj := time.Now()
	year := timeObj.Year()
	month := timeObj.Month()
	day := timeObj.Day()

	fmt.Printf("%d-%02d-%02d \n", year, month, day)

	/**
		时间类型有一个自带的方法 Format进行格式化
		需要注意的是Go语言中格式化时间模板不是长久的 Y-m-d H:M:S
		而是使用Go的诞生时间 2006年1月2日 15点04分 （记忆口诀：2006 1 2 3 4 5）
	 */
	timeObj2 := time.Now()
	fmt.Println(timeObj2.Format("2006-01-02 03:04:05"))

	/**
		获取当前时间戳
	 */
	timeObj3 := time.Now()
	// 获取秒时间戳
	unixTime := timeObj3.Unix()
	// 获取毫秒时间戳
	unixNaTime := timeObj3.UnixNano()
	fmt.Println(unixTime)
	fmt.Println(unixNaTime)

	// 时间戳转换年月日时分秒（一个参数是秒，另一个参数是毫秒）
	var timeObj4 = time.Unix(1595289901, 0)
	var timeStr = timeObj4.Format("2006-01-02 15:04:05")
	fmt.Println(timeStr)

	// 日期字符串转换成时间戳
	var timeStr2 = "2020-07-21 08:10:05";
	var tmp = "2006-01-02 15:04:05"
	timeObj5, _ := time.ParseInLocation(tmp, timeStr2, time.Local)
	fmt.Println(timeObj5.Unix())

	// 时间相加
	now := time.Now()
	// 当前时间加1个小时后
	later := now.Add(time.Hour)
	fmt.Println(later)

	// 定时器, 定义一个1秒间隔的定时器
	ticker := time.NewTicker(time.Second)
	n := 0
	for i := range ticker.C {
		fmt.Println(i)
		n++
		if n>5 {
			ticker.Stop()
			return
		}
	}

	for  {
		time.Sleep(time.Second)
		fmt.Println("一秒后")
	}

}