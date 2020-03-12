package com.moxi.interview.study.thread;

/**
 * ResortSeqDemo
 *
 * @author: 陌溪
 * @create: 2020-03-10-16:08
 */
public class ResortSeqDemo {
    int a= 0;
    boolean flag = false;

    public void method01() {
        a = 1;
        flag = true;
    }

    public void method02() {
        if(flag) {
            a = a + 5;
            System.out.println("reValue:" + a);
        }
    }
}
