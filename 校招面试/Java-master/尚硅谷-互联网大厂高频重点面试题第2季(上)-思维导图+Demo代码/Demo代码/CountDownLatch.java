/*
*
* */
public class CountDownLatch {
    public static void main(String[] args) throws Exception{
        java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(6);

        for(int i=1;i<=6;i++){
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t 上完自习，离开教室");
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }

        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"\t *****班长最后关门走人");
    }
}
