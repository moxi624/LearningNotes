前言
--

最近很多模块使用了Redis进行数据的缓存，然后遇到一个问题就是删除缓存，有的键是这样的方式进行存储的

![](http://image.moguit.cn/e1b0b0ee535f44e2b168dcb4ee4e2e11)

我们能发现，它们都是有特定的前缀的，如果我们需要根据指定前缀删除的话，因为redis没有提供根据前缀来删除key的方法

但是提供了另外一个方法，就是根据模糊查询出符合条件的key，然后在调用delete方法删除，具体代码为

    // 获取Redis中特定前缀
    Set<String> keys = stringRedisTemplate.keys("BLOG_SORT_BY_MONTH:"  + "*");
    
    // 删除
    stringRedisTemplate.delete(keys);

> 需要注意的是：keys的操作会导致数据库暂时被锁住，其他的请求都会被堵塞；业务量大的时候会出问题