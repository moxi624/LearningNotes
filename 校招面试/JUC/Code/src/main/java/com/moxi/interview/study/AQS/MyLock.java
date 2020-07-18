package com.moxi.interview.study.AQS;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 自定义锁
 *
 * @author: 陌溪
 * @create: 2020-07-17-17:06
 */
public class MyLock implements Lock {
    private volatile int i = 0;
    @Override
    public void lock() {
        synchronized (this) {
            // 判断是否有线程已经占用了锁
            while(i != 0) {
                try {
                    // 如果有线程占有锁，可以直接阻塞
                    this.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            i = 1;
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        synchronized (this) {
            i = 0;
            // 唤醒所有线程
            this.notifyAll();
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
