package main

import (
	"reflect"
	"testing"
)

func TestSplit(t *testing.T) {
	ret := Split("abc", "b")
	want := []string{"a", "c"}
	// 判断期望结果和最后结果是否相同
	if !reflect.DeepEqual(ret, want) {
		// 测试用例失败了
		t.Error("want: %v but got: %v", want, ret)
	}

}
