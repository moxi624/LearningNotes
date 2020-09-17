package main

import (
	"LogTransfer/conf"
	"LogTransfer/es"
	"LogTransfer/kafka"
	"fmt"
	"gopkg.in/ini.v1"
)

// LogTransfer
// 将日志数据从Kafka中取出来，发往ElasticSearch
func main() {
	// 加载配置文件
	var cfg conf.LogTransferCfg
	// 因为go里面修改是需要传递引用地址，所以需要用 &cfg
	// 需要注意：在一个函数修改变量的时候，一定要传递一个指针
	// 与配置文件对应的结构体中，一定要设置tag（特别是嵌套结构体）
	err := ini.MapTo(&cfg, "./conf/cfg.ini")
	if err != nil {
		fmt.Println("init config failed, err:", err)
		return
	}
	fmt.Printf("cfg: %v \n", cfg)

	// 初始化ES
	// ES做的事情：初始化一个ES连接的client
	// 对外提供一个往ES中写入数据的一个函数
	err = es.Init(cfg.EsCfg.Address)
	if err != nil {
		fmt.Printf("init ElasticSearch failed, err: %v \n", err)
		return
	}
	fmt.Println("init ElasticSearch Success")

	// 初始化Kafka
	//做的事情：连接kafka ->  创建分区的消费者 -> 每个分区的消费者分别取出数据，通过SendToES()将数据发往ES
	err = kafka.Init([]string{cfg.KafkaCfg.Address}, cfg.KafkaCfg.Topic)
	if err != nil {
		fmt.Printf("init kafka consumer failed, err: %v \n", err)
		return
	}
	fmt.Println("init Kafka Success")

	select {

	}


	// 从Kafka中取日志数据

	// 发往ElasticSearch
}
