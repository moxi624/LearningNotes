package com.atguigu.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @Author: TianTian
 * @Date: 2020/3/12 11:50
 */
@Component
@EnableBinding(Sink.class)  // 绑定通道
public class ReceiveMessageListenerController {

    @Value("${server.port}")
    private String serverPort;

    // 监听队列，用于消费者队列的消息接收
    @StreamListener(Sink.INPUT)
    public void input(Message<String> message) {
        System.out.println("消费者1号，0------>接收到消息："+message.getPayload()+"\t port:"+serverPort);
    }
}
