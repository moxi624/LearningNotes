# 统计有序矩阵中的负数

## 来源

## 描述

给你一个 `m * n` 的矩阵 `grid`，矩阵中的元素无论是按行还是按列，都以非递增顺序排列。 

请你统计并返回 `grid` 中 **负数** 的数目

```
示例 1：
输入：grid = [[4,3,2,-1],[3,2,1,-1],[1,1,-1,-2],[-1,-1,-2,-3]]
输出：8
解释：矩阵中共有 8 个负数。

示例 2：
输入：grid = [[3,2],[1,0]]
输出：0

示例 3：
输入：grid = [[1,-1],[-1,-1]]
输出：3

示例 4：
输入：grid = [[-1]]
输出：1
```



## 代码

最简单的就是暴力破解，但是这个因为有序，所以当我们找到是负数的时候，直接后面可以不判断了

```
class Solution(object):
    def countNegatives(self, grid):
        count = 0
        for i in range(len(grid)):
            for j in range(len(grid[0])):
                if grid[i][j] < 0:
                    count += len(grid[0]) - j
                    break
        return count
```

当然因为是不递增的，我们还可以想到使用二分查找法，时间复杂度是 O(logn)

```
class Solution(object):
    def getIndex(self, line):
        lineLen = len(line)
        left = 0
        right = lineLen - 1

        while left <= right:
            mid = (left + right) // 2
            if line[mid] < 0 and ((mid != 0 and line[mid -1] >= 0) or (mid == 0)):
                return lineLen - mid
            elif line[mid] < 0:
                right = mid -1
            else:
                left = mid + 1
        return 0

    def countNegatives(self, grid):
        count = 0
        for i in range(len(grid)):        
            count += self.getIndex(grid[i])
        return count
```

