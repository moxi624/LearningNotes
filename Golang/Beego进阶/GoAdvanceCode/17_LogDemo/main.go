package main

import (
	"LogDemo/conf"
	"LogDemo/kafka"
	"LogDemo/taillog"
	"fmt"
	"gopkg.in/ini.v1"
	"time"
)

var (
	cfg = new(conf.AppConf)
)

// logAgent入口程序
func run()  {
	// 1.读取日志
	for {
		select {
		case line := <-taillog.ReadChan():
			{
				// 2.发送到kafka
				kafka.SendToKafka(cfg.Topic, line.Text)
			}
		default:
			time.Sleep(1 * time.Second)
		}
	}
}

func main() {
	// 0. 加载配置文件

	// 方式1
	//cfg, err := ini.Load("./conf/config.ini")
	//if err != nil {
	//	fmt.Printf("Fail to read file: %v", err)
	//	os.Exit(1)
	//}
	//
	//// 典型读取操作，默认分区可以使用空字符串表示
	//fmt.Println("kafka address:", cfg.Section("kafka").Key("address").String())
	//fmt.Println("kafka topic:", cfg.Section("kafka").Key("topic").String())
	//fmt.Println("taillog path:", cfg.Section("taillog").Key("path").String())

	// 方式2（传一个可修改的指针）
	err := ini.MapTo(&cfg, "./conf/config.ini")
	if err != nil {
		fmt.Printf("load ini failed, err: %v \n", err)
		return
	}
	fmt.Println("读取到的配置信息", cfg)

	// 1. 初始化kafka连接
	address := []string{cfg.Address}
	topic := cfg.Topic
	err = kafka.Init(address, topic)
	if err != nil {
		fmt.Printf("init Kafka failed, err:%v \n", err)
		return
	}
	// 2. 打开日志文件，准备收集
	err = taillog.Init(cfg.FileName)
	if err != nil {
		fmt.Printf("Init taillog failed, err: %v \n", err)
		return
	}
	// 3.执行业务逻辑
	run()
}