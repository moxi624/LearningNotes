# 希尔排序
class Solution:
    def shellSort(self, arrlist):
        arrayLen = len(arrlist)
        h = 1
        # 工业界认定的求 增量的方法
        while h < arrayLen/3:
            h = h*3 + 1
        # 插入排序的方法，判断是不是后面一个比前面的要小
        # 如果是则交换
        while h >= 1:
            for i in range(h, arrayLen):
                j = i
                while j >= h and arrlist[j] < arrlist[j - h]:
                    arrlist[j], arrlist[j-h] = arrlist[j-h], arrlist[j]
                    j-=h
            # 除以3
            h = h//3
        return arrlist

if __name__ == '__main__':
    print(Solution().shellSort([1,8,3,2,6,9]))