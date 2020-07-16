# 双向List
class Node(object):
    def __init__(self, val=None, key=None, left=None, right=None):
        # 键
        self.key = key
        # 值
        self.val = val
        self.left = left
        self.right = right

class LRUCache(object):
    def __init__(self, capacity):
        self.capacity = capacity
        self.map = {}
        self.array = []
        self.size = 0
        self.root = None

    def get(self, key):
        if self.map.get(key):
            node = self.map.get(key)
            # 判断要删除的是不是根节点
            if key != self.root.key:
                # 移除使用的节点，将其移动到最后
                leftNode = node.left
                rightNode = node.right
                leftNode.right = rightNode
                rightNode.left = leftNode

                # 将该节点放到最后
                leftRoot = self.root.left
                leftRoot.right = node
                node.left = leftRoot
                node.right = self.root
                self.root.left = node
                return node.val
            else:
                leftNode = node.left
                rightNode = node.right
                leftNode.right = rightNode
                rightNode.left = leftNode

                # 将该节点放到最后
                leftRoot = self.root.left
                rightRoot = self.root.right
                leftRoot.right = node
                node.left = leftRoot
                node.right = rightRoot
                self.root = rightRoot
                return node.val

        else:
            return -1

    def put(self, key, value):
        if self.size < self.capacity:
            if self.size == 0:
                # 插入的是第一个节点，那么就是root节点，双向循环链表
                node = Node()
                node.key = key
                node.val = value
                node.left = node
                node.right = node
                self.root = node
                self.map[key] = node
                self.size += 1
            elif self.map.get(key):
                # 将该元素移动到最新使用的
                node = self.map.get(key)
                node.val = value

                # 判断要删除的是不是根节点
                if key != self.root.key:
                    # 移除使用的节点，将其移动到最后
                    leftNode = node.left
                    rightNode = node.right
                    leftNode.right = rightNode
                    rightNode.left = leftNode

                    # 将该节点放到最后
                    leftRoot = self.root.left
                    leftRoot.right = node
                    node.left = leftRoot
                    node.right = self.root
                    self.root.left = node
                    self.map[key] = node
                else:
                    leftNode = node.left
                    rightNode = node.right
                    leftNode.right = rightNode
                    rightNode.left = leftNode

                    # 将该节点放到最后
                    leftRoot = self.root.left
                    rightRoot = self.root.right
                    leftRoot.right = node
                    node.left = leftRoot
                    node.right = rightRoot
                    self.root = rightRoot
                    self.map[key] = node

                # # 将该元素移动到最新使用的
                # node = self.map.get(key)
                # # 更新元素中的值
                # node.val = value
                # node.key = key
                # # 移除使用的节点，将其移动到最后
                # leftNode = node.left
                # rightNode = node.right
                # leftNode.right = rightNode
                # rightNode.left = leftNode
                #
                # # 将该节点放到最后
                # leftRoot = self.root.left
                # leftRoot.left = node
                # node.left = leftRoot
                # node.right = self.root
                # self.root.left = node


            else:
                leftRoot = self.root.left
                node = Node()
                node.val = value
                node.key = key
                node.left = leftRoot
                node.right = self.root
                leftRoot.right = node
                self.root.left = node
                self.map[key] = node
                self.size += 1
        else:
            # 以下代码是淘汰策略，当不需要淘汰的时候
            if self.map.get(key):
                # 将该元素移动到最新使用的
                node = self.map.get(key)

                leftRoot = self.root.left
                rightRoot = self.root.right

                # 更新元素中的值
                node.val = value
                # 移除使用的节点，将其移动到最后
                leftNode = node.left
                rightNode = node.right
                leftNode.right = rightNode
                rightNode.left = leftNode

                # 将该节点放到最后
                leftRoot = self.root.left
                leftRoot.left = node
                node.left = leftRoot
                node.right = rightRoot
                self.root.left = node
                self.root = rightNode

            else:
                deleteKey = self.root.key
                del self.map[deleteKey]
                leftRoot = self.root.left
                rightRoot = self.root.right
                node = Node()
                node.key = key
                node.val = value
                node.left = leftRoot
                node.right = rightRoot
                leftRoot.right = node
                rightRoot.left = node
                self.root = rightRoot
                self.map[key] = node
                self.size += 1


if __name__ == '__main__':
    cache = LRUCache(2)
    cache.put(1, 1)
    cache.put(2, 2)
    print(cache.get(1))
    cache.put(3, 3)
    print(cache.get(2))
    cache.put(4, 4)
    print(cache.get(1))
    print(cache.get(3))
    print(cache.get(4))