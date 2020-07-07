package com.atguigu.java.chapter08;

import java.util.ArrayList;
import java.util.List;

/**
 * OOM测试
 *
 * @author: 陌溪
 * @create: 2020-07-06-21:11
 */
public class OOMTest {
    public static void main(String[] args) throws InterruptedException {
        List<Integer> list = new ArrayList<>();
        while(true) {
            Thread.sleep(1);
            list.add(999999999);
        }
    }
}
