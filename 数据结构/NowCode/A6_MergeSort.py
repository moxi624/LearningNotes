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