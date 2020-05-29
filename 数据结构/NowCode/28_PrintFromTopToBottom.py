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