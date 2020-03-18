package com.moxi.interview.study.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

    public synchronized void method1() {
        method2();
    }

    public synchronized void method2() {

    }

    public static void main(String[] args) {

        /**
         * 创建一个可重入锁，true 表示公平锁，false 表示非公平锁。默认非公平锁
         */
        Lock lock = new ReentrantLock(true);


    }
}
