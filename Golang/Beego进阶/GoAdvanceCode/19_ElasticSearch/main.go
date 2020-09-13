package main

import (
	"context"
	"fmt"
	"github.com/olivere/elastic/v7"
)

// 构造一个student结构体

type Student struct {
	Name    string `json:"name"`
	Age     int    `json:"age"`
	Married bool   `json:"married"`
}

func (s *Student)run() *Student  {
	fmt.Printf("%s在跑...", s.Name)
	return s
}

func (s *Student)play()*Student  {
	fmt.Printf("%s在玩...", s.Name)
	return s
}

func main() {

	moxi := Student{
		Name: "陌溪",
		Age: 9000,
		Married: true,
	}
	moxi.run()
	moxi.run()
	moxi.play().run()


	// 使用 elastic库中的NewClient方法
	client, err := elastic.NewClient(elastic.SetURL("http://127.0.0.1:9200"))
	if err != nil {
		// 抛出异常
		panic(err)
	}
	fmt.Println("connect to es success")
	// 构造一条数据
	p1 := Student{Name: "rion", Age: 22, Married: false}
	put1, err := client.Index().
		Index("user").  // 拿到索引库
		BodyJson(p1). // 将对象转换成json
		Do(context.Background()) // 插入，同时可以设置context的超时

	if err != nil {
		// Handle error
		panic(err)
	}
	fmt.Printf("Indexed student %s to index %s, type %s\n", put1.Id, put1.Index, put1.Type)
}