class Solution(object):
    def getIndex(self, line):
        lineLen = len(line)
        left = 0
        right = lineLen - 1

        while left <= right:
            mid = (left + right) // 2
            if line[mid] < 0 and ((mid != 0 and line[mid -1] >= 0) or (mid == 0)):
                return lineLen - mid
            elif line[mid] < 0:
                right = mid -1
            else:
                left = mid + 1
        return 0


    def countNegatives(self, grid):
        count = 0
        for i in range(len(grid)):
            count += self.getIndex(grid[i])
        return count

if __name__ == '__main__':
    print(Solution().countNegatives([[4,3,2,-1],[3,2,1,-1],[1,1,-1,-2],[-1,-1,-2,-3]]))

