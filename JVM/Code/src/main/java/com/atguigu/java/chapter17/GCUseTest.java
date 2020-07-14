package com.atguigu.java.chapter17;

/**
 * GC垃圾收集过程
 * @author: 陌溪
 * @create: 2020-07-14-8:35
 */
public class GCUseTest {
    static final Integer _1MB = 1024 * 1024;
    public static void main(String[] args) {
        byte [] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 *_1MB];
        allocation2 = new byte[2 *_1MB];
        allocation3 = new byte[2 *_1MB];
        allocation4 = new byte[6 *_1MB];
    }
}
