# 输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。如果是则输出Yes,否则输出No。假设输入的数组的任意两个数字都互不相同。
class Solution:
    def VerifySquenceOfBST(self, sequence):
        if sequence == []:
            return False

        # 找ROOT节点，也就是最后一个
        root = sequence[-1]
        # 删除队列中的末尾节点
        del sequence[-1]
        # 寻找出划分的节点
        index = None
        for i in range(len(sequence)):
            # 只寻找一次，就不进入了
            if index == None and sequence[i] > root:
                index = i
            # 当我们找到一个大的数，然后往后又找到一个更小的数，那么就无法组成二叉搜索树
            if index != None and sequence[i] < root:
                return False

        if sequence[:index] == []:
            left = True
        else:
            # 寻找左子树和右子树
            left = self.VerifySquenceOfBST(sequence[:index])
        if sequence[index:] == []:
            right = True
        else:
            right = self.VerifySquenceOfBST(sequence[index:])
        # 返回结果
        return left and right