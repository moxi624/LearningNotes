# 矩形覆盖
class Solution:
    def rectCover(self, number):
        # write code here
        if number == 0:
            return 0
        if number == 1:
            return 1
        if number == 2:
            return 2

        a = 1
        b = 2
        for i in range(3, number + 1):
            b = a + b
            a = b - a
        return b