# 反转链表
# 输入一个链表，反转链表后，输出新链表的表头。

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
    def ReverseList(self, pHead):
        if pHead == None:
            return None
        if pHead.next == None:
            return pHead

        leftPointer = pHead
        middlePointer = pHead.next
        rightPointer = pHead.next.next
        leftPointer.next = None

        while rightPointer != None:
            middlePointer.next = leftPointer

            leftPointer = middlePointer
            middlePointer = rightPointer
            rightPointer = rightPointer.next

        middlePointer.next = leftPointer

        return middlePointer

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
