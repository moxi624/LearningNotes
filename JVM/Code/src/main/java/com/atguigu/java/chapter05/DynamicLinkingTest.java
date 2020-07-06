package com.atguigu.java.chapter05;

/**
 * 动态链接
 *
 * @author: 陌溪
 * @create: 2020-07-06-10:06
 */
public class DynamicLinkingTest {

    int num = 10;

    public void methodA() {
        System.out.println("methodA()....");
    }

    public void methodB() {
        System.out.println("methodA()....");
        methodA();
        num ++;
    }
}
