# -*- coding:utf-8 -*-
array = []
class Solution:

    def Insert(self, num):
        global array
        # write code here
        array.append(num)
    def GetMedian(self):
        # write code here
        global array
        if array == []:
            return None
        for i in range(len(array)):
            flag = False
            for j in range(len(array) - 1 - i):
                if array[j] > array[j+1]:
                    temp = array[j+1]
                    array[j+1] = array[j]
                    array[j] = temp
                    flag = True
            if not flag:
                break
        mid = len(array) /2
        return array[mid]
if __name__ == '__main__':
    Solution().Insert(1)
    Solution().Insert(5)
    Solution().Insert(4)
    Solution().Insert(3)
    Solution().Insert(2)
    Solution().GetMedian()