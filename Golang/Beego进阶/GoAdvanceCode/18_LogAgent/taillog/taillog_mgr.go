package taillog

import (
	"LogDemo/etcd"
	"fmt"
	"time"
)

var tskMgr *tailLogMgr

// 专门用来管理taillog的，tailTask管理者
type tailLogMgr struct {
	logEntry [] *etcd.LogEntry
	// 有多少干活的TailTask，就记录起来
	tskMap map[string]*TailTask
	// 等待最新配置过来的通道
	newConfChan  chan []*etcd.LogEntry
}

// 初始化方法
func Init(logEntryConf []*etcd.LogEntry)  {

	tskMgr = &tailLogMgr{
		//  把当前的日志收集项配置信息保存起来
		logEntry: logEntryConf,
		tskMap: make(map[string]*TailTask, 16),
		newConfChan: make(chan []*etcd.LogEntry), // 无缓冲区的通道
	}
	for _, logEntry := range logEntryConf {
		// conf是一个LogEntry结构体
		// logEntry.Path：要收集的日志的路径
		tailObj := NewTailTask(logEntry.Path, logEntry.Topic)

		// 初始化的时候，起了多少tailtask都要记下来，为了后续判断方便
		mKey := fmt.Sprintf("%s_%s", logEntry.Path, logEntry.Topic)
		tskMgr.tskMap[mKey] = tailObj
	}

	// 从NewConfChan中获取最新配置
	go tskMgr.run()
}

// 监听自己的newConfChan，有了新的配置过来之后，就做处理
// 1.配置新增、2.配置删除、3.配置变更
func (t *tailLogMgr)run()  {
	for {
	    select{
	    case newConf := <- t.newConfChan:
	    	// 1.配置新增、2.配置删除、3.配置变更
			fmt.Println("新的配置来了:", newConf)
			for _, conf := range newConf {
				// 从tskMap中判断是否有该配置
				mKey := fmt.Sprintf("%s_%s", conf.Path, conf.Topic)
				_, ok := t.tskMap[mKey]
				if ok {
					// 原来就有，不需要操作
					continue
				} else {
					// 如果不存在，说明是一个新增的操作
					tailObj := NewTailTask(conf.Path, conf.Topic)
					tskMgr.tskMap[mKey] = tailObj
				}
			}

			// 找出原来t.logEntry有，但是newConf中没有，要删掉
			for _, c1 := range t.logEntry { // 从原来的配置中依次拿出配置项
				isDelete := true
				for _, c2 := range newConf { // 去新的配置中逐一进行比较
					// 判断原来有，现在是否有，那么什么都不做
					if c2.Path == c1.Path && c2.Topic == c1.Topic {
						isDelete = false
						continue;
					}
				}
				if isDelete {
					// 把c1对应的这个tailObj给停掉
					mk := fmt.Sprintf("%s_%s", c1.Path, c1.Topic)

					// t.tskMap[mk] ==> tailObj
					// 调用tailObj中的退出方法
					t.tskMap[mk].cancelFunc()
				}
			}


		default:
			time.Sleep(time.Second)
		}
	}
}

// 向往暴露一个函数，获取tskMgr的newConfChan
func NewConf() chan<- [] *etcd.LogEntry  {
	return tskMgr.newConfChan
}