package com.moxi.interview.study.AQS;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: 陌溪
 * @create: 2020-07-17-15:56
 */
public class Main3 {
    public static int m = 0;
    // ReentrantLock默认是非公平锁
    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[100];
        ReentrantLock lock = new ReentrantLock();
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                lock.lock();
                try {
                    for (int j = 0; j < 100; j++) {
                        m++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            });
        }

        for (Thread t: threads) {
            t.start();
        }
        // 等待所有线程结束
        for (Thread t: threads) {
            t.join();
        }
        System.out.println(m);
    }
}
