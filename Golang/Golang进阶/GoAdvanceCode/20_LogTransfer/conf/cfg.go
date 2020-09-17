package conf

type LogTransferCfg struct {
	KafkaCfg `ini:"kafka"` // 这个对应ini文件中的 [kafka]
	EsCfg `ini:"es"` // 这个对应ini文件中的 [es]
}

// Kafka配置类
type KafkaCfg struct {
	Address string `ini:"address"`
	Topic string `ini:"topic"`
}

// Es配置类
type EsCfg struct {
	Address string `ini:"address"`
}