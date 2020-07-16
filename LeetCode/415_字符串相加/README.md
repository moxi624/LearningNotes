# 字符串相加

## 来源

https://leetcode-cn.com/problems/add-strings/

## 描述

给定两个字符串形式的非负整数 num1 和 num2，计算他们的和

注意：

num1 和num2 的长度都小于 5100.

num1 和num2 都只包含数字 0-9.

num1 和num2 都不包含任何前导零。

你不能使用任何內建 BigInteger 库， 也不能直接将输入的字符串转换为整数形式

## 代码

我们首先将其转换成字符串数组，然后从末尾开始计算，执行加法操作，同时设置一个进位标志

```python
class Solution(object):
    def addStrings(self, num1, num2):
        """
        :type num1: str
        :type num2: str
        :rtype: str
        """
        list1 = []
        list2 = []
        for i in num1:
            list1.append(i)
        for i in num2:
            list2.append(i)

        len1 = len(list1)
        len2 = len(list2)

        count = len1 - len2
        if count > 0:
            for i in range(count):
                list2.insert(0, 0)
        elif count < 0:
            for i in range(-count):
                list1.insert(0, 0)

        # 进位
        flag = 0
        ret = []
        while list1 and list2:
            params1 = int(list1.pop())
            params2 = int(list2.pop())
            sum = params1 + params2 + flag
            if sum >= 10:
                flag = 1
                sum = sum - 10
            else:
                flag = 0

            ret.insert(0, str(sum))

        if flag == 1:
            ret.insert(0, '1')

        return ''.join(ret)
```

