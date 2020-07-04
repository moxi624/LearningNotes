# 通过翻转子数组使两个数组相等

## 来源

https://leetcode-cn.com/problems/make-two-arrays-equal-by-reversing-sub-arrays/

## 描述

给你两个长度相同的整数数组 target 和 arr 。

每一步中，你可以选择 arr 的任意 非空子数组 并将它翻转。你可以执行此过程任意次。

如果你能让 arr 变得与 target 相同，返回 True；否则，返回 False 。

```
示例 1：
输入：target = [1,2,3,4], arr = [2,4,1,3]
输出：true
解释：你可以按照如下步骤使 arr 变成 target：
1- 翻转子数组 [2,4,1] ，arr 变成 [1,4,2,3]
2- 翻转子数组 [4,2] ，arr 变成 [1,2,4,3]
3- 翻转子数组 [4,3] ，arr 变成 [1,2,3,4]
上述方法并不是唯一的，还存在多种将 arr 变成 target 的方法

示例 2：
输入：target = [7], arr = [7]
输出：true
解释：arr 不需要做任何翻转已经与 target 相等

示例 3：
输入：target = [1,12], arr = [12,1]
输出：true

示例 4：
输入：target = [3,7,9], arr = [3,7,11]
输出：false
解释：arr 没有数字 9 ，所以无论如何也无法变成 target

示例 5：
输入：target = [1,1,1,1,1], arr = [1,1,1,1,1]
输出：true
```

## 代码

首先看着这道题，差点把我唬住了。。其实最后发现就是判断这两个数组的元素是否都相同

证明也很简单，需要知道冒泡排序的过程。

>冒泡排序的所有操作都是不断交换相邻两个元素的过程，交换相邻两个元素的操作也是反转子数组的一种。
>考虑数组target，它一定可以通过冒泡排序变成递增（递减）的数组。假设我们记录下每一次的交换，记为操作序列A。
>考虑数组 arr，它也一定可以通过冒泡排序变成递增（递减）的数组。
>如果target与arr元素相同，那么最终冒泡排序结果也相同。将数组arr进行冒泡排序，再进行操作序列A的逆过程，就一定可以得到target。
>如果数组target的元素与数组arr的元素不同，显然无法通过arr得到target

所以代码就很简单了，只需要将他们排序后判断一下即可

```
class Solution(object):
    def canBeEqual(self, target, arr):
        target = sorted(target)
        arr = sorted(arr)
        return target == arr
```

