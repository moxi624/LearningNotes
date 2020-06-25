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
