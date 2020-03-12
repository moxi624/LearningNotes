import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
* 题目：多线程之间按顺序调用，实现A->B->C三个线程启动，要求如下：
* A打印5次，B打印10次，C打印15次
* 紧接着
* A打印5次，B打印10次，C打印15次
* 。。。。。
* 打印10轮
* */
class ShareResource{
    private int number = 1;//A:1.B:2,C:3
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print5(){
        lock.lock();
        try{
            //1判断
            while(number != 1){
                c1.await();
            }
            //2干活
            for(int i=1;i<=5;i++){
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //3通知
            number = 2;
            c2.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void print10(){
        lock.lock();
        try{
            //1判断
            while(number != 2){
                c2.await();
            }
            //2干活
            for(int i=1;i<=10;i++){
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //3通知
            number = 3;
            c3.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void print15(){
        lock.lock();
        try{
            //1判断
            while(number != 3){
                c3.await();
            }
            //2干活
            for(int i=1;i<=15;i++){
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //3通知
            number = 1;
            c1.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }




}

public class SyncAndReentrantLockDemo {
    public static void main(String[] args){
        ShareResource shareResource = new ShareResource();

        new Thread(()->{
            for(int i=1;i<=10;i++){
                shareResource.print5();
            }
        },"A").start();
        new Thread(()->{
            for(int i=1;i<=10;i++){
                shareResource.print10();
            }
        },"B").start();
        new Thread(()->{
            for(int i=1;i<=10;i++){
                shareResource.print15();
            }
        },"C").start();
    }
}
