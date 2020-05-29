# 输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
# 例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}，则重建二叉树并返回。
class TreeNode:
    def __init__(self, x):
        self.val = x
        self.left = None
        self.right = None
class Solution:
    # 返回构造的TreeNode根节点
    def reConstructBinaryTree(self, pre, tin):
        # 取出pre的值
        if not pre or not tin:
            return None
        if len(pre) != len(tin):
            return None
        root = pre[0]
        # 新建立节点
        rootNode = TreeNode(root)
        # 找出这个节点，在中序遍历对应的角标
        pos = tin.index(root)
        # 取出中序遍历 左子树
        tinLeft = tin[:pos]
        # 取出中序遍历 右子树
        tinRight = tin[pos+1:]
        # 取出先序遍历的 左子树
        preLeft = pre[1:pos+1]
        # 取出先序遍历的 右子树
        preRight = pre[pos+1:]

        leftNode = self.reConstructBinaryTree(preLeft, tinLeft)
        rightNode = self.reConstructBinaryTree(preRight, tinRight)

        if leftNode:
            rootNode.left = leftNode
        if rightNode:
            rootNode.right = rightNode
        return rootNode