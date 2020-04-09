package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entity.Payment;


/**
 * (Payment)表服务接口
 *
 * @author makejava
 * @since 2020-03-06 20:24:56
 */
public interface PaymentService {


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Payment getPaymentById(Long id);


    /**
     * 新增数据
     *
     * @param payment 实例对象
     * @return 影响行数
     */
    int create(Payment payment);
}