class Solution:
    def GetLeastNumbers_Solution(self, tinput, k):
        array = []
        if k > len(tinput):
            return array
        for i in range(k):
            array.append(99)
        for key in tinput:
            for index in range(len(array)):
                if key < array[index]:
                    array.insert(index, key)
                    break
            if len(array) > k:
                del array[-1]
        return array
if __name__ == '__main__':
    print(Solution().GetLeastNumbers_Solution([4,5,1,6,2,7,3,8], 4))