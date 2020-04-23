# 二维数组中的查找

## 题目描述

在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。



## 暴力算法 

下面是这样一个规律的二维数组

```
1 2 3 4 
2 3 4 5 
4 6 7 10
9 11 13 15
```

我们对每个数字进行遍历

```
class Solution:
    def Find(self, target, array):
        for i in range(len(array)):
            for j in range(len(array)):
                if target == array[i][j]:
                    return True
        return False
```



## 标准解法

上面我们可以看到，题目是给定递增的顺序的，而我们使用暴力算法的时候，没有应用到它的有序的特征。

```
1 2 3 4 
2 3 4 5 
4 6 7 10
9 11 13 15
```

我们在回到刚刚的规律这块，我们能够发现，给定一个 target，我们可以比较这个数的最后一列

```
4
5
10
15
```

如果这个数在比 某一个数小，那么它肯定处于该行中，进行查找。例如我们现在要查找 `7`

```
首先比较的是 4，然后发现 4 < 7 ，因此肯定不在第一行
然后比较 5 ， 5 < 7，那么也不在第二行
然后比较 10， 10 > 7，因此可能就在这一行，那么就开始在这一行进行遍历，最终找到了7
```



代码为：

```
class Solution:
    # array 二维列表
    def Find(self, target, array):
    	# 判断二维数组是否非空
        if any(array) == 0:
            return False
        for i in range(len(array)):
            # 判断最后一位是否比target更大
            if array[i][len(array) - 1] >= target:
                for j in range(len(array)):
                    if target == array[i][j]:
                        return True
        return False       
```

