package com.moxi.interview.study.oom;

import java.nio.ByteBuffer;

/**
 * @author: 陌溪
 * @create: 2020-03-24-15:46
 */
public class DIrectBufferMemoryDemo {

    public static void main(String[] args) {

        // -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m

        // 配置的maxDirectMemory：3614.5MB  也就是说JVM默认可以用的物理内存为：4G
        System.out.println("配置的maxDirectMemory：" + (sun.misc.VM.maxDirectMemory() / (double) 1024 / 1024) + "MB");

        // 只设置了5M的物理内存使用，但是却分配 6M的空间
        ByteBuffer bb = ByteBuffer.allocateDirect(6 * 1024 * 1024);

    }
}
