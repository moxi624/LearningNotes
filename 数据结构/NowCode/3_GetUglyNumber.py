# 获取丑数
#
class Solution:
    def JumpFloorII(self, n):
        if n == 1:
            return 1;
        ret = 0
        a = 1
        for i in range(2, n + 1):
            ret = 2 * a
            a = ret
        return ret

if __name__ == '__main__':
    print(Solution().JumpFloorII(10))