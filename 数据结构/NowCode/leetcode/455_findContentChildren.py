class Solution(object):
    def findContentChildren(self, g, s):
        """
        :type g: List[int]
        :type s: List[int]
        :rtype: int
        """
        g = sorted(g, reverse=True)
        s = sorted(s, reverse=True)

        # 饼干索引
        si = 0
        # 贪心索引
        gi = 0
        # 几个小朋友开心
        res = 0
        while gi < len(g) and si < len(s):
            if s[si] >= g[gi]:
                # 如果当前的饼干能够满足最贪心的小朋友，那么就进行分配
                res = res + 1
                si = si + 1
                gi = gi + 1
            else:
                gi = gi + 1
        return res

if __name__ == '__main__':
    print(Solution().findContentChildren([1,5,4,2], [2,1,7,6]))