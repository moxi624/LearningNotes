package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.service.IMassageProvider;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @Author: TianTian
 * @Date: 2020/3/12 11:25
 */
// @EnableBinding： 指信道channel 和 exchange绑定在一起
@EnableBinding(Source.class) //定义消息的推送管道
public class MessageProviderImpl implements IMassageProvider {

    @Resource
    private MessageChannel output; //消息发送管道（原来这里是操作dao，现在是操作消息中间件发送消息）

    @Override
    public String send() {
        String serial= UUID.randomUUID().toString();
        // 消息构建器构建一个消息 MessageBuilder
        output.send(MessageBuilder.withPayload(serial).build());
        System.out.println("***********serial"+serial);
        return null;
    }
}
