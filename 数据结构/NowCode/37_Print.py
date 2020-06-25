class TreeNode:
    def __init__(self, x):
        self.val = x
        self.left = None
        self.right = None
class Solution:
    # 返回二维列表[[1,2],[4,5]]
    def Print(self, pRoot):
        if pRoot == None:
            return []
        queue1 = [pRoot]
        queue2 = []
        ret = []
        while queue1 or queue2:
            if queue1:
                tmpRet = []
                while queue1:
                    tmpNode = queue1.pop(0)
                    tmpRet.append(tmpNode.val)
                    if tmpNode.left:
                        queue2.append(tmpNode.left)
                    if tmpNode.right:
                        queue2.append(tmpNode.right)
                ret.append(tmpRet)
            if queue2:
                tmpRet = []
                while queue2:
                    tmpNode = queue2.pop(0)
                    tmpRet.append(tmpNode.val)
                    if tmpNode.left:
                        queue1.append(tmpNode.left)
                    if tmpNode.right:
                        queue1.append(tmpNode.right)
                ret.append(tmpRet)
        return ret


