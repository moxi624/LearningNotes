package com.atguigu.java.chapter16;

/**
 * System.gc()
 *
 * @author: 陌溪
 * @create: 2020-07-12-19:07
 */
public class SystemGCTest {
    public static void main(String[] args) {
        new SystemGCTest();
        // 提醒JVM进行垃圾回收
        System.gc();
//        System.runFinalization();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("SystemGCTest 执行了 finalize方法");
    }
}
