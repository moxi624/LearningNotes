#  数组中出现次数超过一半的数字
# 数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。例如输入一个长度为9的数组{1,2,3,2,2,2,5,4,2}。由于数字2在数组中出现了5次，超过数组长度的一半，因此输出2。如果不存在则输出0。
# -*- coding:utf-8 -*-
class Solution:
    def MoreThanHalfNum_Solution(self, numbers):
        numsCount = {}
        numLen = len(numbers)
        for num in numbers:
            if num in numsCount:
                numsCount[num] += 1
            else:
                numsCount[num] = 1
            # 找出超过一半的数
            if numsCount[num] > numLen >> 1:
                return num
        return 0

if __name__ == '__main__':
    print(Solution().MoreThanHalfNum_Solution({1,2,3,2,2,2,5,4,2}))
