# 希尔排序

## 思想

希尔排序的是基于插入排序的基础上来做的，我们都知道插入排序需要比较的次数比较多，那么希尔排序就是利用的一个间隔，用于降低比较后，元素移动的次数。

[希尔排序的图解](http://moguit.cn/#/info?blogUid=51efadc72d2f4d66a412fd8ef1a30fdc)

## Python 代码

```
# 希尔排序
class Solution:
    def shellSort(self, arrlist):
        arrayLen = len(arrlist)
        h = 1
        # 工业界认定的求 增量的方法
        while h < arrayLen/3:
            h = h*3 + 1
        # 插入排序的方法，判断是不是后面一个比前面的要小
        # 如果是则交换
        while h >= 1:
            for i in range(h, arrayLen):
                j = i
                while j >= h and arrlist[j] < arrlist[j - h]:
                    arrlist[j], arrlist[j-h] = arrlist[j-h], arrlist[j]
                    j-=h
            # 除以3
            h = h//3
        return arrlist

if __name__ == '__main__':
    print(Solution().shellSort([1,8,3,2,6,9]))
```

## Java代码

```
package com.moxi.interview.study.nowCode;

/**
 * 希尔排序
 *
 * @author: 陌溪
 * @create: 2020-06-25-10:43
 */
public class ShellSort_1 {
    public static int[] shellSort(int[] arrList) {
        int arrLen = arrList.length;
        int h = 1;
        while (h > arrLen /3){
            h = h*3 + 1;
        }

        while(h >=1) {
            for (int i = h; i < arrLen; i++) {
                int j = i;
                while(j >=h && arrList[j] < arrList[j - h]) {
                    int temp = arrList[j];
                    arrList[j] = arrList[j-h];
                    arrList[j-h] = temp;
                    j -= h;
                }
            }
            h /= 3;
        }
        return arrList;
    }
    public static void main(String[] args) {
        int [] arrList = new int[]{1,8,3,2,6,9};
        int [] resultList = shellSort(arrList);
        for (int a=0; a<resultList.length; a++) {
            System.out.print(resultList[a] + " ");
        }
    }
}
```

