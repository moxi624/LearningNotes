package com.moxi.interview.study.ThreadLocal;

/**
 * 需求：线程隔离
 * 在多线程并发的场景下，每个线程中的变量都是相互独立的
 * 线程A：设置变量1，获取变量2
 * 线程B：设置变量2，获取变量2
 * @author: 陌溪
 * @create: 2020-07-10-17:03
 */
/**
 * 需求：线程隔离
 * 在多线程并发的场景下，每个线程中的变量都是相互独立的
 * 线程A：设置变量1，获取变量2
 * 线程B：设置变量2，获取变量2
 * @author: 陌溪
 * @create: 2020-07-10-17:03
 */
public class MyDemo02 {
    // 变量
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static void main(String[] args) {
        MyDemo01 myDemo01 = new MyDemo01();
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                threadLocal.set(Thread.currentThread().getName() + "的数据");
                System.out.println("-----------------------------------------");
                System.out.println(Thread.currentThread().getName() + "\t  " + threadLocal.get());
            }, String.valueOf(i)).start();
        }
    }
}