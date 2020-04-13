package com.moxi.interview.study.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 顺序执行线程
 *
 * @author: 陌溪
 * @create: 2020-04-12-21:59
 */
public class SequenceThread {
    /**
     * 没有顺序执行的示例
     */
    public static void test() {
        Thread t1 = new Thread(() -> {
            System.out.println("1");
        }, "t1");

        Thread t2 = new Thread(() -> {
            System.out.println("2");
        }, "t2");

        Thread t3 = new Thread(() -> {
            System.out.println("3");
        }, "t3");

        t1.start();
        t2.start();
        t3.start();
    }

    public static void test2() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            System.out.println("1");
        }, "t1");

        Thread t2 = new Thread(() -> {
            System.out.println("2");
        }, "t2");

        Thread t3 = new Thread(() -> {
            System.out.println("3");
        }, "t3");

        t1.start();
        t1.join();

        t2.start();
        t2.join();

        t3.start();
        t3.join();
    }

    /**
     * 使用线程池
     */
    public static void test3() {
        // 创建一个单例线程
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Thread t1 = new Thread(() -> {
            System.out.println("1");
        }, "t1");

        Thread t2 = new Thread(() -> {
            System.out.println("2");
        }, "t2");

        Thread t3 = new Thread(() -> {
            System.out.println("3");
        }, "t3");
        executorService.submit(t1);
        executorService.submit(t2);
        executorService.submit(t3);
    }
    public static void main(String[] args) throws InterruptedException {

        test3();
    }
}
