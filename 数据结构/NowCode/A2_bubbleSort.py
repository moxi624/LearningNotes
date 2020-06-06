# 冒泡排序
class Solution:
    def bubbleSort(self, array):
        for i in range(len(array)):
            flag = False
            for j in range(i, len(array) - 1):
                if array[j] > array[j+1]:
                    temp = array[j+1]
                    array[j+1] = array[j]
                    array[j] = temp
                    flag = True
            if not flag:
                break
        return array

if __name__ == '__main__':
    print(Solution().bubbleSort([1,6,4,3,9,8]))