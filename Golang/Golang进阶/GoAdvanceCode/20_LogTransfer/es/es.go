package es

import (
	"context"
	"fmt"
	"github.com/olivere/elastic/v7"
	"strings"
)

type LogData struct {
	Topic string `json:"topic"`
	data string `json:"data"`
}

var (
	client *elastic.Client
	ch = make(chan LogData, 100000)
)
// 初始化ES，准备接受Kafka那边发来的数据
func Init(address string)(err error) {
	if !strings.HasPrefix(address, "http://") {
		address = "http://" + address
	}
	// 使用 elastic库中的NewClient方法
	client, err = elastic.NewClient(elastic.SetURL(address))
	if err != nil {
		fmt.Println("connect to es failed, err:", err)
		return
	}
	return
}

// 发送数据到ES中
func SendToES(indexStr string, data interface{}) error  {
	// 构造一条数据
	put1, err := client.Index().
		Index(indexStr).  // 拿到索引库
		BodyJson(data). // 将对象转换成json
		Do(context.Background()) // 插入，同时可以设置context的超时

	if err != nil {
		return err
	}
	fmt.Printf("Indexed web_log %s to index %s, type %s, data: %v \n", put1.Id, put1.Index, put1.Type, data)
	return err
}