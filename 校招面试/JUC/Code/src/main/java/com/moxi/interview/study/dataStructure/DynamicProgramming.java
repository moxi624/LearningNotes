package com.moxi.interview.study.dataStructure;

/**
 * 动态规划
 * 解决 0-1背包问题
 *
 * @author: 陌溪
 * @create: 2020-04-18-15:00
 */
public class DynamicProgramming {
    public static void main(String[] args) {
        // 物品的重量
        int w [] = {1, 4, 3};

        // 物品的价值
        int val [] = {1500, 3000, 2000};

        // 背包的容量
        int m = 4;

        // 物品的个数
        int n = val.length;

        // 创建二维数组  v[i][j] 表示在前i个物品中，可以装入容量为j的背包中的商品最大值
        int [][] v = new int[n+1][m+1];

        // 为了记录放入商品的情况，我们定义一个二维数组
        int [][] path = new int[n+1][m+1];

        // 初始化第一行  和 第一列，这里在本程序中可以不去处理，因为数组默认就是0
        for(int i = 0; i<v.length; i++) {
            v[i][0] = 0;
        }
        for(int j = 0; j<v[0].length; j++) {
            v[0][j] = 0;
        }

        // 根据前面的公式，来进行动态规划
        for(int i=1; i<v.length; i++) {
            // 不处理第一行 和 第一列
            for(int j=1; j<v[0].length; j++) {
                // 公式
                if(w[i-1] > j) {
                    v[i][j] = v[i-1][j];
                } else {
                    // 因为我们的i从1开始的，因此公式需要调整成 i -> i-1
//                     v[i][j] = Math.max(v[i-1][j], val[i-1] + v[i-1][j - w[i -1]]);

                    // 为了记录商品存放的背包的情况，我们不能直接的使用上面的公式，需要使用if else来体现公式
                    if(v[i-1][j] < (val[i-1] + v[i-1][j - w[i -1]])) {
                        v[i][j] = (val[i-1] + v[i-1][j - w[i -1]]);
                        // 把当前的情况记录到path
                        path[i][j] = 1;
                    } else {
                        v[i][j] = v[i-1][j];
                    }
                }
            }
        }

        // 输出一下
        for(int i = 0; i< v.length; i++) {
            for(int j = 0; j<v[0].length; j++) {
                System.out.print(v[i][j] + " ");
            }
            System.out.println("");
        }

        // 输出最后我们是放入的那些商品
        // 这样遍历，会把所有放入情况都用到，其实我们只需要最后的放入情况
//        for(int i = 0; i< path.length; i++) {
//            for(int j = 0; j<path[0].length; j++) {
//                if(path[i][j] == 1) {
//                    System.out.print(i + " ");
//                }
//            }
//        }

        // 求出最后的一个点
        int i = path.length -1;
        int j = path[0].length -1;
        // 从path的最后开始查找
        while (i> 0 && j>0) {
            if(path[i][j] == 1) {
                System.out.print(i + " ");
                j -= w[i-1];
            }
            // 找到一个 i需要减1
            i --;
        }
    }
}
