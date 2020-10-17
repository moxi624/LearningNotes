# 爬楼梯
# 菲波那切数列
class Solution:
    map = {}
    def ClimbingStairs(self, n):
        # 爬1阶楼梯，有1种方法
        self.map[1] = 1
        # 爬2阶楼梯，有2种方法
        self.map[2] = 2

        for i in range(3, n+1):
            self.map[i] = self.map[i-1] + self.map[i-2]
        return self.map.get(n)
if __name__ == '__main__':
    print(Solution().ClimbingStairs(10))