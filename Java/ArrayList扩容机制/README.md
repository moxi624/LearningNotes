# 浅谈ArrayList及扩容机制

## ArrayList

ArrayList就是动态数组，其实就是Array的复杂版本，它提供了动态的添加元素和删除元素的方法，同时实现了Collection 和 List接口，能够灵活的设置数组的大小。

通过源码的分析，我们可以看到ArrayList有三种构造方法

- 空的构造函数
- 根据传入的数值大小，创建指定长度的数组
- 通过传入Collection元素列表进行生成

```java
// 默认的容量大小
private static final int DEFAULT_CAPACITY = 10;
// 定义的空的数组
private static final Object[] EMPTY_ELEMENTDATA = {};
// 不可以被序列化的数组，相当于存储元素的缓冲区
transient Object[] elementData;
// 这个list集合的长度
private int size;

 /**
  * 空的构造函数
  */
    public ArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }
    
 /**
   * 根据用户传入的容量大小构造一个list集合，长度可以大于等于0，但是如果为负数会抛出异常
   */
    public ArrayList(int initialCapacity) {
        // 如果初始容量大于0
        if (initialCapacity > 0) {
            // 创建一个大小为initialCapacity的数组
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            // 创建一个空数组
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
           // 如果为负数，直接抛出异常
            throw new IllegalArgumentException("Illegal Capacity: "+
                                               initialCapacity);
        }
    }
    
    
 /**
   * 构造包含指定collection元素的列表，这些元素利用该集合的迭代器按顺序返回
   * 如果指定的集合为null，throws NullPointerException。
   */
    public ArrayList(Collection<? extends E> c) {
        elementData = c.toArray();
        if ((size = elementData.length) != 0) {
            // c.toArray might (incorrectly) not return Object[] (see 6260652)
            if (elementData.getClass() != Object[].class)
                elementData = Arrays.copyOf(elementData, size, Object[].class);
        } else {
            // replace with empty array.
            this.elementData = EMPTY_ELEMENTDATA;
        }
    }
    
```

## ArrayList相关问题

### ArrayList优缺点

#### 优点

ArrayList底层是以数组实现，是一种随机访问模式，再加上它实现了RandomAccess接口，因此在执行get方法的时候很快。

ArrayList在顺序添加元素的时候非常场面，只是往数组中添加了一个元素而已，根据下标遍历元素，效率高。

可以自动扩容，默认为每次扩容为原来的1.5倍

#### 缺点

数组里面（除了末尾）插入和删除元素效率不高，因为需要移动大量的元素

> ArrayList在小于扩容容量的情况下，其实增加操作效率非常高，在涉及扩容的情况下，添加操作效率确实低，删除操作需要移位拷贝。
>
> 同时因为ArrayList中增加（扩容）或者删除元素要调用System.arrayCopy()这种效率很低的方法进行处理，所以遇到数据量略大 或者 需要频繁插入和删除操作的时候，效率就比较低了，如果遇到上述的场景，那么就需要使用LinkedList来代替
>
> 因为ArrayList的优点在于构造好数组后，频繁的访问元素的效率非常高。

### ArrayList和Vector的区别

首先List接口一共有三个实现类：ArrayList、Vector、LinkedList

Vector 和 ArrayList一样，都是通过数组来实现的，不同的是 Vector支持线程的同步，也就是说某一个时刻下，只有一个线程能够写Vector，避免了多线程同时写而引起的不一致的问题，但实现同步需要很高的代Synchronized 因此，Vector的效率比ArrayList慢

同时Vector 和 ArrayList的扩容机制有差异的，Vector每次扩容为数组长度的一倍，而ArrayList则是原来数组长度的1.5倍。

## 扩容机制

### add方法

首先我们来看看ArrayList中的add方法是如何添加元素的

```java
 // 将指定的元素加到列表的末尾
 public boolean add(E e) {
        // 添加元素之前，先调用ensureCapacityInternal方法
        ensureCapacityInternal(size + 1);  // Increments modCount!!
        // 这里看到的ArrayList添加元素的实质相当于为数组赋值
        elementData[size++] = e;
        return true;
}
```

### ensureCapacityInternal方法

当add进一个元素的时候，minCapacity为1，此时取两者的最大值

```java
// 得到最小的扩容量
private void ensureCapacityInternal(int minCapacity) {
        // 当一开始是默认空的列表
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            // 获取默认的容量和传入参数的最大值
            // DEFAULT_CAPACITY: 10 , minCapacity: 1
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        ensureExplicitCapacity(minCapacity);
}
```

### ensureExplicitCapacity方法

我们看到，上述的操作在执行完后，会调用 ensureExplicitCapacity方法，该方法主要就是为了判断是否触发扩容

```java
// 判断是否需要扩容
private void ensureExplicitCapacity(int minCapacity) {
        modCount++;

        // overflow-conscious code
        if (minCapacity - elementData.length > 0)
            // 调用grow方法进行扩容
            grow(minCapacity);
}
```

### grow方法

当添加元素的时候，大于当前数组的长度，就会触发grow操作，该操作将会对数组进行扩容

```
int newCapacity = oldCapacity + (oldCapacity >> 1)
```

核心代码是上面这句，将原来的数组长度，进行扩容到1.5倍，然后在执行拷贝命令，将旧数组中的内容，拷贝到新的数组中，实现元素的扩容操作。

```java
elementData = Arrays.copyOf(elementData, newCapacity);
```

> 关于：System.arrayCopy()和Arrays.copyOf()方法 
>
> 看两者源代码可以发现 copyOf() 内部实际调用了 System.arraycopy() 方法
>
> arraycopy() 需要目标数组，将原数组拷贝到你自己定义的数组里或者原数组，而且可以选择拷贝的起点和长度以及放入新数组中的位置 copyOf() 是系统自动在内部新建一个数组，并返回该数

完整代码如下

```java
// 需要分配的数组大小
private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

private void grow(int minCapacity) {
        // 集合的容量
        int oldCapacity = elementData.length;
        // 新的集合的容量（在这里运用了位运算，位运算是计算机最快的，右移一位，所以新容量是1.5倍）
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        // 如果新容量小于添加的集合的容量，则把该容量替换
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
            
        /** 如果新容量大于 MAX_ARRAY_SIZE,进入(执行) `hugeCapacity()` 方法来比较 minCapacity 和           * MAX_ARRAY_SIZE，如果minCapacity大于最大容量，则新容量则为`Integer.MAX_VALUE`，否则，           * 新容量大小则为 MAX_ARRAY_SIZE 即为 `Integer.MAX_VALUE - 8`。
          */
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        // minCapacity is usually close to size, so this is a win:
        // 将原数组copy到新的数组中
        elementData = Arrays.copyOf(elementData, newCapacity);
    }
    
    /** 如果新容量大于 MAX_ARRAY_SIZE,进入(执行) `hugeCapacity()` 方法来比较 minCapacity 和           * MAX_ARRAY_SIZE，如果minCapacity大于最大容量，则新容量则为`Integer.MAX_VALUE`，否则，           * 新容量大小则为 MAX_ARRAY_SIZE 即为 `Integer.MAX_VALUE - 8`。
      */
    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
            Integer.MAX_VALUE :
            MAX_ARRAY_SIZE;
    }
```

### 总结

通过将上面的方法进行梳理，我们能够总结出以下的几点

- 当我们add进第一个元素到ArrayList的时候，elementData.length为0（因为还是一个空的list，有种懒加载的感觉？？），但是此时执行了ensureCapacityInternal() 方法，通过默认的比较，此时会得到minCapacity为10，此时minCapacity - elementData.length > 0满足，所以会进入grow(minCapacity)方法
- 当add第二个元素的时候，minCapacity为2，此时elementData.length()在添加第一个元素后，扩容变成了10，此时minCapacity - elementData.length > 0 不成立，所以不会进入（执行）grow(minCapacity)方法。
- 同时我们继续添加元素 3,4  .... 11，到第11个元素的时候，minCapacity(11) 比 10更大，那么会触发grow操作

## 参考

- https://blog.csdn.net/jmlqqs/article/details/107128147
- https://www.cnblogs.com/clover-forever/p/13155160.html