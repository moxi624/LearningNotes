# 替换空格

## 题目描述

请实现一个函数，将一个字符串中的每个空格替换成“%20”。例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。



## 调用方法实现

```
class Solution:
    # s 源字符串
    def replaceSpace(self, s):
        return s.replace(' ', '%20')
```



## 自己写方法实现Replace

我们将字符串存储在数组中，然后循环比较每个字符的值，当遇到空格的时候，替换即可

```
class Solution:
    def replaceSpace2(self, s):
        strLen = len(s)
        aaa = []
        for i in range(0, strLen):
            if s[i] == " ":
                aaa.append("%")
                aaa.append("2")
                aaa.append("0")
            else:
                aaa.append(s[i])
        return ''.join(aaa)

if __name__ == '__main__':
    print(Solution().replaceSpace2('We Are Happy'))
```

