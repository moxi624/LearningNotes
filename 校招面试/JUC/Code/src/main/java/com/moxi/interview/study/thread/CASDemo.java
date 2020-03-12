package com.moxi.interview.study.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CASDemo
 *
 * 比较并交换：compareAndSet
 *
 * @author: 陌溪
 * @create: 2020-03-10-19:46
 */
public class CASDemo {
    public static void main(String[] args) {
        // 创建一个原子类
        AtomicInteger atomicInteger = new AtomicInteger(5);

        /**
         * 一个是期望值，一个是更新值，但期望值和原来的值相同时，才能够更改
         * 假设三秒前，我拿的是5，也就是expect为5，然后我需要更新成 2019
         */
        System.out.println(atomicInteger.compareAndSet(5, 2019) + "\t current data: " + atomicInteger.get());

        System.out.println(atomicInteger.compareAndSet(5, 1024) + "\t current data: " + atomicInteger.get());

        atomicInteger.getAndIncrement();
    }
}
