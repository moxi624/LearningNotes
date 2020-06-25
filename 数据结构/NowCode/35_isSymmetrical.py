class TreeNode:
    def __init__(self, x):
        self.val = x
        self.left = None
        self.right = None
class Solution:
    def isSymmetrical(self, pRoot):

        if pRoot == None:
            return True

        def isMirror(left, right):
            if left == None and right == None:
                return True
            elif left == None or right == None:
                return False
            if left.val != right.val:
                return False
            ret1 = isMirror(left.left, right.right)
            ret2 = isMirror(left.right, right.left)
            return ret1 and ret2

        return isMirror(pRoot.left, pRoot.right)