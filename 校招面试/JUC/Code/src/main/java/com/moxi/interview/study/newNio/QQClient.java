package com.moxi.interview.study.newNio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * QQ客户端
 *
 * @author: 陌溪
 * @create: 2020-03-28-11:09
 */
public class QQClient {
    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("127.0.0.1", 8080);
        socket.getOutputStream().write("我来发送".getBytes());
        while (true) {

        }
    }
}
