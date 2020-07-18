package com.moxi.interview.study.AQS;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 自定义锁
 *
 * @author: 陌溪
 * @create: 2020-07-17-17:06
 */
public class MyLock2 implements Lock {
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

    /**
     * 内部类
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
            assert arg == 1;
            if(!isHeldExclusively()) {
                throw new IllegalMonitorStateException();
            }
            // 释放锁
            setExclusiveOwnerThread(null);
            setState(0);
            return super.tryRelease(arg);
        }


        @Override
        protected boolean isHeldExclusively() {
            // 判断当前线程 是不是和排它锁的线程一样
            return getExclusiveOwnerThread() == Thread.currentThread();
        }
    }
}
