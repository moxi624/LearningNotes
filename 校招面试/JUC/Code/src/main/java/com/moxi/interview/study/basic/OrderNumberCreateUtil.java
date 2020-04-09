package com.moxi.interview.study.basic;

/**
 * @author: 陌溪
 * @create: 2020-04-03-22:46
 */
public class OrderNumberCreateUtil {
    private static int num = 0;

    public String getOrderNumber() {
        return "\t 生成订单号：" + (++num);
    }

}
