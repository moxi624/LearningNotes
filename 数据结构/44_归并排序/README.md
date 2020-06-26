# 归并排序

## 题目

- [把数组排成最小的数](https://www.nowcoder.com/practice/8fecd3f8ba334add803bf2a06af1b993)
- [数组中的逆序对](https://www.nowcoder.com/practice/96bd6684e04a44eb80e6a68efc0ec6c5)

## 算法描述

归并排序是用到了分治的思想，分治的思想是将一个大问题拆分成很多歌小问题，然后再将已经处理完成的小问题合并成整个大问题，在这个过程中。在这个过程中，大问题就得到了解决，在Hadoop中MapReduce就是利用了这个思想。

- 把长度为n的输入序列分成两个长度为n/2的子序列；
- 对这两个子序列分别采用归并排序；
- 将两个排序好的子序列合并成一个最终的排序序列。

## 步骤

![img](images/1551078679377.gif)

## 代码

代码分为两部分，一个是分治，一个合并

```python
# 归并排序
class Solution:
    def MergeSort(self, arrayList):
        arrayLen = len(arrayList)
        # 判断输入参数的正确性
        if arrayLen < 1:
            return []
        # 归并的出口是当分解到长度为1的时候
        if arrayLen == 1:
            return arrayList
        # 获取中间索引值
        middleIndex = arrayLen >> 1
        # 递归左边部分
        sortedLeft = self.MergeSort(arrayList[:middleIndex])
        # 递归右边部分
        sortedRight = self.MergeSort(arrayList[middleIndex:])
        # 合并代码
        return self.MergeCore(sortedLeft, sortedRight)

    def MergeCore(self, leftList, rightList):
        # leftIndex = 0
        # rightIndex = 0
        # # 由于多次用，length 我们自己保存
        # leftLen = len(leftList)
        # rightLen = len(rightList)
        retList = []

        while leftList != [] and rightList != []:
            leftValue = leftList[0]
            rightValue = rightList[0]
            if leftValue >= rightValue:
                retList.append(rightValue)
                del rightList[0]
            else:
                retList.append(leftValue)
                del leftList[0]

        # 归并排序后，然后
        if leftList != []:
            for i in leftList:
                retList.append(i)
        if rightList != []:
            for i in rightList:
                retList.append(i)
        return retList

if __name__ == '__main__':
    print(Solution().MergeSort([1,8,7,5,4,2]))
```

