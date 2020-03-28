package com.moxi.interview.study.newNio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * 单线程版服务器，NIO的伪代码
 *
 * @author: 陌溪
 * @create: 2020-03-28-12:04
 */
public class QQServerByOneThread {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(8080));

        // 套接字列表，用于存储
        List<Socket> socketList = new ArrayList<>();
        // 缓冲区
        byte[] bytes = new byte[1024];

        // 设置非阻塞
//        serverSocket.setConfig();

        while(true) {
            // 获取套接字
            Socket socket = serverSocket.accept();

            // 如果没人连接
            if(socket == null) {
                System.out.println("没人连接");

                // 遍历循环socketList，套接字list
                for(Socket item : socketList) {
                    int read = socket.getInputStream().read(bytes);
                    // 表示有人发送东西
                    if(read != 0) {
                        // 打印出内容
                        System.out.println(new String(bytes));
                    }
                }
            } else {
                // 如果有人连接，把套接字放入到列表中
                socketList.add(socket);

                // 设置非阻塞
//                serverSocket.setConfig();
                // 遍历循环socketList，套接字list

                // 遍历循环socketList，套接字list
                for(Socket item : socketList) {
                    int read = socket.getInputStream().read(bytes);
                    // 表示有人发送东西
                    if(read != 0) {
                        // 打印出内容
                        System.out.println(new String(bytes));
                    }
                }

            }
        }
    }
}
