# 一个整型数组里除了两个数字之外，其他的数字都出现了两次。请写程序找出这两个只出现一次的数字。
class Solution:
    # 返回[a,b] 其中ab是出现一次的两个数字
    def FindNumsAppearOnce(self, array):
        dict = {}
        for i in range(len(array)):
            if dict.get(array[i]) != None:
                del dict[array[i]]
            else:
                dict[array[i]] = 1
        if len(dict) == 0:
            return None
        list = []
        for key in dict:
            list.append(key)
        return list[0], list[1]
if __name__ == '__main__':
    array = [2,4,3,6,3,2,5,5]
    print(Solution().FindNumsAppearOnce(array))