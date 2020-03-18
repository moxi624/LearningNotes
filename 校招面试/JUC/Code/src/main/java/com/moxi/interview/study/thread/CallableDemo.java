package com.moxi.interview.study.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author: 陌溪
 * @create: 2020-03-17-12:40
 */

/**
 * 实现Runnable接口
 * Runnable接口 没有返回值
 */
class MyThread implements Runnable {

    @Override
    public void run() {

    }
}

/**
 * Callable有返回值
 * 批量处理的时候，需要带返回值的接口（例如支付失败的时候，需要返回错误状态）
 *
 */
class MyThread2 implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + "\t come in Callable ");

        // 模拟计算两秒
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 1024;
    }
}
public class CallableDemo {
    public static void main(String[] args) throws Exception{
        // FutureTask：实现了Runnable接口，构造函数又需要传入 Callable接口
        // 这里通过了FutureTask接触了Callable接口
        FutureTask<Integer> futureTask = new FutureTask<>(new MyThread2());
        FutureTask<Integer> futureTask2 = new FutureTask<>(new MyThread2());

        // 开启两
        new Thread(futureTask, "AAA").start();

        new Thread(futureTask2, "BBB").start();


        System.out.println("+++++++++++++++");

        // 判断futureTask是否计算完成
        while(!futureTask.isDone()) {

        }

        // 输出FutureTask的返回值，  futureTask.get() 可以放在最后
        // 要求获得Callable线程的计算结果，如果没有计算完成就要去强求，会导致阻塞，直到计算完成
        System.out.println("result FutureTask " + futureTask.get());


    }
}
