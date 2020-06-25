# 排序算法-冒泡插入选择

## 冒泡排序

```python
# 冒泡排序
class Solution:
    def bubbleSort(self, array):
        for i in range(len(array)):
            flag = False
            for j in range(len(array) - 1 - i):
                if array[j] > array[j+1]:
                    temp = array[j+1]
                    array[j+1] = array[j]
                    array[j] = temp
                    flag = True
            if not flag:
                break
        return array

if __name__ == '__main__':
    print(Solution().bubbleSort([1,5,4,3,2]))
```

## 选择排序

```
# 插入排序
class Solution:
    def insertSort(self, array):
        for i in range(len(array)):
            minIndex = i
            for j in range(i, len(array)):
                if array[minIndex] > array[j]:
                    minIndex = j
            temp = array[minIndex]
            array[minIndex] = array[i]
            array[i] = temp
        return array
if __name__ == '__main__':
    print(Solution().insertSort([1,8,3,2,6,9]))
```

## 插入排序

```
# 插入排序
class Solution:
    def insertSort(self, alist):
        n = len(alist)
        for j in range(0, n):
            for i in range(j, 0, -1):
                if alist[i] < alist[i - 1]:
                    alist[i], alist[i - 1] = alist[i - 1], alist[i]
                else:
                    break
        return alist
```

