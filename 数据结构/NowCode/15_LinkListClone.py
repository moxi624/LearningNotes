# 复杂链表的复制
# 输入一个复杂链表（每个节点中有节点值，以及两个指针，一个指向下一个节点，另一个特殊指针random指向一个随机节点），
# 请对此链表进行深拷贝，并返回拷贝后的头结点。（注意，输出结果中请不要返回参数中的节点引用，否则判题程序会直接返回空）

# 链表结构
class RandomListNode:
    def __init__(self, x):
        self.label = x
        self.next = None
        self.random = None


class Solution:
    # 返回合并后列表
    def Clone(self, pHead):
        if pHead == None:
            return None
        pTmp = pHead
        # 复制一个一样的node，并且添加到之前的链表的每一个node后面
        while pTmp:
            # 创建一个和原来一样的node
            node = RandomListNode(pTmp.laebl)
            node.next = pTmp.next
            # 将原来的指向刚刚创建的节点
            pTmp.next = node
            # 同时移动被复制的节点
            pTmp = node.next

        # 实现新建node的random的指向
        pTmp = pHead
        while pHead:
            # 将复制节点的random，指向 它Random的next
            if pTmp.random:
                pHead.next.random = pTmp.random.next
            pTmp = pTmp.next.next

        # 断开原来的node 和 新 node 之间的连接
        pTmp = pHead
        newHead = pHead.next
        pNewTmp = pHead.next
        while pTmp:
            pTmp.next = pTmp.next.next
            if pNewTmp.next:
                pNewTmp.next = pNewTmp.next.next
            pTmp = pTmp.next
            pNewTmp = pNewTmp.next
        return newHead

if __name__ == '__main__':
    print()
