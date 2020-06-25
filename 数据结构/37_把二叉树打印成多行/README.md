# 把二叉树打印成多行

## 题目

https://www.nowcoder.com/practice/445c44d982d04483b04a54f298796288

## 描述

从上到下按层打印二叉树，同一层结点从左至右输出。每一层输出一行。

## 思路

本题的思路，和上一题是一样的，也是使用两个队列来进行存储，然后交替换行，需要注意的是，这里用的是队列，上一题之字形的用的是栈

## 代码

```
class TreeNode:
    def __init__(self, x):
        self.val = x
        self.left = None
        self.right = None
class Solution:
    # 返回二维列表[[1,2],[4,5]]
    def Print(self, pRoot):
        if pRoot == None:
            return []
        queue1 = [pRoot]
        queue2 = []
        ret = []
        while queue1 or queue2:
            if queue1:
                tmpRet = []
                while queue1:
                    tmpNode = queue1.pop(0)
                    tmpRet.append(tmpNode.val)
                    if tmpNode.left:
                        queue2.append(tmpNode.left)
                    if tmpNode.right:
                        queue2.append(tmpNode.right)
                ret.append(tmpRet)
            if queue2:
                tmpRet = []
                while queue2:
                    tmpNode = queue2.pop(0)
                    tmpRet.append(tmpNode.val)
                    if tmpNode.left:
                        queue1.append(tmpNode.left)
                    if tmpNode.right:
                        queue1.append(tmpNode.right)
                ret.append(tmpRet)
        return ret
```

