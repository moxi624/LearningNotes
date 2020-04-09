package com.moxi.interview.study.basic;

/**
 * ZkLock抽象类
 * 这就是模板设计模式
 *
 * @author: 陌溪
 * @create: 2020-04-03-23:06
 */
public abstract class ZkAbstractTemplateLock implements ZkLock{

    @Override
    public void zkLock() {
        // 尝试获取锁
        if(tryLock()) {
            System.out.println(Thread.currentThread().getName() + "\t 占用锁成功");
        } else {
            // 等待锁
            waitZkLock();
            // 重新调用获取锁的方法
            zkLock();
        }
    }

    /**
     * 定义两个抽象方法，一个是尝试锁，一个是等待锁
     * @return
     */
    public abstract boolean tryLock();

    public abstract void waitZkLock();

    @Override
    public void zkUnlock() {
        // 清空Zk的节点
    }
}
