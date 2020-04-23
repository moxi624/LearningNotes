# 二维数组查找
# 在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，
# 每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
class Solution:
    # 暴力算法
    def Find(self, target, array):
        for i in range(len(array)):
            for j in range(len(array)):
                if target == array[i][j]:
                    return True
        return False

    def Find2(self, target, array):
        if any(array) == 0:
            return False
        for i in range(len(array)):
            # 判断最后一位是否比target更大
            if array[i][len(array) - 1] >= target:
                for j in range(len(array)):
                    if target == array[i][j]:
                        return True
        return False

if __name__ == '__main__':
    print(Solution().Find(10))