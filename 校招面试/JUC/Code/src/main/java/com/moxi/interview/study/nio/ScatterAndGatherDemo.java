package com.moxi.interview.study.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


/**
 * 分散读取，聚集写入
 * @author: 陌溪
 * @create: 2020-03-27-17:54
 */
public class ScatterAndGatherDemo {
    public static void main(String[] args) throws IOException {


    }

    /**
     * 聚集写入
     * @throws IOException
     */
    private static void Gather() throws IOException {
        RandomAccessFile raf2 = new RandomAccessFile("2.txt", "rw");
        FileChannel channel2 = raf2.getChannel();

        // 分配指定大小的缓冲区
        ByteBuffer buf1 = ByteBuffer.allocate(10);
        ByteBuffer buf2 = ByteBuffer.allocate(1024);
        ByteBuffer[] bufs = {buf1, buf2};

        // 聚集写入
        channel2.write(bufs);
    }

    /**
     * 分散读取
     * @throws IOException
     */
    private static void Scatteer() throws IOException {
        RandomAccessFile raf1 = new RandomAccessFile("1.txt", "rw");

        // 获取通道
        FileChannel channel = raf1.getChannel();

        // 分配指定大小的缓冲区
        ByteBuffer buf1 = ByteBuffer.allocate(10);
        ByteBuffer buf2 = ByteBuffer.allocate(1024);

        // 分散读取
        ByteBuffer[] bufs = {buf1, buf2};
        channel.read(bufs);

        for (ByteBuffer byteBuffer: bufs) {
            // 切换成读模式
            byteBuffer.flip();
        }

        System.out.println(new String(bufs[0].array(), 0, bufs[0].limit()));
        System.out.println(new String(bufs[1].array(), 0, bufs[1].limit()));
    }
}
