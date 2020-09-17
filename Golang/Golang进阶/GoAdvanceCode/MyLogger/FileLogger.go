package main

// 往文件里面写日志相关的代码
type FileLogger struct {
	Level LogLevel
	filePath string  // 日志文件保存的路径
	fileName string  // 日志文件保存的文件名
	maxFileSize int64  // 最大的文件大小
}

func NewFileLogger(levelStr, fp, fn string, maxSize int64)(*FileLogger)  {
	LogLevel, err := parseLogLevel(levelStr)
	if err != nil {
		panic(err)
	}
	return &FileLogger{
		Level: LogLevel,
		filePath: fp,
		fileName: fn,
		maxFileSize: maxSize,
	}
}
