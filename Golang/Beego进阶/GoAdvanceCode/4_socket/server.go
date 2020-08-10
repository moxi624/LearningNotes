package main

import (
	"fmt"
	"net"
)

func main() {
	// 本地端口启动服务
	listen, err := net.Listen("tcp", "127.0.0.1:20000")
	if err!= nil {
		fmt.Println("start server on failed ", err)
	}
	// 等待别人来建立连接
	conn, err := listen.Accept()
	if err != nil {
		fmt.Println("accept failed, err: ", err)
		return
	}
	// 与客户端通信
	var tmp [128]byte
	n, err := conn.Read(tmp[:])
	if err != nil {
		fmt.Println("read from conn failed, err:", err)
		return
	}
	fmt.Println(string(tmp[:n]))
}