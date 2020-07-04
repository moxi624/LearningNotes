# 有多少小于当前数字的数字

## 来源

https://leetcode-cn.com/problems/how-many-numbers-are-smaller-than-the-current-number/

## 描述

给你一个数组 nums，对于其中每个元素 nums[i]，请你统计数组中比它小的所有数字的数目。

换而言之，对于每个 nums[i] 你必须计算出有效的 j 的数量，其中 j 满足 j != i 且 nums[j] < nums[i] 。

以数组形式返回答案。

```java
示例 1：
输入：nums = [8,1,2,2,3]
输出：[4,0,1,1,3]
解释： 
对于 nums[0]=8 存在四个比它小的数字：（1，2，2 和 3）。 
对于 nums[1]=1 不存在比它小的数字。
对于 nums[2]=2 存在一个比它小的数字：（1）。 
对于 nums[3]=2 存在一个比它小的数字：（1）。 
对于 nums[4]=3 存在三个比它小的数字：（1，2 和 2）

示例 2：
输入：nums = [6,5,4,8]
输出：[2,1,0,3]

示例 3：
输入：nums = [7,7,7,7]
输出：[0,0,0,0]
```

## 代码

### 方法1

暴力解法，两层for循环完事

```
class Solution(object):
    def smallerNumbersThanCurrent(self, nums):
        ret = []
        for i in range(len(nums)):
            sum = 0
            for j in range(len(nums)):
                if i == j:
                    continue
                if nums[i] > nums[j]:
                    sum += 1
            ret.append(sum)
        return ret
```

### 方法2

使用排序算法，先进行排序，完成后在统计小于的即可，排序后时间复杂度就可以由原来的 O(n^2) 变成 O(n logN)。

```java
class Solution(object):
    def smallerNumbersThanCurrent(self, nums):
        ret = []
        nums2 = sorted(nums)
        for i in nums:
            ret.append(nums2.index(i))
        return ret
```

