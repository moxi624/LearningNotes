class Solution(object):
    map = {}
    # 使用记忆化搜索解决
    def integerBreak(self, n):
        if n == 1:
            return 1
        res = -1
        if self.map.get(n) == None:

            for i in range(1, n+1):
                # 找到最大值【需要判断三个值，一个 n * n-i】
                a = i * self.integerBreak(n - i)
                b = i * (n - i)
                c = res
                res = max(a, b, c)
            self.map[n] = res
            return res
        else:
            return self.map[n]

    # 使用动态规划解决
    def integerBreak2(self, n):
        self.map[1] = 1
        for i in range(2, n+1):
            for j in range(1, i):
                # 求解 j+(i-j)的形式
                a = j*(i-j)
                b = j * self.map.get(i-j)
                c = -1
                if self.map.get(i) != None:
                    c = self.map.get(i)
                self.map[i] = max(a, b, c)
        return self.map[n]

if __name__ == '__main__':
    print(Solution().integerBreak2(10))
