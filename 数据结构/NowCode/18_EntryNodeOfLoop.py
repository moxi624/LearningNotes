#  链表中环的入口结点
# 给一个链表，若其中包含环，请找出该链表的环的入口结点，否则，输出null
class ListNode:
    def __init__(self, x):
        self.val = x
        self.next = None

class Solution:
    def EntryNodeOfLoop(self, pHead):
        # 首先需要定义两个指针，其中一个快，跳两步，一个慢跳一步。
        # 循环跳
        # 要么是快的指针 为 none（没有环），要么是快慢指针相等（有环）。
        if pHead == None:
            return None
        # 定义两个指针，一个快的一个慢的。
        fastPointer = pHead
        slowPointer = pHead
        # 当快指针存在时，而且快指针的结点指向的下一个也存在
        while fastPointer and fastPointer.next:
            # 那么让快指针走两步
            fastPointer = fastPointer.next.next
            # 让慢指针走一步
            slowPointer = slowPointer.next
            # 如果慢指针等于快指针时，那么就说明这个链表中有环。有环的话那么就跳出，break
            if fastPointer == slowPointer:
                break
        # 如果说两个指针没有相等的时候，快指针就已经走到链表的尽头了，说明这个链表没有环。那么就返回None。
        if fastPointer == None or fastPointer.next == None:
            return None

        # 如果slow 走了 l 的长度 那么 fast 就走了 2l 的长度
        # 假设 从开始到入口点的长度是 s；slow 在环里面走的长度是 d

        # 那么  L = s + d
        # 假设 环内 slow 没走的 长度 是 m; fast 走的长度是多少
        # fast 走的长度 就是 ( m + d ) * n + d + s = 2 L
        # 带入 ( m + d ) * n + d + s = 2 （s + d ）
        # s = m + (n-1)(m+d)
        # 有环的话，那么就让快指针从头开始走，这次一次走一步，
        fastPointer = pHead
        # 此时慢指针还在环里走着，没有走到结点
        while fastPointer != slowPointer:
            fastPointer = fastPointer.next
            slowPointer = slowPointer.next
        # 当两个指针相等时，就会相遇，这时返回一个指针的值，就为 入口结点处。
        return fastPointer

if __name__ == '__main__':
    print(Solution().LastRemaining_Solution(5, 2))
