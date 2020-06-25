# 连续子数组的最大和
class Solution:
    # def FindGreatestSumOfSubArray(self, array):
    #     # write code here
    #     maxSum = -99999
    #     for i in range(len(array)):
    #         sum = 0
    #         for j in range(i, len(array)):
    #             sum += array[j]
    #             if sum > maxSum:
    #                 maxSum = sum
    #     return maxSum

    def FindGreatestSumOfSubArray(self, array):
        # write code here
        maxNum = None
        tmpNum = 0
        for i in array:
            if maxNum == None:
                maxNum = i
            if tmpNum + i < i:
                tmpNum = i
            else:
                tmpNum += i
            if maxNum < tmpNum:
                maxNum = tmpNum
        return maxNum

if __name__ == '__main__':
    print(Solution().FindGreatestSumOfSubArray([1,-2,3,10,-4,7,2,-5]))