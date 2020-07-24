package interfaceDemo

import "fmt"

// 如果接口里面有方法的话，必须要通过结构体或自定义类型实现这个接口

// 使用相机结构体来实现 接口
type Camera struct {
	Name string
}
// 相机要实现Usber接口的话，必须实现usb接口的所有方法
func (p Camera) start()  {
	fmt.Println(p.Name, "启动")
}
func (p Camera) stop()  {
	fmt.Println(p.Name, "关闭")
}
func (p Camera)Start() {
	var camera Usber = Camera{
		"佳能",
	}
	camera.start()
	camera.stop()
}
