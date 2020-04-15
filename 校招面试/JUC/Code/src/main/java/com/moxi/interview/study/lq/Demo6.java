package com.moxi.interview.study.lq;

import java.util.Scanner;

/**
 * 给定一个单词，请使用凯撒密码将这个单词加密。
 * 　　凯撒密码是一种替换加密的技术，单词中的所有字母都在字母表上向后偏移3位后被替换成密文。即a变为d，b变为e，...，w变为z，x变为a，y变为b，z变为c。
 * 　　例如，lanqiao会变成odqtldr。
 * @author: 陌溪
 * @create: 2020-04-13-20:28
 */
public class Demo6 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String key = sc.nextLine();
        char [] chars = key.toCharArray();
        String result = "";
        for (int a=0; a<chars.length; a++) {
            result += (char)(chars[a] + 3);
        }
        System.out.println(result);
    }
}
