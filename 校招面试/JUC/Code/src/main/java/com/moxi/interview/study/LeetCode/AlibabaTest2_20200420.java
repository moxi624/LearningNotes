package com.moxi.interview.study.LeetCode;

import java.util.Scanner;

/**
 * @author: 陌溪
 * @create: 2020-04-20-18:56
 */
public class AlibabaTest2_20200420 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int weight [] = new int [n];
        int path [][] = new int[n][n];
        for(int a=0; a< n; a++) {
            weight[a] = sc.nextInt();
        }
        for(int a=0; a< n-1; a++) {
            int node1 = sc.nextInt();
            int node2 = sc.nextInt();
            path[node1 - 1][node2 - 1] = 1;
            path[node2 - 1][node1 - 1] = 1;
        }

        for(int a=0; a< n; a++) {
            System.out.print(weight[a] + " ");
        }

        for(int a=0; a< n; a++) {
            for(int b=0; b< n; b++) {
                System.out.print(path[a][b] + " ");
            }
            System.out.println("");
        }

    }
}
