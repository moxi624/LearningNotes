# 旋转数组

## 来源

https://leetcode-cn.com/problems/rotate-array/

## 描述

给定一个数组，将数组中的元素向右移动 *k* 个位置，其中 *k* 是非负数。

```
示例 1:
输入: [1,2,3,4,5,6,7] 和 k = 3
输出: [5,6,7,1,2,3,4]
解释:
向右旋转 1 步: [7,1,2,3,4,5,6]
向右旋转 2 步: [6,7,1,2,3,4,5]
向右旋转 3 步: [5,6,7,1,2,3,4]

示例 2:
输入: [-1,-100,3,99] 和 k = 2
输出: [3,99,-1,-100]
解释: 
向右旋转 1 步: [99,-1,-100,3]
向右旋转 2 步: [3,99,-1,-100]

说明:
尽可能想出更多的解决方案，至少有三种不同的方法可以解决这个问题。
要求使用空间复杂度为 O(1) 的 原地 算法。
```

## 解法1

解法1就是需要重新创建一个数组，然后通过模运算得到角标，在填入到新数组中

```python
class Solution(object):
    def rotate(self, nums, k):
        arrLen = len(nums)
        if k == arrLen:
            return nums
        arr=[0 for i in range(arrLen)]
        for index in range(arrLen):
            newIndex = (index + k) % arrLen
            arr[newIndex] = nums[index]
        return arr
```

## 解决2

解法2就不需要创建数组了，而是直接在原来数组上进行出列和插入操作。这样可以保证空间复杂度为 O(1)

```python
class Solution(object):
    def rotate(self, nums, k):
        arrLen = len(nums)
        if k == arrLen:
            return nums
        k = k % arrLen
        for index in range(k):
            nums.insert(0, nums.pop())
        return nums
```

## 解法3

解法3就是截取数组，然后进行拼接

```python
class Solution:
    def rotate(self, nums: List[int], k: int) -> None:
        n = len(nums)
        k %= n
        nums[:] = nums[-k:] + nums[:-k]
```

