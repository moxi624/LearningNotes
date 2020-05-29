class TreeNode(object):
    def __init__(self, x):
        self.val = x
        self.left = None
        self.right = None

########## 递归遍历 ###############

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

########## 非递归遍历 ###############

# 非递归先序遍历
def preOrder(root):
    if root == None:
        return None
    stack = []
    tempNode = root
    while tempNode != None or stack:
        print(tempNode.val, end=" ")
        stack.append(tempNode)
        tempNode = tempNode.left
        while tempNode == None and stack != []:
            tempNode = stack.pop()
            tempNode = tempNode.right

# 非递归中序遍历
def midOrder(root):
    if root == None:
        return None
    stack = []
    tempNode = root
    while tempNode != None or stack:
        stack.append(tempNode)
        tempNode = tempNode.left
        while tempNode == None and stack != []:
            tempNode = stack.pop()
            print(tempNode.val, end=" ")
            tempNode = tempNode.right

# 非递归后序遍历
def latOrder(root):
    if root == None:
        return None
    stack = []
    tempNode = root
    while tempNode != None or stack:
        stack.append(tempNode)
        tempNode = tempNode.left
        while tempNode == None and stack != []:
            # 后序遍历，pop的方式有变化，不能在右子树不为空的时候pop
            node = stack[-1] # 因此这里不出列
            tempNode = node.right
            # 当右节点没有的时候，才能够pop
            if node.right == None:
                print(node.val, end=" ")
                node = stack.pop()
                # 判断pop的节点，是否是上一个节点
                while stack and node == stack[-1].right:
                    node = stack.pop()
                    print(node.val, end=" ")

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
    t6.right = t8

    preOrderRecusive(t1)
    print()
    midOrderRecusive(t1)
    print()
    latOrderRecusive(t1)
    print()
    preOrder(t1)
    print()
    midOrder(t1)
    print()
    latOrder(t1)