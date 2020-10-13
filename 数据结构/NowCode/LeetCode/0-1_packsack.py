
class Solution:
    # 用 [0 .... index] 的物品，来填充容积为 capacity的背包的最大价值
    map = {}
    # 采用记忆化搜索
    def bestValue(self, weight, value, index, capacity):
        # 当物品无法放入的时候，或者没有价值
        if index < 0 or capacity <= 0:
            return 0

        # 记忆化搜索，从map中获取
        if self.map.get(str(index) + "_" + str(capacity)) != None:
            return self.map.get(index + "_" + capacity)

        # 当这个物品不放入的时候
        res = self.bestValue(weight, value, index-1, capacity)

        # 当这个物品放入的时候, 首先判断物品能否放入背包
        if capacity >= weight[index]:
            # 价值该物品存入背包的时候
            res2 = value[index] + self.bestValue(weight, value, index-1, capacity - weight[index])
            # 两种策略，找出价值最大的结果
            res = max(res, res2)

        self.map[str(index) + "_" + str(capacity)] = res
        return res

    # 采用动态规划
    def bestValue2(self, weight, value, index, capacity):
        n = len(weight)
        if n == 0:
            return 0
        for j in range(capacity):
            if j >= weight[0]:
                self.map["0_" + str(j)] = weight[0]
            else:
                self.map["0_" + str(j)] = 0

        for i in range(1, n):
            for j in range(capacity):
                # 两种策略， 假设不放入到背包中
                res = self.map.get(str(i-1) + "_" + str(j))

                # 假设物品能够放入到背包中
                if j >= weight[i]:
                    res2 = value[i] + self.map.get(str(i-1) + "_" + str(j-weight[i]))
                    res = max(res, res2)
                # 重新调整数组
                self.map[str(i) + "_" + str(j)] = res
        return self.map.get(str(n-1) + "_" + str(capacity))

if __name__ == '__main__':
    weight = [1, 2, 3]
    value = [6, 10, 12]
    print(Solution().bestValue2(weight, value, len(weight) - 1, 5))
