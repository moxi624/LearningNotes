# 二进制中1的个数
# 输入一个整数，输出该数二进制表示中1的个数。其中负数用补码表示

class Solution:
    def NumberOf1(self, n):
        # 这一步是求补码的
        n = n & 0xFFFFFFFF
        count = 0
        for c in str(bin(n)):
            if c == "1":
                count +=1
        return count

    def NumberOf2(self, n):
        # 这一步是求补码的
        n = n & 0xFFFFFFFF
        count = 0
        for i in range(32):
            mask = 1 << i
            if n & mask != 0:
                count += 1
        return count

if __name__ == '__main__':
    print(Solution().NumberOf1(-2))
