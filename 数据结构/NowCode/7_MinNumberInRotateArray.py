# 旋转数组的最小数字
# 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。
# 输入一个非递减排序的数组的一个旋转，输出旋转数组的最小元素。
# 例如数组{3,4,5,1,2}为{1,2,3,4,5}的一个旋转，该数组的最小值为1。
# NOTE：给出的所有元素都大于0，若数组大小为0，请返回0。
class Solution:
    def minNumberInRotateArray(self, rotateArray):
        minNum = 0
        # 第一种方法，就是遍历所以的元素，找出最小的
        for i in range(0, len(rotateArray)):
            minNum = minNum if minNum < rotateArray[i] and minNum != 0 else rotateArray[i]
        return minNum

    # 二分查找法
    # 有序的数组中使用
    def bSearch(self, array, target):
        left = 0
        right = len(array) - 1
        while left < right:
            # 右移1位，相当于除以2
            mid = (left + right) >> 1
            if target == mid:
                return mid
            if target > mid:
                left = mid + 1
            else:
                right = mid - 1
        return None

    # 有序的数组中使用
    def minNumberInRotateArray2(self, rotateArray):
        if not rotateArray:
            return None
        left = 0
        right = len(rotateArray) - 1
        while left <= right:
            middle = (left + right) >> 1
            # middle 比两边的都小，说明是最小值
            if rotateArray[middle] < rotateArray[middle - 1]:
                return rotateArray[middle]
            elif rotateArray[middle] < rotateArray[right]:
                right = middle - 1
            else:
                left = middle + 1
        return 0

    # 二分查找法(以下代码错误)
    # 有序的数组中使用
    def minNumberInRotateArray3(self, rotateArray):
        if not rotateArray:
            return None
        left = 0
        right = len(rotateArray) - 1
        while left < right:
            middle = (left + right) >> 1
            # middle 比两边的都小，说明是最小值
            if rotateArray[middle] < rotateArray[middle - 1]:
                return rotateArray[middle]
            elif abs(rotateArray[left] - rotateArray[middle]) < abs(rotateArray[right] - rotateArray[middle]):
                left = middle + 1
            elif abs(rotateArray[left] - rotateArray[middle]) > abs(rotateArray[right] - rotateArray[middle]):
                right = middle - 1
        return 0

if __name__ == '__main__':
    print(Solution().minNumberInRotateArray2([3,4,5,2,3,6,7,8]))