/*
*第4种获得/使用java多线程的方式，通过线程池
* （其他三种是：继承Thread类；实现Runnable接口，但是Runnable没有返回值，不抛异常；
* 实现Callable接口，有返回值，会跑出异常）
* */

import java.util.concurrent.*;

//System.out.println(Runtime.getRuntime().availableProcessors());
//Array Arrays  辅助工具类
//Collection Collections
//Executor Executors
public class MyThreadPoolDemo {
    public static void main(String[] args){
        ExecutorService threadPool = new ThreadPoolExecutor(2,
                5,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy());

        try{
            for(int i=1;i<=11;i++){
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"\t 办理业务");
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }
    }

    private static void threadPoolInit() {
        //ExecutorService threadPool = Executors.newFixedThreadPool(5);//一池5个处理线程
        //ExecutorService threadPool = Executors.newFixedThreadPool(1);//一池1个线程
        ExecutorService threadPool = Executors.newCachedThreadPool();//一池N个线程

        //模拟10个用户来办理业务，每个用户就是一个来自外部的请求线程
        try{
            for(int i=1;i<=10;i++){
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"\t 办理业务");
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }
    }
}
