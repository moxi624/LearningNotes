# 插入排序
class Solution:
    def insertSort(self, array):
        for i in range(len(array)):
            minIndex = i
            for j in range(i, len(array)):
                if array[minIndex] > array[j]:
                    minIndex = j
            temp = array[minIndex]
            array[minIndex] = array[i]
            array[i] = temp
        return array
if __name__ == '__main__':
    print(Solution().insertSort([1,8,3,2,6,9]))