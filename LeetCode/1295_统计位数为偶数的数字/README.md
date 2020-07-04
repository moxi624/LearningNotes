# 统计位数为偶数的数组

## 题目

https://leetcode-cn.com/problems/find-numbers-with-even-number-of-digits/

## 描述

给你一个整数数组 nums，请你返回其中位数为 偶数 的数字的个数。

```
示例 1：
输入：nums = [12,345,2,6,7896]
输出：2
解释：
12 是 2 位数字（位数为偶数） 
345 是 3 位数字（位数为奇数）  
2 是 1 位数字（位数为奇数） 
6 是 1 位数字 位数为奇数） 
7896 是 4 位数字（位数为偶数）  
因此只有 12 和 7896 是位数为偶数的数字

示例 2：
输入：nums = [555,901,482,1771]
输出：1 
解释： 
只有 1771 是位数为偶数的数字。
```

## 代码

用到了转换成字符串求长度的方法

```
class Solution(object):
    def findNumbers(self, nums):
        ret = 0
        for i in nums:
            if len(str(i)) % 2 ==0:
                ret += 1
        return ret
```

