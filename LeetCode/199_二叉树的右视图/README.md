# 二叉树的右视图

## 来源

https://leetcode-cn.com/problems/binary-tree-right-side-view/

## 描述

给定一棵二叉树，想象自己站在它的右侧，按照从顶部到底部的顺序，返回从右侧所能看到的节点值。

```bash
示例:

输入: [1,2,3,null,5,null,4]
输出: [1, 3, 4]
解释:

   1            <---
 /   \
2     3         <---
 \     \
  5     4       <---
```



## 代码1（错误）

最开始的想法就是一直匹配最右边，当该节点没有最右侧的子树时，再去匹配它的左子树，然后再去寻找最右侧

```python
class Solution(object):
    def rightSideView(self, root):
        if root == None:
            return []
        ret = [root.val]
        while root.right or root.left:
            rightNode = root.right
            if rightNode == None:
                leftNode = root.left
                ret.append(leftNode.val)
                root = root.left
            else:
                ret.append(rightNode.val)
                root = root.right
        return ret
```

但是遇到这样的情况的话，是有问题的

```
   1            <---
 /   \
2     3         <---
  \    
   4           <---
```

也就是上面的程序只会输出  1 3  而最后的结果应该是  1 3 4

## 代码2

因为之前还做过二叉树的层次遍历这道题，想着右侧视图，不就是层次遍历时候的最右侧的节点么？想到这里，就重新复现了一下二叉树的层次遍历的代码~，然后通过一个状态位来记录 最右侧的节点

```python
class Solution(object):
    def rightSideView(self, root):
        if root == None:
            return []
        ret = []
        stack = [root]
        secondStack = []
        while stack or secondStack:
            count = 0
            while stack:
                node = stack.pop()
                if count == 0:
                    ret.append(node.val)
                count += 1

                if node.right:
                    secondStack.insert(0, node.right)
                if node.left:
                    secondStack.insert(0, node.left)
            
            count = 0
            while secondStack:
                node = secondStack.pop()
                if count == 0:
                    ret.append(node.val)
                count += 1

                if node.right:
                    stack.insert(0, node.right)                
                if node.left:
                    stack.insert(0, node.left)
        return ret
```

