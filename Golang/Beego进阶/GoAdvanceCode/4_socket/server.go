package main

import (
	"fmt"
	"net"
)

// 用于接收请求的方法
func processConn(conn net.Conn)  {
	// 与客户端通信
	var tmp [128]byte
	// 使用for循环监听消息
	for {
		n, err := conn.Read(tmp[:])
		if err != nil {
			fmt.Println("read from conn failed, err:", err)
			return
		}
		fmt.Println(conn, string(tmp[:n]))
	}
}
func main() {
	// 本地端口启动服务
	listen, err := net.Listen("tcp", "127.0.0.1:20000")
	if err!= nil {
		fmt.Println("start server on failed ", err)
	}

	// for循环监听
	for {
		// 等待别人来建立连接
		conn, err := listen.Accept()
		if err != nil {
			fmt.Println("accept failed, err: ", err)
			return
		}
		go processConn(conn)
	}
}