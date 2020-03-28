package com.moxi.interview.study.newNio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * QQ客户端
 *
 * @author: 陌溪
 * @create: 2020-03-28-11:09
 */
public class QQServer {
    static byte[] bytes = new byte[1024];

    public static void main(String[] args) throws IOException {

        while(true) {
            ServerSocket serverSocket = new ServerSocket();

            // 绑定IP地址
            serverSocket.bind(new InetSocketAddress(8080));

            System.out.println("服务器等待连接....");

            // 阻塞
            Socket socket = serverSocket.accept();

            System.out.println("服务器连接....");

            // 阻塞
            System.out.println("等待发送客户端数据");
            socket.getInputStream().read(bytes);

            System.out.println("数据接收成功:" + new String(bytes));
        }
    }
}
