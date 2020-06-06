package com.moxi.interview.study.thread;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: 陌溪
 * @create: 2020-06-04-14:42
 */
public class ThreadPoolDemo {
    public static void main(String[] args) {
        // 一池5个处理线程（用池化技术，一定要记得关闭）
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList();
        CopyOnWriteArrayList<Integer> copyOnWriteArrayList2 = new CopyOnWriteArrayList();
        for (int i = 1; i <= 16; i++) {
            copyOnWriteArrayList.add(i);
        }
        while(copyOnWriteArrayList.size() > 0) {
            threadPool.execute(() -> {
                Integer temp = copyOnWriteArrayList.get(0);
                System.out.println(Thread.currentThread().getName() + "\t" + temp);
                copyOnWriteArrayList2.add(temp);
                copyOnWriteArrayList.remove(0);
            });
        }
        for (int i = 0; i < 15; i++) {
            System.out.print(copyOnWriteArrayList2.get(i) + " ");
        }
    }
}
