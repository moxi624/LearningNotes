package com.atguigu.java.chapter13;

/**
 * @author: 陌溪
 * @create: 2020-07-11-11:16
 */
public class StringIntern {
    public static void main(String[] args) {
        String s = new String("1");
        s = s.intern();
        String s2 = "1";
        System.out.println(s == s2); // true

        String s3 = new String("1") + new String("1");
        s3.intern();
        String s4 = "11";
        System.out.println(s3 == s4); // true
    }
}
