package interfaceDemo

// 电脑
type Computer struct {

}

// 接收一个实现了Usber接口的 结构体
func (computer Computer) Startup(usb Usber)  {
	usb.start()
}

// 关闭
func (computer Computer) Shutdown (usb Usber)  {
	usb.stop()
}