package com.atguigu.java.chapter08;

import java.util.ArrayList;
import java.util.Random;

/**
 * 代码演示对象创建过程
 *
 * @author: 陌溪
 * @create: 2020-07-07-9:16
 */
public class HeapInstanceTest {
    byte [] buffer = new byte[new Random().nextInt(1024 * 200)];
    public static void main(String[] args) throws InterruptedException {
        ArrayList<HeapInstanceTest> list = new ArrayList<>();
        while (true) {
            list.add(new HeapInstanceTest());
            Thread.sleep(10);
        }
    }
}
