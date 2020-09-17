package main

import (
	"fmt"
	"reflect"
)

// ini配置文件解析器

// MysqlConfig MySQL配置结构体
type MysqlConfig struct {
	Address string `ini:"address"`
	Port int `ini:"port"`
	Username string `ini:"username"`
	Password string `ini:"password"`
}

func loadIni(x interface{})  {
	v := reflect.ValueOf(x)
	fmt.Println(v)
}

func main() {
	var mc MysqlConfig
	loadIni(&mc)
	fmt.Println(mc.Address, mc.Port, mc.Username, mc.Password)
}
