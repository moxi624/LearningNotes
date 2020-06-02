# 输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的双向链表。要求不能创建任何新的结点，只能调整树中结点指针的指向。
class TreeNode:
    def __init__(self, x):
        self.val = x
        self.left = None
        self.right = None
class Solution:
    def Convert(self, pRootOfTree):
        def find_right(node):
            while node.right:
                node = node.right
            return node

        if pRootOfTree == None:
            return None

        leftNode = self.Convert(pRootOfTree.left)
        rightNode = self.Convert(pRootOfTree.right)
        retNode = leftNode
        if leftNode:
            leftNode = find_right(leftNode)
            retNode = leftNode
        else:
            retNode = pRootOfTree

        pRootOfTree.left = leftNode
        pRootOfTree.right = rightNode

        if leftNode != None:
            leftNode.right = pRootOfTree
        if rightNode != None:
            rightNode.left = pRootOfTree

        return retNode