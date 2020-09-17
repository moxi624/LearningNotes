package kafka

import (
	"fmt"
	"github.com/Shopify/sarama"
)

// 专门往kafka写日志的模块

var (
	// 声明一个全局连接kafka的生产者
	client sarama.SyncProducer
)

// 初始化client
func Init(address []string, topic string)(err error)  {
	config := sarama.NewConfig()

	// tailf包使用，发送完数据需要 leader 和 follow都确定
	config.Producer.RequiredAcks = sarama.WaitForAll
	// 新选出一个partition
	config.Producer.Partitioner = sarama.NewRandomPartitioner
	// 成功交付的消息将在 success channel返回
	config.Producer.Return.Successes = true

	msg := &sarama.ProducerMessage{}
	msg.Topic = topic
	msg.Value = sarama.StringEncoder("this is a test log")
	// 连接kafka，可以连接一个集群
	client, err = sarama.NewSyncProducer(address, config)
	if err != nil {
		fmt.Println("producer closed, err: ", err)
	}
	fmt.Println("Kafka初始化成功")
	return err
}

// 发送消息到Kafka
func SendToKafka(topic, data string) {
	msg := &sarama.ProducerMessage{}
	msg.Topic = topic
	msg.Value = sarama.StringEncoder(data)
	// 发送到kafka
	pid, offset, err := client.SendMessage(msg)
	if err != nil {
		fmt.Println("send msg failed, err:", err)
		return
	}
	fmt.Println("发送消息：", data)
	fmt.Printf("发送成功~  pid:%v offset:%v \n", pid, offset)
}