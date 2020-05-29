class TreeNode(object):
    def __init__(self, x):
        self.val = x
        self.left = None
        self.right = None

# 递归先序遍历
def preOrderRecusive(root):
    if root == None:
        return None
    print(root.val, end=" ")
    preOrderRecusive(root.left)
    preOrderRecusive(root.right)

# 递归中序遍历
def midOrderRecusive(root):
    if root == None:
        return None
    midOrderRecusive(root.left)
    print(root.val, end=" ")
    midOrderRecusive(root.right)

# 递归后序遍历
def latOrderRecusive(root):
    if root == None:
        return None
    latOrderRecusive(root.left)
    latOrderRecusive(root.right)
    print(root.val, end=" ")

if __name__ == '__main__':
    t1 = TreeNode(1)
    t2 = TreeNode(2)
    t3 = TreeNode(3)
    t4 = TreeNode(4)
    t5 = TreeNode(5)
    t6 = TreeNode(6)
    t7 = TreeNode(7)
    t8 = TreeNode(8)
    t1.left = t2
    t1.right = t3
    t2.left = t4
    t2.right = t5
    t3.left = t6
    t3.right = t7
    t6.left = t8

    preOrderRecusive(t1)
    print()
    midOrderRecusive(t1)
    print()
    latOrderRecusive(t1)