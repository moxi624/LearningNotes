package com.atguigu.java.chapter05;

/**
 * 演示栈中的异常：StackOverflowError
 * @author: 陌溪
 * @create: 2020-07-05-17:11
 */
public class StackErrorTest {
    private static int count = 1;
    public static void main(String[] args) {
        System.out.println(count++);
        main(args);
    }
}
