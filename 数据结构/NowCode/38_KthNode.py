# 二叉排序树的第K小的节点
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