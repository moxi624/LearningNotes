package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

/**
 * @Author: TianTian
 * @Date: 2020/3/8 20:13
 */
@Component
public class PaymentFallbackService implements PaymentHystrixService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "--- PaymentFallbackService  fall  paymentInfo_OK vack ，/(ㄒoㄒ)/~~";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "--- PaymentFallbackService  fall  paymentInfo_TimeOut， /(ㄒoㄒ)/~~";
    }
}
