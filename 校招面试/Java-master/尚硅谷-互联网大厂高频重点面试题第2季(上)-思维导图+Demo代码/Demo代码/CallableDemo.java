import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/*class MyThread implements Runnable{
    public void run(){

    }
}*/

class MyThread implements Callable<Integer> {
    public Integer call() throws Exception{
        System.out.println(".........come in callable");
        return 1024;
    }
}

/*
*
* */
public class CallableDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        FutureTask<Integer> futureTask = new FutureTask<>(new MyThread());
        Thread t1 = new Thread(futureTask,"AA");
        t1.start();
        int r1 = 100;
        int r2 = futureTask.get();//要求获得Callable线程的计算结果，如果没有计算完成就要强求，会导致阻塞，知道计算完成，建议放在最后

        System.out.println(".....result"+r1+r2);
    }
}
