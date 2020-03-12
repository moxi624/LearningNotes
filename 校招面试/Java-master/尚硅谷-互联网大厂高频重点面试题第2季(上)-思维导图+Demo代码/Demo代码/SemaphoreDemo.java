import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/*
*
* */
public class SemaphoreDemo {
    public static void main(String[] args){
        Semaphore semaphore = new Semaphore(3);  //模拟3个车位
        for(int i=1;i<=6;i++){  //模拟6部车
            new Thread(()->{
                try{
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+"\t抢到车位");
                    try{
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {e.printStackTrace();}
                    System.out.println(Thread.currentThread().getName()+"\t停车3秒后离开车位");
                } catch (InterruptedException e){
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            },String.valueOf(i)).start();
        }
    }
}
