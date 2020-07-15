class TreeNode:
    def __init__(self, x):
        self.val = x
        self.left = None
        self.right = None
class Solution:
    # 返回镜像树的根节点
    # def Mirror(self, root):
    #     if root == None:
    #         return None
    #     # 处理左子树
    #     temp = root.left
    #     root.left = root.right
    #     root.right = temp
    #     self.Mirror(root.left)
    #     self.Mirror(root.right)

    def Mirror(self, root):
        if root == None:
            return None
        stack = [root]
        while stack:
            node = stack.pop()
            if node.left:
                stack.append(node.left)
            if node.right:
                stack.append(node.right)
            node.right,node.left = node.left, node.right
        return root
