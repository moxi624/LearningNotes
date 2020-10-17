class Solution(object):
    # def rotate(self, nums, k):
    #     arrLen = len(nums)
    #     if k == arrLen:
    #         return nums
    #     arr=[0 for i in range(arrLen)]
    #     for index in range(arrLen):
    #         newIndex = (index + k) % arrLen
    #         arr[newIndex] = nums[index]
    #     return arr

    def rotate(self, nums, k):
        arrLen = len(nums)
        if k == arrLen:
            return nums

        k = k % arrLen

        for index in range(k):
            nums.insert(0, nums.pop())
        return nums
if __name__ == '__main__':
    print(Solution().rotate([1,2,3,4,5,6,7], 3))