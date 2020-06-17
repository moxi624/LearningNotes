# 二叉树的下一个节点
class TreeLinkNode:
    def __init__(self, x):
        self.val = x
        self.left = None
        self.right = None
        self.next = None
class Solution:
    def GetNext(self, pNode):
        # 寻找右子树,如果存在就一直找右子树的最左边就是下一个节点
        if pNode.right:
            tmpNode = pNode.right
            while tmpNode.left:
                tmpNode = tmpNode.left
            return tmpNode
        else:
            tmpNode = pNode
            # 寻找父节点  没有右子树，就寻找它的父节点，一直找到它是父节点的左子树，打印父节点
            while tmpNode.next:
                # 判断父节点的左子树，是否是该节点，如果是直接返回父节点
                if tmpNode.next.left == tmpNode:
                    return tmpNode.next
                tmpNode = tmpNode.next
            return None

