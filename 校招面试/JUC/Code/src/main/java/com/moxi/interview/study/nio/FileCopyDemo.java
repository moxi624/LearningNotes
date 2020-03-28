package com.moxi.interview.study.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 利用通道完成文件的复制（非直接缓冲区）
 * @author: 陌溪
 * @create: 2020-03-27-16:36
 */
public class FileCopyDemo {

    public static void main(String[] args) {

        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            fis = new FileInputStream("1.jpg");
            fos = new FileOutputStream("2.jpg");

            // 获取通道
            inChannel = fis.getChannel();
            outChannel = fos.getChannel();

            //分配一个指定大小的缓冲区
            ByteBuffer buf = ByteBuffer.allocate(1024);

            // 将通道中的数据，存入缓冲区
            while (inChannel.read(buf) != -1) {
                // 切换成读取数据的模式
                buf.flip();

                // 将缓冲区中的数据写入通道
                outChannel.write(buf);

                // 清空缓冲区
                buf.clear();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                if(fis != null) {
                    fis.close();
                }
                if(fos != null) {
                    fos.close();
                }

                // // 关闭通道
                if(outChannel != null) {
                    outChannel.close();
                }
                if(inChannel != null) {
                    inChannel.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
        }
    }
}
