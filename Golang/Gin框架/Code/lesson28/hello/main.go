/**
 * @Description
 * @Author 陌溪
 * @Date 2021/4/22 9:17
 **/
package main

import "fmt"

func main() {
	c := make(chan int, 1)
	fmt.Print("end", <-c)
	c <- 2

}
