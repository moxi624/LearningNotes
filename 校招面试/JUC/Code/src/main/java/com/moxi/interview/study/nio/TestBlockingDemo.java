package com.moxi.interview.study.nio;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.TimeUnit;

/**
 * 阻塞式NIO
 *
 * @author: 陌溪
 * @create: 2020-03-27-19:16
 */
public class TestBlockingDemo {

    public static void client() throws IOException {
        // 获取通道
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));

        FileChannel inChannel = FileChannel.open(Paths.get("D:\\1.jpg"), StandardOpenOption.READ);
        // 分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        // 读取本地文件，并发送到服务端
        while (inChannel.read(buf) != -1) {
            // 切换到读数据模式
            buf.flip();

            // 将缓冲区的数据写入管道
            sChannel.write(buf);

            // 清空缓冲区
            buf.clear();
        }

        // 告诉客户端我发送完成了，或者切换成非阻塞模式
        sChannel.shutdownOutput();

        // 接收服务端的反馈
        int len = 0;
        while((len = sChannel.read(buf)) != -1) {
            buf.flip();
            System.out.println(new String(buf.array(), 0, len));
            buf.clear();
        }

        //关闭通道
        inChannel.close();
        sChannel.close();
    }

    /**
     * 服务端
     */
    public static void server() throws IOException {
        // 获取通道
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        FileChannel fileChannel = FileChannel.open(Paths.get("D:\\2.jpg"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        // 绑定端口号
        ssChannel.bind(new InetSocketAddress(9898));

        // 获取客户端连接的通道
        SocketChannel socketChannel = ssChannel.accept();

        // 分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        // 读取客户端的数据，并保存到本地
        while(socketChannel.read(buf) != -1) {
            // 切换成读模式
            buf.flip();

            // 写入
            fileChannel.write(buf);

            // 清空缓冲区
            buf.clear();
        }

        //向客户端反馈
        buf.put("服务端数据接收成功".getBytes());
        buf.flip();
        socketChannel.write(buf);

        // 关闭通道
        ssChannel.close();
        socketChannel.close();
        fileChannel.close();
    }

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                server();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            try {
                client();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "t2").start();
    }
}
