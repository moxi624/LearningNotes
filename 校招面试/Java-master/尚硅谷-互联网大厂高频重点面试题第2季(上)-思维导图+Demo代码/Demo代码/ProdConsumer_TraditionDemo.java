import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareData{  //资源类
    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void increment() throws Exception{
        lock.lock();
        try{
            //        1.判断
            while(number !=0){
//            等待，不能生产
                condition.await();
            }
            //2.干活
            number++;
            System.out.println(Thread.currentThread().getName()+"\t"+number);
            //3.通知唤醒
            condition.signalAll();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }

    public void decrement() throws Exception{
        lock.lock();
        try{
            //        1.判断
            while(number ==0){
//            等待，不能生产
                condition.await();
            }
            //2.干活
            number--;
            System.out.println(Thread.currentThread().getName()+"\t"+number);
            //3.通知唤醒
            condition.signalAll();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }

}

/*
* 题目：一个初始值为0的变量，两个线程对其交替操作，一个加1，一个减1，来5轮
*
* 1.线程操作资源类
* 2.判断 干活 通知
* 3.防止虚假唤醒机制
* */
public class ProdConsumer_TraditionDemo {
    public static void main(String[] args){
        ShareData shareData = new ShareData();

        new Thread(()->{
            for(int i=1;i<=5;i++){
                try {
                    shareData.increment();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"AAA").start();

        new Thread(()->{
            for(int i=1;i<=5;i++){
                try {
                    shareData.decrement();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"BBB").start();
    }
}
