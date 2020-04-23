# 一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法（先后次序不同算不同的结果）
class Solution:
    def JumpFloor(self, n):
        if n == 0:
            return 0;
        if n == 1:
            return 1;
        if n == 2:
            return 2;

        ret = 0
        a = 1
        b = 2
        for i in range(3, n + 1):
            ret = a + b
            a = b
            b = ret
        return ret

if __name__ == '__main__':
    print(Solution().JumpFloor(10))