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
                # 将该元素移动到最新使用的
                index = self.array.index(key)
                del self.array[index]
                self.array.append(key)
                # 更新key
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