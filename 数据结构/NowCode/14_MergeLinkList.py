# 合并两个链表
# 输入两个单调递增的链表，输出两个链表合成后的链表，当然我们需要合成后的链表满足单调不减规则。

# 链表结构
class ListNode:
    def __init__(self, x):
        self.val = x
        self.next = None

# 打印链表
def printChain(head):
    node = head
    while node:
        print(node.val)
        node = node.next

class Solution:
    # 返回合并后列表
    def Merge(self, pHead1, pHead2):

        if pHead1 == None:
            return pHead2
        if pHead2 == None:
            return pHead1

        # 得到最小的一个头结点
        newHead = pHead1 if pHead1.val < pHead2.val else pHead2
        # 链表1上的指针 和 链表2上的指针
        pTmp1 = pHead1
        pTmp2 = pHead2
        if newHead == pTmp1:
            pTmp1 = pTmp1.next
        if newHead == pTmp2:
            pTmp2 = pTmp2.next

        # 前面的指针
        previousPointer = newHead

        # 链表1 和 链表2 都不为空的时候，开始合并
        while pTmp1 and pTmp2:
            # 找出最小值，放在previousPointer指针后面
            if pTmp1.val < pTmp2.val:
                previousPointer.next = pTmp1
                previousPointer = pTmp1
                pTmp1 = pTmp1.next
            else:
                previousPointer.next = pTmp2
                previousPointer = pTmp2
                pTmp2 = pTmp2.next

        if pTmp1 == None:
            previousPointer.next = pTmp2
        if pTmp2 == None:
            previousPointer.next = pTmp1

        return newHead

if __name__ == '__main__':
    # 创建链表
    l1 = ListNode(1)
    l2 = ListNode(2)
    l3 = ListNode(3)
    l4 = ListNode(4)
    l5 = ListNode(5)

    l1.next = l2
    l2.next = l3
    l3.next = l4
    l4.next = l5

    print(Solution().ReverseList(l1))
