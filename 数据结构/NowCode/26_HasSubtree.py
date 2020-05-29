class TreeNode:
    def __init__(self, x):
        self.val = x
        self.left = None
        self.right = None
class Solution:
    def HasSubtree(self, pRoot1, pRoot2):
        if pRoot1 == None or pRoot2 == None:
            return None

        def hasEqual(pRoot1, pRoot2):
            if pRoot2 == None:
                return True
            if pRoot1 == None:
                return None
            if pRoot1.val == pRoot2.val:
                if pRoot2.left == None:
                    leftEqual = True
                else:
                    leftEqual = hasEqual(pRoot1.left, pRoot2.left)
                if pRoot2.right == None:
                    rightEqual = True
                else:
                    rightEqual = hasEqual(pRoot1.right, pRoot2.right)
                # 左边相等和右边相等的时候，才返回
                return leftEqual and rightEqual
            return False

        ret = hasEqual(pRoot1, pRoot2)
        if ret:
            return True

        ret = self.HasSubtree(pRoot1.left, pRoot2)
        if ret:
            return True
        ret = self.HasSubtree(pRoot1.right, pRoot2)
        return  ret