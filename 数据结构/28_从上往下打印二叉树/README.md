# 从上往下打印二叉树

## 来源

https://www.nowcoder.com/practice/7fe2212963db4790b57431d9ed259701

## 题目

从上往下打印出二叉树的每个节点，同层节点从左至右打印。

## 代码

```
class TreeNode:
    def __init__(self, x):
        self.val = x
        self.left = None
        self.right = None
class Solution:
    # 返回从上到下每个节点值列表，例：[1,2,3]
    def PrintFromTopToBottom(self, root):
        if root == None:
            return []
        # 将输入的节点存入support中
        support = [root]
        ret = []
        while support:
            tempNode = support[0]
            ret.append(tempNode.val)
            if tempNode.left:
                support.append(tempNode.left)
            if tempNode.right:
                support.append(tempNode.right)
            # 删除已经输出的节点
            del support[0]
        return ret
```

