# 三角形最小路径和
class Solution(object):
    def minimumTotal(self, triangle):
        for i in range(len(triangle) - 1, 0, -1):
            for j in range(i):
                triangle[i-1][j] += min(triangle[i][j], triangle[i][j+1])
        return triangle[0][0]