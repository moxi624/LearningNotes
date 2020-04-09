package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * 自定义负载均衡算法
 * @Author: TianTian
 * @Date: 2020/3/7 19:53
 */
public interface LoadBalancer {
    // 获取注册的一个实例
    ServiceInstance instances(List<ServiceInstance> serviceInstances);
}
