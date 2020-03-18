package com.moxi.interview.study.lq;

import java.util.Scanner;

/**
 * @author: 陌溪
 * @create: 2020-03-13-18:38
 */
public class Demo3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Integer count = sc.nextInt();
        Scanner sc1 = new Scanner(System.in);
        String str = sc1.nextLine();
        String [] strArray = str.split(" ");
        for(int a=0; a<strArray.length; a++) {
            System.out.println(strArray[a]);
        }
    }
}
