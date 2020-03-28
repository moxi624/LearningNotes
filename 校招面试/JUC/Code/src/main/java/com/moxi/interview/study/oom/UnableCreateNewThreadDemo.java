package com.moxi.interview.study.oom;

import java.util.concurrent.TimeUnit;

/**
 * 无法创建更多的线程
 *
 * @author: 陌溪
 * @create: 2020-03-24-17:02
 */
public class UnableCreateNewThreadDemo {
    public static void main(String[] args) {
        for (int i = 0; ; i++) {
            System.out.println("************** i = " + i);
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }
}
