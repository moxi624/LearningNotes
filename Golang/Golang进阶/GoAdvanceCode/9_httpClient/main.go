package main

import (
	"fmt"
	"io/ioutil"
	"net/http"
	"net/url"
)

func main() {
	res, err := http.Get("http://127.0.0.1:9090/query?name=zansan&age=10")
	if err != nil {
		fmt.Println(err)
		return
	}

	// 从res中把服务端返回的数据读取出来
	b, err := ioutil.ReadAll(res.Body)
	if err != nil {
		fmt.Println(err)
		return
	}
	fmt.Println(string(b))


	data := url.Values{}
	urlObj, _ := url.Parse("http://127.0.0.1:9090/query")
	data.Set("name", "周林")
	data.Set("age", "100")
	// 对请求进行编码
	queryStr := data.Encode()
	urlObj.RawQuery = queryStr

	req, err := http.NewRequest("GET", urlObj.String(), nil)
	if err != nil {
		fmt.Println(err)
		return
	}
	fmt.Println(req)

}
