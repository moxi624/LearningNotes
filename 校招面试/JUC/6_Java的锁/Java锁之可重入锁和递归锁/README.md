# 可重入锁和递归锁ReentrantLock

## 概念

可重入锁就是递归锁

指的是同一线程外层函数获得锁之后，内层递归函数仍然能获取到该锁的代码，在同一线程在外层方法获取锁的时候，在进入内层方法会自动获取锁

也就是说：`线程可以进入任何一个它已经拥有的锁所同步的代码块`

ReentrantLock / Synchronized 就是一个典型的可重入锁

## 代码

可重入锁就是，在一个method1方法中加入一把锁，方法2也加锁了，那么他们拥有的是同一把锁

```
public synchronized void method1() {
	method2();
}

public synchronized void method2() {

}
```

也就是说我们只需要进入method1后，那么它也能直接进入method2方法，因为他们所拥有的锁，是同一把。

## 作用

可重入锁的最大作用就是避免死锁

## 可重入锁验证

### 证明Synchronized

```
/**
 * 可重入锁（也叫递归锁）
 * 指的是同一线程外层函数获得锁之后，内层递归函数仍然能获取到该锁的代码，在同一线程在外层方法获取锁的时候，在进入内层方法会自动获取锁
 *
 * 也就是说：`线程可以进入任何一个它已经拥有的锁所同步的代码块`
 * @author: 陌溪
 * @create: 2020-03-15-12:12
 */

/**
 * 资源类
 */
class Phone {

    /**
     * 发送短信
     * @throws Exception
     */
    public synchronized void sendSMS() throws Exception{
        System.out.println(Thread.currentThread().getName() + "\t invoked sendSMS()");

        // 在同步方法中，调用另外一个同步方法
        sendEmail();
    }

    /**
     * 发邮件
     * @throws Exception
     */
    public synchronized void sendEmail() throws Exception{
        System.out.println(Thread.currentThread().getId() + "\t invoked sendEmail()");
    }
}
public class ReenterLockDemo {


    public static void main(String[] args) {
        Phone phone = new Phone();

        // 两个线程操作资源列
        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t1").start();

        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t2").start();
    }
}
```

在这里，我们编写了一个资源类phone，拥有两个加了synchronized的同步方法，分别是sendSMS 和 sendEmail，我们在sendSMS方法中，调用sendEmail。最后在主线程同时开启了两个线程进行测试，最后得到的结果为：

```
t1	 invoked sendSMS()
t1	 invoked sendEmail()
t2	 invoked sendSMS()
t2	 invoked sendEmail()
```

这就说明当 t1 线程进入sendSMS的时候，拥有了一把锁，同时t2线程无法进入，直到t1线程拿着锁，执行了sendEmail 方法后，才释放锁，这样t2才能够进入

```
t1	 invoked sendSMS()      t1线程在外层方法获取锁的时候
t1	 invoked sendEmail()    t1在进入内层方法会自动获取锁

t2	 invoked sendSMS()      t2线程在外层方法获取锁的时候
t2	 invoked sendEmail()    t2在进入内层方法会自动获取锁
```

### 证明ReentrantLock

```
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 资源类
 */
class Phone implements Runnable{

    Lock lock = new ReentrantLock();

    /**
     * set进去的时候，就加锁，调用set方法的时候，能否访问另外一个加锁的set方法
     */
    public void getLock() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t get Lock");
            setLock();
        } finally {
            lock.unlock();
        }
    }

    public void setLock() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t set Lock");
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        getLock();
    }
}
public class ReenterLockDemo {


    public static void main(String[] args) {
        Phone phone = new Phone();

        /**
         * 因为Phone实现了Runnable接口
         */
        Thread t3 = new Thread(phone, "t3");
        Thread t4 = new Thread(phone, "t4");
        t3.start();
        t4.start();
    }
}

```

现在我们使用ReentrantLock进行验证，首先资源类实现了Runnable接口，重写Run方法，里面调用get方法，get方法在进入的时候，就加了锁

```
    public void getLock() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t get Lock");
            setLock();
        } finally {
            lock.unlock();
        }
    }
```

然后在方法里面，又调用另外一个加了锁的setLock方法

```
    public void setLock() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t set Lock");
        } finally {
            lock.unlock();
        }
    }
```

最后输出结果我们能发现，结果和加synchronized方法是一致的，都是在外层的方法获取锁之后，线程能够直接进入里层

```
t3	 get Lock
t3	 set Lock
t4	 get Lock
t4	 set Lock
```

**当我们在getLock方法加两把锁会是什么情况呢？**  (阿里面试)

```
    public void getLock() {
        lock.lock();
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t get Lock");
            setLock();
        } finally {
            lock.unlock();
            lock.unlock();
        }
    }
```

最后得到的结果也是一样的，因为里面不管有几把锁，其它他们都是同一把锁，也就是说用同一个钥匙都能够打开

**当我们在getLock方法加两把锁，但是只解一把锁会出现什么情况呢？**

    public void getLock() {
        lock.lock();
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t get Lock");
            setLock();
        } finally {
            lock.unlock();
            lock.unlock();
        }
    }
得到结果

```
t3	 get Lock
t3	 set Lock
```

也就是说程序直接卡死，线程不能出来，也就说明我们申请几把锁，最后需要解除几把锁

**当我们只加一把锁，但是用两把锁来解锁的时候，又会出现什么情况呢？**

```
    public void getLock() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t get Lock");
            setLock();
        } finally {
            lock.unlock();
            lock.unlock();
        }
    }
```

这个时候，运行程序会直接报错

```
t3	 get Lock
t3	 set Lock
t4	 get Lock
t4	 set Lock
Exception in thread "t3" Exception in thread "t4" java.lang.IllegalMonitorStateException
	at java.util.concurrent.locks.ReentrantLock$Sync.tryRelease(ReentrantLock.java:151)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.release(AbstractQueuedSynchronizer.java:1261)
	at java.util.concurrent.locks.ReentrantLock.unlock(ReentrantLock.java:457)
	at com.moxi.interview.study.thread.Phone.getLock(ReenterLockDemo.java:52)
	at com.moxi.interview.study.thread.Phone.run(ReenterLockDemo.java:67)
	at java.lang.Thread.run(Thread.java:745)
java.lang.IllegalMonitorStateException
	at java.util.concurrent.locks.ReentrantLock$Sync.tryRelease(ReentrantLock.java:151)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.release(AbstractQueuedSynchronizer.java:1261)
	at java.util.concurrent.locks.ReentrantLock.unlock(ReentrantLock.java:457)
	at com.moxi.interview.study.thread.Phone.getLock(ReenterLockDemo.java:52)
	at com.moxi.interview.study.thread.Phone.run(ReenterLockDemo.java:67)
	at java.lang.Thread.run(Thread.java:745)
```

