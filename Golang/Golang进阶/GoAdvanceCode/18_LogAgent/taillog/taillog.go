package taillog

import (
	"LogDemo/kafka"
	"context"
	"fmt"
	"github.com/hpcloud/tail"
)

// TailTask：具体的一个日志收集的任务
type TailTask struct {
	// 收集的哪一个路径下的日志
	path string
	// 收集的日志要放到哪个topic下面
	topic string
	// 具体的tailObj对象
	instance *tail.Tail
	// 为了能显示退出 t.run()
	ctx context.Context
	cancelFunc context.CancelFunc
}

// 初始化TailTask
func NewTailTask(path string, topic string)(tailObj *TailTask)  {
	ctx, cancel := context.WithCancel(context.Background())
	tailObj = &TailTask{
		path: path,
		topic: topic,
		ctx: ctx,
		cancelFunc: cancel,
	}
	// 根据路径去打开对应的日志
	tailObj.init()
	return
}

func (t *TailTask)init()() {

	// 定义配置文件
	config := tail.Config{
		ReOpen: true, // 重新打开，日志文件到了一定大小，就会分裂
		Follow: true, // 是否跟随
		Location: &tail.SeekInfo{Offset: 0, Whence: 2}, // 从文件的哪个位置开始读
		MustExist: false, // 是否必须存在，如果不存在是否报错
		Poll: true, //
	}
	var err error
	t.instance, err = tail.TailFile(t.path, config)
	if err != nil {
		fmt.Println("tail file failed, err:", err)
	}
	// 开启goroutine 去采集日志发送到kafka中【当goroutine执行的函数退出的时候，也就退出了】
	go t.run()
}

// 采集日志
func (t *TailTask) run()  {
	// 从tailObj的通道中一行一行的读取日志数据 【多路复用】
	for {
		select {
		// 当ctx中的Done通道中，有内容时，代表需要退出了
		case <- t.ctx.Done():
			fmt.Printf("tail task: %s_%s 结束了.... \n", t.path , t.topic)
			return
		case line := <- t.instance.Lines:
			// 发往数据到kafka中
			// 可以优化，先把日志数据发送到一个通道中，然后在kafka的那个包中，有单独的goroutine去取日志数据发送到kafka中
			kafka.SendToChan(t.topic, line.Text)
		}
	}
}

// 读取日志，返回一个只读的chan
func (t *TailTask)ReadChan() <-chan *tail.Line {
	return t.instance.Lines
}
