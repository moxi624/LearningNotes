package Utils

import (
	"fmt"
	"net"
	"strings"
)

// 获取本地对外IP
func GetOutboundIP()(ip string, err error)  {
	// 使用udp的方式拨号
	conn, err := net.Dial("udp", "8.8.8.8:80")
	if err != nil {
		fmt.Printf("get ip failed, err: %s, \n", err)
		return
	}
	defer conn.Close()
	localAddr := conn.LocalAddr().(*net.UDPAddr)
	fmt.Println(localAddr.String())
	ip = strings.Split(localAddr.IP.String(), ":")[0]
	return
}
