#### 树的知识点：

##### 什么叫做树？

**树状图**是一种[数据结构](https://baike.baidu.com/item/%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84/1450)，它是由n（n>=0）个有限结点组成一个具有层次关系的[集合](https://baike.baidu.com/item/%E9%9B%86%E5%90%88)。把它叫做“树”是因为它看起来像一棵倒挂的树，也就是说它是根朝上，而叶朝下的。它具有以下的特点：

每个结点有零个或多个子结点；没有父结点的结点称为根结点；每一个非根结点有且只有一个父结点；除了根结点外，每个子结点可以分为多个不相交的子树；

叶节点没有子节点，根节点没有父节点。

##### 什么是二叉树？

每个节点最多含有两个子树的树称为二叉树。下图就是一个二叉树。

在计算机科学中，二叉树是每个结点最多有两个子树的树结构。通常子树被称作“左子树”（left subtree）和“右子树”（right subtree）。二叉树常被用于实现二叉查找树和二叉堆。

一棵深度为k，且有2^k-1个节点的二叉树，称为满二叉树。这种树的特点是每一层上的节点数都是最大节点数。而在一棵二叉树中，除最后一层外，若其余层都是满的，并且最后一层或者是满的，或者是在右边缺少连续若干节点，则此二叉树为完全二叉树。具有n个节点的完全二叉树的深度为floor(log2n)+1。深度为k的完全二叉树，至少有2k-1个节点，至多有2k-1个节点



![](C:\Users\Administrator\Desktop\剑指offer\二叉树.png)

##### 二叉树的遍历：

遍历是对树的一种最基本的运算，所谓遍历二叉树，就是按一定的规则和顺序走遍二叉树的所有结点，使每一个结点都被访问一次，而且只被访问一次。由于二叉树是非线性结构，因此，[树的遍历](https://baike.baidu.com/item/%E6%A0%91%E7%9A%84%E9%81%8D%E5%8E%86)实质上是将二叉树的各个结点转换成为一个线性序列来表示。

设L、D、R分别表示遍历左子树、访问根结点和遍历右子树， 则对一棵二叉树的遍历有三种情况：DLR（称为先根次序遍历），LDR（称为中根次序遍历），LRD （称为后根次序遍历）。

```python
class treeNode(object):
    def __init__(self,x):
        self.val = x
        self.left = None
        self.right = None

#1. 深度优先
#2. 广度优先
#对于深度优先来说：
"""

1 先序遍历  先打印根 1,2,4,5,3,6,8,7
2 中序遍历  先打印左侧的叶子节点 4，再输出 中节点  2 ；  4 2 5 1 6 8 3 7 
3 先序遍历  输出顺序  4 5 2 8 6 7 3 1

注意：  先序   中序 后序  都是对应于根节点来说的，左右节点都是先左后右

"""
#递归
def preOrderRecusive(root):
    if root == None:
        return None

    print(root.val)
    preOrderRecusive(root.left)
    preOrderRecusive(root.right)

def midOrderRecusive(root):
    if root == None:
        return None
    midOrderRecusive(root.left)
    print(root.val)
    midOrderRecusive(root.right)


def laterOrderRecusive(root):
    if root == None:
        return None
    laterOrderRecusive(root.left)
    laterOrderRecusive(root.right)
    print(root.val)

#非递归的形式 去遍历数
#递归和循环是可以互相转换的

"""

1 先根遍历 先访问根节点，再访问左子节点，最后访问右子节点
2 中根遍历 先访问左子节点，再访问根节点，最后访问右子节点
3 后跟遍历 先访问左子节点，再访问右子节点，最后访问根节点。

"""

def preOrder(root):
    if root == None:
        return None

    stack = []
    tmpNode = root
    while tmpNode or stack :
        while tmpNode:
            print(tmpNode.val)
            stack.append(tmpNode)
            tmpNode = tmpNode.left
        node  = stack.pop()
        tmpNode = node.right

def midOrder(root):
    if root == None:
        return None

    stack = []
    tmpNode = root
    while tmpNode or stack :
        while tmpNode:

            stack.append(tmpNode)
            tmpNode = tmpNode.left
        node  = stack.pop()
        print(node.val)
        tmpNode = node.right

def laterOrder(root):
    if root == None:
        return None

    stack = []
    tmpNode = root
    while tmpNode or stack :
        while tmpNode:
            stack.append(tmpNode)
            tmpNode = tmpNode.left
        node = stack[-1]
        tmpNode = node.right
        if node.right == None:
            node = stack.pop()
            print(node.val)
            while stack and node == stack[-1].right:
                node =  stack.pop()
                print(node.val)
```

## 25.重建二叉树

**输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}，则重建二叉树并返回。**

```python
# -*- coding:utf-8 -*-
# class TreeNode:
#     def __init__(self, x):
#         self.val = x
#         self.left = None
#         self.right = None
class Solution:
    # 返回构造的TreeNode根节点
    def reConstructBinaryTree(self, pre, tin):
        # write code here
        if not pre or not tin:
            return None
        if len(pre) != len(tin):
            return None
        # 取出pre 的第一个值  就是根节点
        root = pre[0]
        rootNode = TreeNode(root)
        # 找到在 tin  中序遍历中的根节点 所在的索引位置
        pos = tin.index(root)
        # 中序遍历的 列表的左右节点 分开 切片 成两个列表
        tinLeft = tin[0:pos]
        tinRight = tin[pos + 1:]
        # 前序遍历的 列表的左右节点 分开 切片 成两个列表
        preLeft = pre[1:pos + 1]
        preRight = pre[pos + 1:]

        leftNode = self.reConstructBinaryTree(preLeft, tinLeft)
        rightNode = self.reConstructBinaryTree(preRight, tinRight)

        rootNode.left = leftNode
        rootNode.right = rightNode
        return rootNode
```

___



## 26.树的子结构

**输入两棵二叉树A，B，判断B是不是A的子结构。（ps：我们约定空树不是任意一个树的子结构）**

```python
# -*- coding:utf-8 -*-
# class TreeNode:
#     def __init__(self, x):
#         self.val = x
#         self.left = None
#         self.right = None
class Solution:
    def HasSubtree(self, pRoot1, pRoot2):
        # write code here
        if pRoot2 == None or pRoot1 == None:
            return False

        def hasEqual(pRoot1, pRoot2):
            if pRoot2 == None:
                return True
            if pRoot1 == None:
                return False
            if pRoot1.val == pRoot2.val:
                if pRoot2.left == None:
                    leftEqual = True
                else:
                    leftEqual = hasEqual(pRoot1.left, pRoot2.left)
                if pRoot2.right == None:
                    rightEqual = True
                else:
                    rightEqual = hasEqual(pRoot1.right, pRoot2.right)
                return leftEqual and rightEqual
            return False

        if pRoot1.val == pRoot2.val:
            ret = hasEqual(pRoot1, pRoot2)
            if ret:
                return True

        ret = self.HasSubtree(pRoot1.left, pRoot2)
        if ret:
            return True

        ret = self.HasSubtree(pRoot1.right, pRoot2)
        return ret


"""

对于Python这道题，有些地方需要仔细考虑的。

先说下算法实现思路：对于两棵二叉树来说，要判断B是不是A的子结构，首先第一步在树A中查找与B根节点的值一样的节点。

通常对于查找树中某一个节点，我们都是采用递归的方法来遍历整棵树。

第二步就是判断树A中以R为根节点的子树是不是和树B具有相同的结构。

这里同样利用到了递归的方法，如果节点R的值和树的根节点不相同，则以R为根节点的子树和树B肯定不具有相同的节点；

如果它们值是相同的，则递归的判断各自的左右节点的值是不是相同。

递归的终止条件是我们达到了树A或者树B的叶节点。

有地方要重点注意，DoesTree1haveTree2()函数中的两个 if 判断语句 不能颠倒顺序 。
因为如果颠倒了顺序，会先判断pRoot1 是否为None, 其实这个时候，pRoot1 的节点已经遍历完成确认相等了，但是这个时候会返回 False，判断错误。

有同学不相信的，可以去试试换个顺序，肯定不能AC。同时这个也是《剑指offer》书上没有写的，希望能引起大家的注意。

"""
# -*- coding:utf-8 -*-
# class TreeNode:
#     def __init__(self, x):
#         self.val = x
#         self.left = None
#         self.right = None
class Solution2:
    def HasSubtree(self, pRoot1, pRoot2):
        # write code here
        result = False
        if pRoot1 != None and pRoot2 != None:
            if pRoot1.val == pRoot2.val:
                result = self.DoesTree1haveTree2(pRoot1, pRoot2)
            if not result:
                result = self.HasSubtree(pRoot1.left, pRoot2)
            if not result:
                result = self.HasSubtree(pRoot1.right, pRoot2)
        return result
    # 用于递归判断树的每个节点是否相同
    # 需要注意的地方是: 前两个if语句不可以颠倒顺序
    # 如果颠倒顺序, 会先判断pRoot1是否为None, 其实这个时候pRoot2的结点已经遍历完成确定相等了, 但是返回了False, 判断错误
    def DoesTree1haveTree2(self, pRoot1, pRoot2):
        if pRoot2 == None:
            return True
        if pRoot1 == None:
            return False
        if pRoot1.val != pRoot2.val:
            return False
        return self.DoesTree1haveTree2(pRoot1.left, pRoot2.left) and self.DoesTree1haveTree2(pRoot1.right, pRoot2.right)
```

___

## 27.二叉树的镜像

**操作给定的二叉树，将其变换为源二叉树的镜像。**

##### `输入描述:`

```python
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

___



```python
# -*- coding:utf-8 -*-
# class TreeNode:
#     def __init__(self, x):
#         self.val = x
#         self.left = None
#         self.right = None
class Solution:
    # 返回镜像树的根节点
    def Mirror(self, root):
        # write code here
        if root == None:
            return None
        #处理根节点
        root.left,root.right = root.right,root.left
        self.Mirror(root.left)
        self.Mirror(root.right)
```

___

## 28.从上往下打印二叉树

**从上往下打印出二叉树的每个节点，同层节点从左至右打印。**

```python
# -*- coding:utf-8 -*-
# class TreeNode:
#     def __init__(self, x):
#         self.val = x
#         self.left = None
#         self.right = None
class Solution:
    # 返回从上到下每个节点值列表，例：[1,2,3]
    def PrintFromTopToBottom(self, root):
        # write code here
        if root == None:
            return []
        treeNodeTmp = [root]
        ret = []
        while treeNodeTmp:
            tmpNode = treeNodeTmp[0]
            ret.append(tmpNode.val)
            if tmpNode.left:
                treeNodeTmp.append(tmpNode.left)
            if tmpNode.right:
                treeNodeTmp.append(tmpNode.right)
            del treeNodeTmp[0]
        return ret
```

___

## 29.二叉搜索树的后序遍历序列

**输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。如果是则输出Yes,否则输出No。假设输入的数组的任意两个数字都互不相同。**

```python
"""
python:后序遍历 的序列中，最后一个数字是树的根节点 ，数组中前面的数字可以分为两部分：第一部分是左子树节点 的值，都比根节点的值小；第二部分 是右子树 节点的值，都比 根 节点 的值大，后面用递归分别判断前后两部分 是否 符合以上原则

"""


class Solution:
    def VerifySquenceOfBST(self, sequence):
        # write code here
        if sequence==None or len(sequence)==0:
            return False
        length=len(sequence)
        root=sequence[length-1]
        # 在二叉搜索 树中 左子树节点小于根节点
        for i in range(length):
            if sequence[i]>root:
                break
        # 二叉搜索树中右子树的节点都大于根节点
        for j  in range(i,length):
            if sequence[j]<root:
                return False
        # 判断左子树是否为二叉树
        left=True
        if  i>0:
            left=self.VerifySquenceOfBST(sequence[0:i])
        # 判断 右子树是否为二叉树
        right=True
        if i<length-1:
            right=self.VerifySquenceOfBST(sequence[i:-1])
        return left and right


# -*- coding:utf-8 -*-
class Solution2:
    def VerifySquenceOfBST(self, sequence):
        # write code here
        if sequence == []:
            return False
        rootNum = sequence[-1]
        del sequence[-1]
        index = None
        for i in range(len(sequence)):
            if index == None and sequence[i] > rootNum:
                index = i
            if index != None and sequence[i] < rootNum:
                return False
        if sequence[:index] == []:
            leftRet = True
        else:
            leftRet = self.VerifySquenceOfBST(sequence[:index])
        if sequence[index:] == []:
            rightRet = True
        else:
            rightRet = self.VerifySquenceOfBST(sequence[index:])

        return leftRet and rightRet
```

___



## 30.二叉树中和为某一值的路径

**输入一颗二叉树的跟节点和一个整数，打印出二叉树中结点值的和为输入整数的所有路径。路径定义为从树的根结点开始往下一直到叶结点所经过的结点形成一条路径。(注意: 在返回值的list中，数组长度大的数组靠前)**

```python
"""
递归先序遍历树， 把结点加入路径。
若该结点是叶子结点则比较当前路径和是否等于期待和。
弹出结点，每一轮递归返回到父结点时，当前路径也应该回退一个结点

"""
# -*- coding:utf-8 -*-
# class TreeNode:
#     def __init__(self, x):
#         self.val = x
#         self.left = None
#         self.right = None
import copy


class Solution:
    # 返回二维列表，内部每个列表表示找到的路径
    def FindPath(self, root, expectNumber):
        # write code here
        if root == None:
            return []

        ret = []
        support = [root]
        supportArrayList = [[root.val]]

        while support:
            tmpNode = support[0]
            tmpArrayList = supportArrayList[0]
            if tmpNode.left == None and tmpNode.right == None:
                if sum(tmpArrayList) == expectNumber:
                    ret.insert(0, tmpArrayList)

            if tmpNode.left:
                support.append(tmpNode.left)
                newTmpArrayList = copy.copy(tmpArrayList)
                newTmpArrayList.append(tmpNode.left.val)
                supportArrayList.append(newTmpArrayList)
            if tmpNode.right:
                support.append(tmpNode.right)
                newTmpArrayList = copy.copy(tmpArrayList)
                newTmpArrayList.append(tmpNode.right.val)
                supportArrayList.append(newTmpArrayList)

            del supportArrayList[0]
            del support[0]

        return ret



# -*- coding:utf-8 -*-
# class TreeNode:
#     def __init__(self, x):
#         self.val = x
#         self.left = None
#         self.right = None
class Solution2:
    # 返回二维列表，内部每个列表表示找到的路径
    def FindPath(self, root, expectNumber):
        # write code here
        if not root:
            return []

        result = []

        def FindPathMain(root, path, currentSum):
            currentSum += root.val

            path.append(root)
            isLeaf = root.left == None and root.right == None

            if currentSum == expectNumber and isLeaf:
                onePath = []
                for node in path:
                    onePath.append(node.val)
                result.append(onePath)

            if currentSum < expectNumber:
                if root.left:
                    FindPathMain(root.left, path, currentSum)
                if root.right:
                    FindPathMain(root.right, path, currentSum)

            path.pop()

        FindPathMain(root, [], 0)

        return result
```

___



## 31.二叉搜索树与双向链表

**输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的双向链表。要求不能创建任何新的结点，只能调整树中结点指针的指向。**

```python 
# -*- coding:utf-8 -*-
# class TreeNode:
#     def __init__(self, x):
#         self.val = x
#         self.left = None
#         self.right = None
class Solution:
    def Convert(self, pRootOfTree):
        # write code here
        if pRootOfTree == None:
            return None

        def find_right(node):
            while node.right:
                node = node.right
            return node

        leftNode = self.Convert(pRootOfTree.left)
        rightNode = self.Convert(pRootOfTree.right)

        retNode = leftNode

        if leftNode:
            leftNode = find_right(leftNode)
        else:
            retNode = pRootOfTree

        pRootOfTree.left = leftNode
        pRootOfTree.right = rightNode

        if leftNode != None:
            leftNode.right = pRootOfTree
        if rightNode != None:
            rightNode.left = pRootOfTree

        return retNode
```