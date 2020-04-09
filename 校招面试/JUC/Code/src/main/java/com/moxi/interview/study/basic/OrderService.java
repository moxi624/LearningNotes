package com.moxi.interview.study.basic;

/**
 * 订单业务逻辑
 *
 * @author: 陌溪
 * @create: 2020-04-03-22:47
 */
public class OrderService {
    private OrderNumberCreateUtil orderNumberCreateUtil = new OrderNumberCreateUtil();

//    public String getOrderNumber() {
//        return orderNumberCreateUtil.getOrderNumber();
//    }

    public void getOrderNumber() {
        ZkLock zkLock = new ZkDistributedLock();
        zkLock.zkLock();
        try {

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            zkLock.zkUnlock();
        }
        System.out.println(orderNumberCreateUtil.getOrderNumber());
    }
}
