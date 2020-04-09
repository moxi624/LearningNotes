package com.moxi.interview.study.basic;

import java.util.concurrent.CountDownLatch;

/**
 * 分布式锁
 *
 * @author: 陌溪
 * @create: 2020-04-03-23:21
 */
public class ZkDistributedLock extends ZkAbstractTemplateLock{
    @Override
    public boolean tryLock() {
        // 判断节点是否存在，如果存在则返回false，否者返回true
        return false;
    }

    @Override
    public void waitZkLock() {
        // 等待锁的时候，需要加监控，查询这个lock是否释放

        CountDownLatch countDownLatch = new CountDownLatch(1);

        try {
            countDownLatch.await();
        } catch (Exception e) {

        }


        // 解除监听
    }
}
