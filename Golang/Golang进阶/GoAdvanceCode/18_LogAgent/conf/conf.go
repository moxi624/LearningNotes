package conf

type AppConf struct {
	KafkaConf `ini:"kafka"`
	EtcdConf `ini:"etcd"`
}
type KafkaConf struct {
	Address     string `ini:"address"`
	Topic       string `ini:"topic"`
	ChanMaxSize int    `ini:"chan_max_size"`
}
// etcd配置
type EtcdConf struct {
	Address string `ini:"address"`
	Key string `ini:"collect_log_key"`
	Timeout int `ini:"timeout"`
}
