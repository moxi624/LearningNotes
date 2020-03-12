import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyData{
    volatile int number = 0;
    public void addTo60(){
        this.number = 60;
    }
    public void addPlusPlus(){
        number++;
    }

    AtomicInteger atomicInteger = new AtomicInteger();
    public void addMyAtommic(){
        atomicInteger.getAndIncrement();
    }
}
/*
1 验证volatile的可见性
    1.1 加入int number=0，number变量之前根本没有添加volatile关键字修饰,没有可见性
    1.2 添加了volatile，可以解决可见性问题
2 验证volatile不保证原子性

    2.1 原子性是不可分割，完整性，也即某个线程正在做某个具体业务时，中间不可以被加塞或者分割。
    需要整体完成，要么同时成功，要么同时失败。

    2.2 volatile不可以保证原子性演示

    2.3 如何解决原子性
        *加sync
        *使用我们的JUC下AtomicInteger

* */
public class VolatileDemo {
    public static void main(String[] args){
        MyData myData = new MyData();
        for (int i = 1; i <= 20 ; i++) {
            new Thread(()->{
                for (int j = 1; j <= 1000 ; j++) {
                    myData.addPlusPlus();
                    myData.addMyAtommic();
                }
            },String.valueOf(i)).start();
        }

        //需要等待上述20个线程都计算完成后，再用main线程去的最终的结果是多少？
//        try{TimeUnit.SECONDS.sleep(5);} catch (InterruptedException e) {e.printStackTrace();}
        while(Thread.activeCount() > 2){
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName()+"\t finnally number value: "+myData.number);
        System.out.println(Thread.currentThread().getName()+"\t finnally number value: "+myData.atomicInteger);
    }
}
