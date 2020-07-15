# LRU缓存机制

## 来源

https://leetcode-cn.com/problems/lru-cache/

## 描述

运用你所掌握的数据结构，设计和实现一个  LRU (最近最少使用) 缓存机制。它应该支持以下操作： 获取数据 get 和 写入数据 put 。

获取数据 get(key) - 如果关键字 (key) 存在于缓存中，则获取关键字的值（总是正数），否则返回 -1。
写入数据 put(key, value) - 如果关键字已经存在，则变更其数据值；如果关键字不存在，则插入该组「关键字/值」。当缓存容量达到上限时，它应该在写入新数据之前删除最久未使用的数据值，从而为新的数据值留出空间。

>**进阶:** 你是否可以在 **O(1)** 时间复杂度内完成这两种操作？

```bash
LRUCache cache = new LRUCache( 2 /* 缓存容量 */ );

cache.put(1, 1);
cache.put(2, 2);
cache.get(1);       // 返回  1
cache.put(3, 3);    // 该操作会使得关键字 2 作废
cache.get(2);       // 返回 -1 (未找到)
cache.put(4, 4);    // 该操作会使得关键字 1 作废
cache.get(1);       // 返回 -1 (未找到)
cache.get(3);       // 返回  3
cache.get(4);       // 返回  4
```



## 代码

使用数组+哈希来实现，如果要时间复杂度为O(N)，可以使用 双向链表 + Hash来记录

```python
class LRUCache(object):

    def __init__(self, capacity):
        self.capacity = capacity
        self.map = {}
        self.array = []
        self.size = 0

    def get(self, key):
        if self.map.get(key):
            index = self.array.index(key)
            # 将该元素移动到最新使用的
            del self.array[index]
            self.array.append(key)
            return self.map.get(key)
        else:
            return -1

    def put(self, key, value):
        if self.size < self.capacity:
            if self.map.get(key):
                self.map[key] = value
            else:
                self.map[key] = value
                self.array.append(key)
                self.size += 1
        else:
            if self.map.get(key):
                # 将该元素移动到最新使用的
                index = self.array.index(key)
                del self.array[index]
                self.array.append(key)
                # 更新key
                self.map[key] = value

            else:
                # 淘汰第一个，在最后插入元素
                deleteKey = self.array.pop(0)
                del self.map[deleteKey]
                self.map[key] = value
                self.array.append(key)

if __name__ == '__main__':
    cache = LRUCache(2)
    print(cache.get(2))
    cache.put(2, 6)
    print(cache.get(1))
    cache.put(1, 5)
    cache.put(1, 2)
    print(cache.get(1))
    print(cache.get(2))
```

