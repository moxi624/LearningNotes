package com.moxi.interview.study.nio;

import java.nio.ByteBuffer;

/**
 * 缓冲区：Buffer
 * 在Java NIO中负责数据的存取。缓冲区就是数组。用于存储不同类型的数据
 * 根据数据类型不同，提供相同类型的缓冲区（除了Boolean）
 * ByteBuffer
 * CharBuffer
 * @author: 陌溪
 * @create: 2020-03-27-14:48
 */
public class BufferDemo {

    public static void main(String[] args) {

        // 创建一个直接缓冲区，直接和物理内存挂钩
        ByteBuffer buf = ByteBuffer.allocateDirect(1024);

        System.out.println("是否直接缓冲区: " + buf.isDirect());

    }

    /**
     * 缓冲区相关操作
     */
    private static void test1() {
        // 分配一个指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);
        System.out.println("初始化");
        System.out.println("position：" + buf.position());
        System.out.println("limit：" + buf.limit());
        System.out.println("capacity：" + buf.capacity());

        // 存入数据到缓冲区
        String str = "abcde";
        buf.put(str.getBytes());
        System.out.println("存入数据");
        System.out.println("position：" + buf.position());
        System.out.println("limit：" + buf.limit());
        System.out.println("capacity：" + buf.capacity());

        // 切换读取数据模式
        buf.flip();
        System.out.println("切换读取数据模式");
        System.out.println("position：" + buf.position());
        System.out.println("limit：" + buf.limit());
        System.out.println("capacity：" + buf.capacity());

        // 开始读取数据
        System.out.println("开始读取数据");
        byte[] dst = new byte[buf.limit()];
        buf.get(dst);
        System.out.println(new String(dst, 0, dst.length));

        System.out.println("数据读取完毕");
        System.out.println("position：" + buf.position());
        System.out.println("limit：" + buf.limit());
        System.out.println("capacity：" + buf.capacity());

        // rewind()：表示重复读
        buf.rewind();
        System.out.println("rewind");
        System.out.println("position：" + buf.position());
        System.out.println("limit：" + buf.limit());
        System.out.println("capacity：" + buf.capacity());

        // clear()：清空缓冲区，但是缓冲区中的数据仍然存储，但是处于被遗忘状态
        buf.clear();
        System.out.println("clear");
        System.out.println("position：" + buf.position());
        System.out.println("limit：" + buf.limit());
        System.out.println("capacity：" + buf.capacity());
    }

}
