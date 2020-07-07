package com.atguigu.java.chapter08;

/**
 *  -Xms10m  -Xmx10m
 * @author: 陌溪
 * @create: 2020-07-06-19:58
 */
public class HeapDemo {
    public static void main(String[] args) {
        System.out.println("start.........");
        try {
            Thread.sleep(100000000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("end.......");
        }

    }
}
