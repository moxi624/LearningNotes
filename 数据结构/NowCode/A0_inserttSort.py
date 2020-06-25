# 插入排序
class Solution:
    def insertSort(self, alist):
        n = len(alist)
        for j in range(0, n):
            for i in range(j, 0, -1):
                if alist[i] < alist[i - 1]:
                    alist[i], alist[i - 1] = alist[i - 1], alist[i]
                else:
                    break
        return alist


if __name__ == '__main__':
    print(Solution().insertSort([1,8,3,2,6,9]))