# 二分查找
class Solution:
    def binarySearch(self, array, key):
        left = 0
        right = len(array)

        while(left < right):
            middle = int((right + left) / 2)
            if array[middle] == key:
                return middle
            elif array[middle] > key:
                right = middle - 1
            else:
                left = middle + 1
        return -1

if __name__ == '__main__':
    print(Solution().binarySearch([1,3,5,6,8,9], 10))