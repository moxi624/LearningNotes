class TreeNode:
    def __init__(self, x):
        self.val = x
        self.left = None
        self.right = None
class Solution:
    # 序列化
    def Serialize(self, root):
        retList = []
        def preOrder(root):
            if root == None:
                # 找到为空的节点，插入 #
                retList.append("#")
                return None

            # 否者插入该节点
            retList.append(str(root.val))
            preOrder(root.left)
            preOrder(root.right)
        preOrder(root)
        return ' '.join(retList)

    # 反序列化
    def Deserialize(self, s):
        retList = s.split(" ")
        if retList == None:
            return None
        def dePreOrder():
            rootVal = retList.pop(0)
            if rootVal == "#":
                return None
            node = TreeNode(rootVal)
            leftNode = dePreOrder()
            rightNode = dePreOrder()
            node.left = leftNode
            node.right = rightNode
            return node
        return dePreOrder()