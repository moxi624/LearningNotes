# 变态跳台阶
# 一只青蛙一次可以跳上1级台阶，也可以跳上2级，也可以跳n阶。求该青蛙跳上一个n级的台阶总共有多少种跳法（先后次序不同算不同的结果）
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