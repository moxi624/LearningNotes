package interfaceDemo

import "fmt"

// 如果接口里面有方法的话，必须要通过结构体或自定义类型实现这个接口

// 使用结构体来实现 接口
type Phone struct {
	Name string
}
// 手机要实现Usber接口的话，必须实现usb接口的所有方法
func (p Phone) start()  {
	fmt.Println(p.Name, "启动")
}
func (p Phone) stop()  {
	fmt.Println(p.Name, "关闭")
}
func main() {
	var phone Usber = Phone{
		"三星手机",
	}
	phone.start()
	phone.stop()
}
