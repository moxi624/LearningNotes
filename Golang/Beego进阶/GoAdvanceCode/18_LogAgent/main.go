package main

import (
	"LogDemo/Utils"
	"LogDemo/conf"
	"LogDemo/etcd"
	"LogDemo/kafka"
	"LogDemo/taillog"
	"fmt"
	"gopkg.in/ini.v1"
	"sync"
	"time"
)

var (
	cfg = new(conf.AppConf)
)

func main() {

	// 加载配置文件
	err := ini.MapTo(&cfg, "./conf/config.ini")
	if err != nil {
		fmt.Printf("load ini failed, err: %v \n", err)
		return
	}
	fmt.Println("读取到的配置信息", cfg)

	// 1. 初始化kafka连接
	address := []string{cfg.KafkaConf.Address}
	topic := cfg.Topic
	err = kafka.Init(address, topic, cfg.ChanMaxSize)
	if err != nil {
		fmt.Printf("init Kafka failed, err:%v \n", err)
		return
	}
	fmt.Println("init kafka success.")

	// 初始化etcd
	err = etcd.Init(cfg.EtcdConf.Address, time.Duration(cfg.EtcdConf.Timeout) * time.Second)
	if err != nil {
		fmt.Println("init etcd failed, err:%v \n", err)
		return
	}

	// 为了实现每个logagent都拉取自己独有的配置，所以要以自己的IP地址作为区分
	ipStr, err := Utils.GetOutboundIP()
	if err != nil {
		panic(err)
	}
	etcdConfKey := fmt.Sprintf(cfg.EtcdConf.Key, ipStr)

	// 从etcd中获取日志收集项的配置信息
	logEntryConf, err := etcd.GetConf(etcdConfKey)
	if err != nil {
		fmt.Println("etcd.GetConf failed, err:%v \n", err)
		return
	}
	fmt.Printf("get conf from etcd success, %v \n", logEntryConf)

	// 派一个哨兵去监视日志收集项的变化（有变化及时通知我的logAgent的热加载配置）

	// 打印出配置
	for index, value := range logEntryConf {
		fmt.Printf("index:%v, value:%v \n", index, value)
	}

	// 收集日志，发往kafka中【因为NewConfChan访问了tskMgr的NewConfChan，这个channel是在初始化完成时才执行初始化】
	taillog.Init(logEntryConf)

	// 从taillog包中获取对外暴露的通道
	newConfChan := taillog.NewConf()

	// 获取一个等待组
	var wg sync.WaitGroup
	wg.Add(1)
	// 哨兵发现最新的配置信息会通知上面的那个通道
	go etcd.WatchConf(etcdConfKey, newConfChan)
	wg.Wait()
}