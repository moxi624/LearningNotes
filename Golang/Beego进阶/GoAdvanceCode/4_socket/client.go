package main

import "net"

// tcp client
func main() {
	// 与server端建立连接
	net.Dial("127.0.0.1:3306")
}