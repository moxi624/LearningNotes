# 二叉树的镜像

## 来源

https://www.nowcoder.com/practice/564f4c26aa584921bc75623e48ca3011

## 题目

操作给定的二叉树，将其变换为源二叉树的镜像。

## 输入描述:

```
二叉树的镜像定义：源二叉树 
    	    8
    	   /  \
    	  6   10
    	 / \  / \
    	5  7 9 11
    	镜像二叉树
    	    8
    	   /  \
    	  10   6
    	 / \  / \
    	11 9 7  5
```

## 思路

我们就对每个节点的左右子树进行交换即可，也就是需要使用一个temp变量来存储交换的节点。然后在重复它的左子树和右子树

```
class TreeNode:
    def __init__(self, x):
        self.val = x
        self.left = None
        self.right = None
class Solution:
    # 返回镜像树的根节点
    def Mirror(self, root):
        if root == None:
            return None
        # 处理左子树
        temp = root.left
        root.left = root.right
        root.right = temp
        self.Mirror(root.left)
        self.Mirror(root.right)
```

