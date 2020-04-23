# 替换空格
# 请实现一个函数，将一个字符串中的每个空格替换成“%20”。例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。
class Solution:
    # s 源字符串
    def replaceSpace(self, s):
        return s.replace(' ', '%20')

    def replaceSpace2(self, s):
        strLen = len(s)
        aaa = []
        for i in range(0, strLen):
            if s[i] == " ":
                aaa.append("%")
                aaa.append("2")
                aaa.append("0")
            else:
                aaa.append(s[i])
        return ''.join(aaa)

if __name__ == '__main__':
    print(Solution().replaceSpace2('We Are Happy'))