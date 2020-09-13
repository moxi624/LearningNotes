package kafka

import (
	"LogTransfer/es"
	"fmt"
	"github.com/Shopify/sarama"
)

type LogData struct {
	data string `json:"data"`
}

// 初始化Kafka消费者，从Kafka取数据发往ES中
func Init(address []string, topic string)(error)  {
	consumer, err := sarama.NewConsumer(address, nil)
	if err != nil {
		fmt.Printf("fail to start consumer, err:%v\n", err)
		return err
	}
	partitionList, err := consumer.Partitions(topic) // 根据topic取到所有的分区
	if err != nil {
		fmt.Printf("fail to get list of partition:err%v\n", err)
		return err
	}
	fmt.Println("分区列表", partitionList)
	for partition := range partitionList { // 遍历所有的分区
		// 针对每个分区创建一个对应的分区消费者
		pc, err := consumer.ConsumePartition(topic, int32(partition), sarama.OffsetNewest)
		if err != nil {
			fmt.Printf("failed to start consumer for partition %d,err:%v\n", partition, err)
			return err
		}

		// 异步从每个分区消费信息
		go func(sarama.PartitionConsumer) {
			for msg := range pc.Messages() {
				// 直接发送到ES中
				ld := map[string]interface{} {
					"data": string(msg.Value),
				}

				if err != nil {
					fmt.Printf("unmarshal failed, err: %v \n", err)
					return
				}
				fmt.Println("向ES中发送数据,", ld)
				// 发送数据到es 【函数调函数，可以使用异步的方式】
				es.SendToES(topic, ld)
				// 优化一下：直接放到一个chan中
			}
		}(pc)
	}
	return err
}