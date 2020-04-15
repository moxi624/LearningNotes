package com.moxi.interview.study.lq;

import java.util.Scanner;

/**
 * 问题描述
 * 　　将LANQIAO中的字母重新排列，可以得到不同的单词，如LANQIAO、AAILNOQ等，注意这7个字母都要被用上，单词不一定有具体的英文意义。
 * 　　请问，总共能排列如多少个不同的单词。
 * @author: 陌溪
 * @create: 2020-04-13-20:12
 */
public class Demo5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        int b = sc.nextInt();
        int c = sc.nextInt();
        int d = sc.nextInt();
        int result = 0;
        for (int num=1; num <=a; num ++) {
            if(num % a != 0 && num % b != 0 && num % c != 0) {
                result++;
            }
        }
        System.out.println(result);
    }
}
