#  按之字形顺序打印二叉树
class TreeNode:
    def __init__(self, x):
        self.val = x
        self.left = None
        self.right = None
class Solution:
    def Print(self, pRoot):
        if pRoot == None:
            return []
        # write code here
        stack1 = [pRoot]
        stack2 = []
        # 存储打印
        ret = []
        # 当1和2中有空时
        while stack1 or stack2:
            if stack1:
                tmpRet = []
                while stack1:
                    # 取出stack1中的一个节点
                    tmpNode = stack1.pop()
                    tmpRet.append(tmpNode.val)
                    # 把左右子树放到stack2中
                    if tmpNode.left:
                        stack2.append(tmpNode.left)
                    if tmpNode.right:
                        stack2.append(tmpNode.right)
                ret.append(tmpRet)

            if stack2:
                tmpRet = []
                while stack2:
                    # 取出stack1中的一个节点
                    tmpNode = stack2.pop()
                    tmpRet.append(tmpNode.val)
                    # 把左右子树放到stack2中
                    if tmpNode.right:
                        stack1.append(tmpNode.right)
                    if tmpNode.left:
                        stack1.append(tmpNode.left)
                ret.append(tmpRet)
        return ret