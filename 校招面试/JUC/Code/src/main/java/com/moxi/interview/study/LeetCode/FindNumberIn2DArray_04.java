package com.moxi.interview.study.LeetCode;

/**
 * 二维数组中的查找
 *
 * @author: 陌溪
 * @create: 2020-06-26-15:32
 */
public class FindNumberIn2DArray_04 {
//    public static boolean findNumberIn2DArray(int[][] matrix, int target) {
//        int [] oneLine = matrix[0];
//        int lineIndex = -1;
//        // 找出比那个大
//        for(int a=1; a<oneLine.length; a++) {
//            if(oneLine[a-1] <= target && oneLine[a] > target) {
//                lineIndex = a;
//                break;
//            }
//        }
//        // 比最大的还大时
//        if(target >= oneLine[oneLine.length - 1]) {
//            lineIndex = oneLine.length;
//        }
//
//        for(int a=0;a<matrix[lineIndex].length; a++) {
//            if(matrix[a][lineIndex] == target) {
//                return true;
//            }
//        }
//        return false;
//    }

    public static boolean findNumberIn2DArray(int[][] matrix, int target) {
        //特殊处理
        if(matrix.length == 0 || matrix[0].length == 0){
            return false;
        }

        //从右上开始查找
        int row = matrix.length;    //行
        int col = matrix[0].length;     //列

        int i = 0;  //右上角元素的横坐标
        int j = col - 1;    //右上角元素的纵坐标

        while(i < row && j >= 0){
            if(matrix[i][j] < target){
                //当前元素小于目标元素，即向下一行移动
                i++;
            }else if(matrix[i][j] > target){
                //当前元素大于目标元素，即向左侧移动
                j--;
            }else{
                return true;
            }
        }

        //即不存在目标元素
        return false;
    }

    public static void main(String[] args) {
        int [][] array = new int[][]{
                {-5}};
        System.out.println(findNumberIn2DArray(array, -5));
    }
}
