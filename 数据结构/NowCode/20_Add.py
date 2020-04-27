#  链表中环的入口结点
# 给一个链表，若其中包含环，请找出该链表的环的入口结点，否则，输出null
# -*- coding:utf-8 -*-
class Solution:
    def Add(self, num1, num2):
        # 第一种代码：循环。简洁但是原理相同，那么我们以下面第二段代码为例；来解析。
        # while (num2):
        #     num1, num2 = (num1 ^ num2) & 0xFFFFFFFF, ((num1 & num2) << 1) & 0xFFFFFFFF
        # return num1 if num1 <= 0x7FFFFFFF else ~(num1 ^ 0xFFFFFFFF)

        # 第二种代码：
        # 首先两个数做 一个 异或 运算^ 那就是 在不进位的情况下，让两个相加 求和。
        xorNum = num1 ^ num2
        # 让两个数 做 位与 操作，然后再向 左 移 一位，得到它 向前进位的值。
        andNum = (num1 & num2) << 1
        # 判断，当 进位 的值不等于0 的时候，说明 一直有进位，也就是 过程没有结束。
        while andNum != 0:
            # 那么我们就继续上面的操作。但是这次的 数值 改为上次的两个结果，
            # 一个 是异或的结果，一个是 与 操作 & 以后 左移一位的 结果。
            tmp1 = xorNum ^ andNum
            tmp2 = (xorNum & andNum) << 1
            # 因为如果这个数为负数的话，那么负数 左移 一位与正数 不同，负数 是数值变小，正数 数值变大
            # 如果是正数的话那么这一步就 不变，如果是负数的话，这一步就对负数来起作用。
            # 对于python来说  负数的 二进制 可能会有无数个1，我们用这个方法让它变成一个可数的数字长度。
            tmp1 = tmp1 & 0xffffffff

            xorNum = tmp1
            andNum = tmp2
        # 一个负整数（或原码）与其补数（或补码）相加，和为模。 0xffffffff
        # ~(xorNum ^ 0xFFFFFFFF)  这个是 异或数  与  模 来 异或，最后 按位 取反 来求得 负数的补码。
        return xorNum if xorNum <= 0x7ffffff else ~(xorNum ^ 0xFFFFFFFF)

if __name__ == '__main__':
    print(Solution().NumberOf1(-2))
