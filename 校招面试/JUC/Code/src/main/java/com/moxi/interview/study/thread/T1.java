package com.moxi.interview.study.thread;

/**
 * T1
 *
 * @author: 陌溪
 * @create: 2020-03-09-17:54
 */
public class T1 {
    volatile int n = 0;
    public void add() {
        n++;
    }
}
