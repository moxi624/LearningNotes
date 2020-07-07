package com.atguigu.java.chapter08;

/**
 * -Xms20m  -Xmx20m
 * @author: 陌溪
 * @create: 2020-07-06-19:59
 */
public class HeapDemo2 {
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
