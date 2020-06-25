# 二叉搜索树的第K个节点

## 题目

https://www.nowcoder.com/practice/ef068f602dde4d28aab2b210e859150a

## 描述

给定一棵二叉搜索树，请找出其中的第k小的结点。例如， （5，3，7，2，4，6，8）  中，按结点数值大小顺序第三小结点的值为4。

## 思考

首先需要知道，二叉排序树的中序遍历，即为有序的，所以我们查找第K小的节点，只需要将二叉树进行中序排列输出，那么取得 k-1个数，即为结果

## 代码

```
class Solution:
    # 返回对应节点TreeNode
    def KthNode(self, pRoot, k):
        retList = []
        def preOrder(pRoot):
            if pRoot == None:
                return None
            preOrder(pRoot.left)
            retList.append(pRoot)
            preOrder(pRoot.right)

        preOrder(pRoot)
        if len(retList) < k or k < 1:
            return None
        return retList[k-1]
```

