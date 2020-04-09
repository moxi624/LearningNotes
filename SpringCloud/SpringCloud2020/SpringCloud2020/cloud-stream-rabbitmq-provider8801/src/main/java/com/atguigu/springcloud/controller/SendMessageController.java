package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.IMassageProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: TianTian
 * @Date: 2020/3/12 11:33
 */
@RestController
public class SendMessageController {
    @Resource
    private IMassageProvider massageProvider;

    @GetMapping("/sendMessage")
    public String sendMessage(){
        return massageProvider.send();
    }
}
