package kafka

import (
	"fmt"
	"github.com/Shopify/sarama"
	"time"
)

// 专门往kafka写日志的模块

type logData struct {
	topic string
	data string
}

var (
	// 声明一个全局连接kafka的生产者
	client sarama.SyncProducer
	// 全局的用于存放日志的chan
	logDataChan chan *logData
)

// 初始化client
func Init(address []string, topic string, maxSize int)(err error)  {
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
		return err
	}
	// 初始化全局的chan，用来指定最大的大小
	logDataChan = make(chan *logData, maxSize)

	// 开启协程，从logDataChan等待数据过来，然后发送到kafka
	go sendToKafka()
	fmt.Println("Kafka初始化成功")
	return err
}

// 给外部暴露的一个函数，该函数只把日志数据发送到一个内部的channel中
func SendToChan(topic, data string)  {
	msg := &logData{
		topic: topic,
		data: data,
	}
	logDataChan <- msg
}

// 发送消息到Kafka
func sendToKafka() {
	for {
		select {
		case ld := <-logDataChan: {
			msg := &sarama.ProducerMessage{}
			msg.Topic = ld.topic
			msg.Value = sarama.StringEncoder(ld.data)
			// 发送到kafka
			pid, offset, err := client.SendMessage(msg)
			if err != nil {
				fmt.Println("send msg failed, err:", err)
				return
			}
			fmt.Printf("message: %v , pid:%v , offset:%v \n", ld, pid, offset)
		}
		default:
			time.Sleep(time.Millisecond * 50)
		}
	}
}