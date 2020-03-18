package com.moxi.interview.study.lq;

import java.util.Scanner;

/**
 * @author: 陌溪
 * @create: 2020-03-13-18:58
 */
public class Demo4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String keyword = sc.next();
        String [] str = keyword.split("[a,e,i,o,u]");
        if(str.length != 2) {
            System.out.println("no");
        } else {
            if(keyword.indexOf(str[0]) != 0 || keyword.indexOf(str[1]) + str[1].length() == keyword.length()) {
                System.out.println("no");
            } else {
                System.out.println("yes");
            }
        }
    }
}
