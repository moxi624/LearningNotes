package com.moxi.interview.study.AQS;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * AQS
 *
 * @author: 陌溪
 * @create: 2020-07-17-19:56
 */
public class Sync extends AbstractQueuedSynchronizer {

    @Override
    protected boolean tryAcquire(int arg) {
        // 使用自旋锁 ，同时CAS必须保证原子性
        // 目前的CPU底层汇编都有这条指令了，即支持原语操作
        if (compareAndSetState(0, 1)) {
            // 设置排它的拥有者，也就是互斥锁
            setExclusiveOwnerThread(Thread.currentThread());
            return true;
        }
        return false;
    }

    @Override
    protected boolean tryRelease(int arg) {
        // 释放锁
        setExclusiveOwnerThread(null);
        setState(0);
        return super.tryRelease(arg);
    }


    @Override
    protected boolean isHeldExclusively() {
        // 判断当前线程是不是
        return getState() == 1;
    }
}
