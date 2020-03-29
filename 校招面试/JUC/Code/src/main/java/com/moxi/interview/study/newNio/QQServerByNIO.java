package com.moxi.interview.study.newNio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * NIO版QQ服务器
 *
 * @author: 陌溪
 * @create: 2020-03-28-12:16
 */
public class QQServerByNIO {
    public static void main(String[] args) throws IOException {
        // 创建一个通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8080));

        // 定义list用于存储SocketChannel，也就是非阻塞的连接
        List<SocketChannel> socketChannelList = new ArrayList<>();
        byte [] bytes = new byte[1024];
        // 缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        // 设置非阻塞
        serverSocketChannel.configureBlocking(false);

        while(true) {
            SocketChannel socketChannel = serverSocketChannel.accept();

            // 但无人连接的时候
            if(socketChannel == null) {

                // 睡眠一秒
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("无人连接");
                for(SocketChannel item: socketChannelList) {
                    int len = item.read(byteBuffer);
                    if(len > 0) {
                        // 切换成读模式
                        byteBuffer.flip();
                        // 打印出结果
                        System.out.println("读取到的数据" + new String(byteBuffer.array(), 0, len));
                    }
                    byteBuffer.clear();
                }

            } else {
                // 但有人连接的时候

                // 设置成非阻塞
                socketChannel.configureBlocking(false);

                // 将该通道存入到List中
                socketChannelList.add(socketChannel);

                for(SocketChannel item: socketChannelList) {
                    int len = item.read(byteBuffer);
                    if(len > 0) {
                        // 切换成读模式
                        byteBuffer.flip();
                        // 打印出结果
                        System.out.println("读取到的数据" + new String(byteBuffer.array(), 0, len));
                    }
                    byteBuffer.clear();
                }
            }
        }
    }
}
