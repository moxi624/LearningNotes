# 独占锁（写锁） / 共享锁（读锁） / 互斥锁

## 概念

独占锁：指该锁一次只能被一个线程所持有。对ReentrantLock和Synchronized而言都是独占锁

共享锁：指该锁可以被多个线程锁持有

对ReentrantReadWriteLock其读锁是共享，其写锁是独占

写的时候只能一个人写，但是读的时候，可以多个人同时读

## 为什么会有写锁和读锁

原来我们使用ReentrantLock创建锁的时候，是独占锁，也就是说一次只能一个线程访问，但是有一个读写分离场景，读的时候想同时进行，因此原来独占锁的并发性就没这么好了，因为读锁并不会造成数据不一致的问题，因此可以多个人共享读

```
多个线程 同时读一个资源类没有任何问题，所以为了满足并发量，读取共享资源应该可以同时进行，但是如果一个线程想去写共享资源，就不应该再有其它线程可以对该资源进行读或写
```

读-读：能共存

读-写：不能共存

写-写：不能共存

## 代码实现

实现一个读写缓存的操作，假设开始没有加锁的时候，会出现什么情况

```
/**
 * 读写锁
 * 多个线程 同时读一个资源类没有任何问题，所以为了满足并发量，读取共享资源应该可以同时进行
 * 但是，如果一个线程想去写共享资源，就不应该再有其它线程可以对该资源进行读或写
 *
 * @author: 陌溪
 * @create: 2020-03-15-16:59
 */

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * 资源类
 */
class MyCache {

    private volatile Map<String, Object> map = new HashMap<>();
    // private Lock lock = null;

    /**
     * 定义写操作
     * 满足：原子 + 独占
     * @param key
     * @param value
     */
    public void put(String key, Object value) {
        System.out.println(Thread.currentThread().getName() + "\t 正在写入：" + key);
        try {
            // 模拟网络拥堵，延迟0.3秒
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        map.put(key, value);
        System.out.println(Thread.currentThread().getName() + "\t 写入完成");
    }

    public void get(String key) {
        System.out.println(Thread.currentThread().getName() + "\t 正在读取:");
        try {
            // 模拟网络拥堵，延迟0.3秒
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Object value = map.get(key);
        System.out.println(Thread.currentThread().getName() + "\t 读取完成：" + value);
    }


}
public class ReadWriteLockDemo {

    public static void main(String[] args) {

        MyCache myCache = new MyCache();
        // 线程操作资源类，5个线程写
        for (int i = 0; i < 5; i++) {
            // lambda表达式内部必须是final
            final int tempInt = i;
            new Thread(() -> {
                myCache.put(tempInt + "", tempInt +  "");
            }, String.valueOf(i)).start();
        }
        // 线程操作资源类， 5个线程读
        for (int i = 0; i < 5; i++) {
            // lambda表达式内部必须是final
            final int tempInt = i;
            new Thread(() -> {
                myCache.get(tempInt + "");
            }, String.valueOf(i)).start();
        }
    }
}
```

我们分别创建5个线程写入缓存

```
        // 线程操作资源类，5个线程写
        for (int i = 0; i < 5; i++) {
            // lambda表达式内部必须是final
            final int tempInt = i;
            new Thread(() -> {
                myCache.put(tempInt + "", tempInt +  "");
            }, String.valueOf(i)).start();
        }
```

5个线程读取缓存，

```
        // 线程操作资源类， 5个线程读
        for (int i = 0; i < 5; i++) {
            // lambda表达式内部必须是final
            final int tempInt = i;
            new Thread(() -> {
                myCache.get(tempInt + "");
            }, String.valueOf(i)).start();
        }
```

最后运行结果：

```
0	 正在写入：0
4	 正在写入：4
3	 正在写入：3
1	 正在写入：1
2	 正在写入：2
0	 正在读取:
1	 正在读取:
2	 正在读取:
3	 正在读取:
4	 正在读取:
2	 写入完成
4	 写入完成
4	 读取完成：null
0	 写入完成
3	 读取完成：null
0	 读取完成：null
1	 写入完成
3	 写入完成
1	 读取完成：null
2	 读取完成：null
```

我们可以看到，在写入的时候，写操作都没其它线程打断了，这就造成了，还没写完，其它线程又开始写，这样就造成数据不一致

## 解决方法

上面的代码是没有加锁的，这样就会造成线程在进行写入操作的时候，被其它线程频繁打断，从而不具备原子性，这个时候，我们就需要用到读写锁来解决了

```
/**
* 创建一个读写锁
* 它是一个读写融为一体的锁，在使用的时候，需要转换
*/
private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
```

当我们在进行写操作的时候，就需要转换成写锁

```
// 创建一个写锁
rwLock.writeLock().lock();

// 写锁 释放
rwLock.writeLock().unlock();
```

当们在进行读操作的时候，在转换成读锁

```
// 创建一个读锁
rwLock.readLock().lock();

// 读锁 释放
rwLock.readLock().unlock();
```

这里的读锁和写锁的区别在于，写锁一次只能一个线程进入，执行写操作，而读锁是多个线程能够同时进入，进行读取的操作

完整代码：

```
/**
 * 读写锁
 * 多个线程 同时读一个资源类没有任何问题，所以为了满足并发量，读取共享资源应该可以同时进行
 * 但是，如果一个线程想去写共享资源，就不应该再有其它线程可以对该资源进行读或写
 *
 * @author: 陌溪
 * @create: 2020-03-15-16:59
 */

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 资源类
 */
class MyCache {

    /**
     * 缓存中的东西，必须保持可见性，因此使用volatile修饰
     */
    private volatile Map<String, Object> map = new HashMap<>();

    /**
     * 创建一个读写锁
     * 它是一个读写融为一体的锁，在使用的时候，需要转换
     */
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    /**
     * 定义写操作
     * 满足：原子 + 独占
     * @param key
     * @param value
     */
    public void put(String key, Object value) {

        // 创建一个写锁
        rwLock.writeLock().lock();

        try {

            System.out.println(Thread.currentThread().getName() + "\t 正在写入：" + key);

            try {
                // 模拟网络拥堵，延迟0.3秒
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            map.put(key, value);

            System.out.println(Thread.currentThread().getName() + "\t 写入完成");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 写锁 释放
            rwLock.writeLock().unlock();
        }
    }

    /**
     * 获取
     * @param key
     */
    public void get(String key) {

        // 读锁
        rwLock.readLock().lock();
        try {

            System.out.println(Thread.currentThread().getName() + "\t 正在读取:");

            try {
                // 模拟网络拥堵，延迟0.3秒
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Object value = map.get(key);

            System.out.println(Thread.currentThread().getName() + "\t 读取完成：" + value);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 读锁释放
            rwLock.readLock().unlock();
        }
    }

    /**
     * 清空缓存
     */
    public void clean() {
        map.clear();
    }


}
public class ReadWriteLockDemo {

    public static void main(String[] args) {

        MyCache myCache = new MyCache();

        // 线程操作资源类，5个线程写
        for (int i = 1; i <= 5; i++) {
            // lambda表达式内部必须是final
            final int tempInt = i;
            new Thread(() -> {
                myCache.put(tempInt + "", tempInt +  "");
            }, String.valueOf(i)).start();
        }

        // 线程操作资源类， 5个线程读
        for (int i = 1; i <= 5; i++) {
            // lambda表达式内部必须是final
            final int tempInt = i;
            new Thread(() -> {
                myCache.get(tempInt + "");
            }, String.valueOf(i)).start();
        }
    }
}
```

运行结果：

```
1	 正在写入：1
1	 写入完成
2	 正在写入：2
2	 写入完成
3	 正在写入：3
3	 写入完成
4	 正在写入：4
4	 写入完成
5	 正在写入：5
5	 写入完成
2	 正在读取:
3	 正在读取:
1	 正在读取:
4	 正在读取:
5	 正在读取:
2	 读取完成：2
1	 读取完成：1
4	 读取完成：4
3	 读取完成：3
5	 读取完成：5
```

从运行结果我们可以看出，写入操作是一个一个线程进行执行的，并且中间不会被打断，而读操作的时候，是同时5个线程进入，然后并发读取操作