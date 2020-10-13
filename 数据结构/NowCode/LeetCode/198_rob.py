class Solution(object):
    # map.get(index): 表示考虑抢劫 nums[i ... n] 所能获得的最大收益
    map = {}
    # 考虑抢劫 nums[index... nums.size] 这个范围的所有房子
    def tryRob(self, nums, index):
        if index >= len(nums):
            return 0
        if self.map.get(index) != None:
            return self.map.get(index)
        res = 0
        for i in range(index, len(nums)):
            res = max(nums[i] + self.tryRob(nums, i+2), res)
        self.map[index] = res
        return res

    def rob(self, nums):
        return self.tryRob(nums, 0)

    def rob2(self, nums):
        n = len(nums)
        if n == 0:
            return 0

        self.map[n - 1] = nums[n -1]
        for i in range(n-2, 0, -1):
            # 求解map[i]
            for j in range(i, n):
                a = nums[j]
                if j + 2 < n:
                    a = nums[j] + self.map.get(j+2)
                b = 0
                if self.map.get(i) != None:
                    b = self.map.get(i)
                self.map[i] = max(a ,b)
        return self.map.get(0)

if __name__ == '__main__':
    print(Solution().rob2([2,7,9,3,1]))