# 将每个元素替换为右侧最大的元素

## 来源

https://leetcode-cn.com/problems/replace-elements-with-greatest-element-on-right-side/

## 描述

给你一个数组 arr ，请你将每个元素用它右边最大的元素替换，如果是最后一个元素，用 -1 替换。

完成所有替换操作后，请你返回这个数组。

```
示例：

输入：arr = [17,18,5,4,6,1]
输出：[18,6,6,6,1,-1]
```

## 代码

我们就是从右到左遍历数组，同时使用

```python
class Solution(object):
    def replaceElements(self, arr):
        max = -1
        arrLen = len(arr)
        ret = [0 for i in range(arrLen)]
        ret[-1] = -1
        for i in range(arrLen - 1, 0, -1):
            if arr[i] > max:
                max = arr[i]
            ret[i-1] = max
        return ret
```

