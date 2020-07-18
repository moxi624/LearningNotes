package com.moxi.interview.study.AQS;

/**
 * @author: 陌溪
 * @create: 2020-07-17-15:56
 */
public class Main2 {
    public static int m = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[100];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                synchronized (Main2.class) {
                    for (int j = 0; j < 100; j++) {
                        m++;
                    }
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
