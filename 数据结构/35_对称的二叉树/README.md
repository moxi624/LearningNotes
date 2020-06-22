# 对称的二叉树

## 来源

https://www.nowcoder.com/practice/ff05d44dfdb04e1d83bdbdab320efbcb

## 描述

请实现一个函数，用来判断一颗二叉树是不是对称的。注意，如果一个二叉树同此二叉树的镜像是同样的，定义其为对称的。

## 思路

之前我们做过一道题目，就是二叉树的镜像，那么判断对称二叉树，就变成了二叉树和它的镜像是否相同

## 代码

```
class TreeNode:
    def __init__(self, x):
        self.val = x
        self.left = None
        self.right = None
class Solution:
    def isSymmetrical(self, pRoot):

        if pRoot == None:
            return True

        def isMirror(left, right):
            if left == None and right == None:
                return True
            elif left == None or right == None:
                return False
            if left.val != right.val:
                return False
            ret1 = isMirror(left.left, right.right)
            ret2 = isMirror(left.right, right.left)
            return ret1 and ret2

        return isMirror(pRoot.left, pRoot.right)
```

