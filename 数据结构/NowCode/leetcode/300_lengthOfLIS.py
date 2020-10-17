# 最长上升子序列
class Solution(object):
    def lengthOfLIS(self, nums):
        if nums == []:
            return 0
        status = [1 for _ in range(len(nums))]
        for i in range(1, len(nums)):
            max = 1
            for j in range(i):
                if nums[j] < nums[i] and status[j] >= max:
                    max = status[j] + 1
            status[i] = max
        result = 1
        for i in status:
            if i > result:
                result = i
        return result

if __name__ == '__main__':
    print(Solution().lengthOfLIS([10,9,2,5,3,7,101,18]))