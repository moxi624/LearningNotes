# 菲波那切数列
class Solution:
    def fib(self, n):
        if n == 0:
            return 0
        if n == 1:
            return 1
        return self.fib(n-1) + self.fib(n-2)

    def fib2(self, n):
        if n == 0:
            return 0
        if n == 1:
            return 1
        a = 1
        b = 2
        for i in range(2, n):
            a = a + b
            b = a



if __name__ == '__main__':
    print(Solution().fib(10))