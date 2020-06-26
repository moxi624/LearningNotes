# 快速排序

## 概念

快速排序也是分治的思想，但是它于归并算法更加好，是因为归并算法会用到辅助数组，其空间复杂度为O(n)，而快速排序不需要用到新的数组空间，它的空间复杂度是O(1)

快速排序使用分治法来把一个串（list）分为两个子串（sub-lists）。具体算法描述如下：

- 从数列中挑出一个元素，称为 “基准”（**pivot**）；
- 重新排序数列，所有元素比基准值小的摆放在基准前面，所有元素比基准值大的摆在基准的后面（相同的数可以到任一边）。在这个分区退出之后，该基准就处于数列的中间位置。这个称为分区（partition）操作；
- 递归地（recursive）把小于基准值元素的子数列和大于基准值元素的子数列排序。

![img](images/1551078742204.gif)

## 代码

```
def quick_sort(li, start, end):
    # 分治 一分为二
    # start=end ,证明要处理的数据只有一个
    # start>end ,证明右边没有数据
    if start >= end:
        return
    # 定义两个游标，分别指向0和末尾位置
    left = start
    right = end
    # 把0位置的数据，认为是中间值
    mid = li[left]
    while left < right:
        # 让右边游标往左移动，目的是找到小于mid的值，放到left游标位置
        while left < right and li[right] >= mid:
            right -= 1
        print("left 1", li)
        li[left] = li[right]
        print("left 2", li)
        # 让左边游标往右移动，目的是找到大于mid的值，放到right游标位置
        while left < right and li[left] < mid:
            left += 1
        li[right] = li[left]
        print("right ", li)
    # while结束后，把mid放到中间位置，left=right
    li[left] = mid
    # 递归处理左边的数据
    quick_sort(li, start, left-1)
    # 递归处理右边的数据
    quick_sort(li, left+1, end)

if __name__ == '__main__':
    l = [1,5,4,6,2,8]
    # l = 3 [2,1,5,6,5,4]
    # [2, 1, 5, 6, 5, 4]
    quick_sort(l,0,len(l)-1)
    print(l)
    # 稳定性：不稳定
    # 最优时间复杂度：O(nlogn)
    # 最坏时间复杂度：O(n^2)
```

