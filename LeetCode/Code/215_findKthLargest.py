# 在未排序的数组中找到第 k 个最大的元素。请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
class Solution(object):
    def findKthLargest(self, nums, k):
        map = {}

        for i in nums:
            if map.get(i):
                count = map[i] + 1
                map[i] = count
            else:
                map[i] = 1

        for i in range(len(nums), 0, -1):
            if map.get(i):
                k = k - map[i]
                if k <= 0:
                    return i

if __name__ == '__main__':
    print(Solution().findKthLargest([99,99], 1))


