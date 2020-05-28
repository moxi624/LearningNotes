class Solution:
    def GetUglyNumber_Solution(self, index):
        # write code here
        if index < 1:
            return None
        #死循环，找丑数
        #判断一个数是不是丑数，先循环除以2，直到不能整除，
        #循环除以3 直到不能整除，循环除以5 直到不能整除
        #这时如果剩余的值是1  我们就说它是丑数
        #其他情况就都不是丑数
        def isUglyNumber(num):
            while num % 2 == 0:
                num = num //2
            while num % 3 == 0:
                num = num //3
            while num % 5 == 0:
                num = num //5
            if num == 1:
                return True
            else:
                return False
        count = 0
        num = 1
        while True:
            if isUglyNumber(num):
                count += 1
            if count == index:
                return num
            num += 1