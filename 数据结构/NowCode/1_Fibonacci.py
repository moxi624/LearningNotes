# 大家都知道斐波那契数列，现在要求输入一个整数n，请你输出斐波那契数列的第n项（从0开始，第0项为0）  n<=39
class Solution:

    # 递归实现
    def Fibonacci(self, n):
        if n == 0:
            return 0;
        if n == 1:
            return 1;
        if n > 0:
            return self.Fibonacci(n - 1) + self.Fibonacci(n -2);
        else:
            return None

    # 非递归实现
    # 当 n = 2的时候， h = f(1) + f(0) = 1 + 0 = 1
    # 当 n = 3的时候， h = f(2) + f(1) = 1 + 1 = 2
    # 当 n = 4的时候， h = f(3) + f(2) = 3 + 1 = 4
    def Fibonacci2(self, n):
        if n == 0:
            return 0;
        if n == 1:
            return 1;

        ret = 0
        a = 1
        b = 0
        for i in range(0, n-1):
            ret = a + b
            b = a
            a = ret
        return ret

if __name__ == '__main__':
    print(Solution().Fibonacci2(10))