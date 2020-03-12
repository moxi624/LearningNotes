/*
* 可重入锁（也就是递归锁）：指的是同一个线程外层函数获得锁之后，内层递归函数仍然能获取该锁的代码，
* 在同一线程在外层方法获取锁的时候，在进入内层方法会自动获取锁。也就是说，线程可以进入任何一个
* 它已经拥有的锁所有同步着的代码块。
* */
class Phone{
    public synchronized void sendSMS() throws Exception{
        System.out.println(Thread.currentThread().getId()+"\t invoked sendSMS()");
        sendEmail();
    }

    public synchronized void sendEmail() throws Exception{
        System.out.println(Thread.currentThread().getId()+"\t invoked sendEmail()");
    }
}

public class RenenterLockDemo {
    public static void main(String[] args){
        Phone phone = new Phone();
        new Thread(()->{
            try {
                phone.sendSMS();
            } catch (Exception e){
                e.printStackTrace();
            }
        },"t1").start();

        new Thread(()->{
            try {
                phone.sendSMS();
            } catch (Exception e){
                e.printStackTrace();
            }
        },"t2").start();
    }

}
