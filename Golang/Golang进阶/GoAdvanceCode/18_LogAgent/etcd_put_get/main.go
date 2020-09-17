package main

import (
	"context"
	"fmt"
	"go.etcd.io/etcd/clientv3"
	"time"
)

func main() {
	cli, err := clientv3.New(clientv3.Config {
		Endpoints: []string{"127.0.0.1:2379"}, // etcd的节点，可以传入多个
		DialTimeout: 5*time.Second, // 连接超时时间
	})

	if err != nil {
		fmt.Printf("connect to etcd failed, err: %v \n", err)
		return
	}
	fmt.Println("connect to etcd success")

	// 延迟关闭
	defer cli.Close()

	// put操作  设置1秒超时
	ctx, cancel := context.WithTimeout(context.Background(), time.Second)
	value := `[{"path":"D:/mogu_blog/logs/mysql.log","topic":"web_log"},{"path":"D:/mogu_blog/logs/redis.log","topic":"redis_log"}]`
	_, err = cli.Put(ctx, "/logagent/202.193.57.73/collect_config", value)
	cancel()
	if err != nil {
		fmt.Printf("put to etcd failed, err:%v \n", err)
		return
	}
	fmt.Println("设置成功")
}