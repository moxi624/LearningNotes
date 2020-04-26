# 链表中倒数第K个节点

## 描述

来源：

输入一个链表，输出该链表中倒数第k个结点。

## 思路

其实这个解法和我们的上一题类似，就是使用一个数组来存储每个节点，最后我们输出第 len(list) - K 个节点即可

```
        while node:
            list.append(node)
            node = node.next

        # K比链表长度大
        if k > len(list):
            return None

        return list[len(list) - k]
```

完整代码

```
# 链表中倒数第K个节点
# 输入一个链表，输出该链表中倒数第k个结点。

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
    def FindKthToTail(self, head, k):
        if k <= 0 or head == []:
            return None

        node = head
        list = []
        while node:
            list.append(node)
            node = node.next

        # K比链表长度大
        if k > len(list):
            return None

        return list[len(list) - k]

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

    print(Solution().FindKthToTail(l1, 1))
```

## 思路2

第二种方式就是我们需要使用两个指针，第一个指针先走K步，然后两个指针在同时行走，最后当第一个指针到达终点的时候，第二个指针就是倒数第K个值。

![image-20200425103015322](images/image-20200425103015322.png)

完整代码

```
class Solution:

    def FindKthToTail(self, head, k):
        if k <= 0 or head == []:
            return None
        list = []
        first = head
        second = head
        # 先让第一个节点走 K步
        for i in range(0, k):
        	# 判断第一个是否走完
            if first == None:
                return None
            first = first.next

        # 然后两个节点在继续走，当first走到头的时候，second就是倒数第K个节点
        while first:
            first = first.next
            second = second.next
        return second
```

