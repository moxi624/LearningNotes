class Solution(object):
    map = {}
    # 使用 nums[0 ... index]，是否可以完全填充一个容量为sum的背包
    def tryPartition(self, nums, index, sum):
        if sum == 0:
            return True
        if sum < 0 or index < 0:
            return False
        if self.map.get(str(index) + "_" + str(sum)) != None:
            return self.map.get(str(index) + "_" + str(sum))

        result = self.tryPartition(nums, index - 1, sum) or self.tryPartition(nums, index - 1, sum - nums[index])
        self.map[str(index) + "_" + str(sum)] = result
        return result

    def canPartition(self, nums):
        sum = 0
        for i in nums:
            sum += i
        if sum % 2 != 0:
            return False
        return self.tryPartition(nums, len(nums) -1, sum/2)

if __name__ == '__main__':
    print(Solution().canPartition([2,2,3,5]))
