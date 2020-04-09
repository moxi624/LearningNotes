package com.moxi.interview.study.basic;

import java.util.HashMap;
import java.util.Map;

/**
 * 客户端
 *
 * @author: 陌溪
 * @create: 2020-04-03-22:48
 */
public class Client {
    public static void main(String[] args) {
        OrderService orderService = new OrderService();

        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
//                String str = orderService.getOrderNumber();
//                System.out.println(str);
            }, String.valueOf(i)).start();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("a", 1);
    }
}
