package com.atguigu.java.chapter06;

/**
 * 本地方法
 *
 * @author: 陌溪
 * @create: 2020-07-06-16:45
 */
public class IhaveNatives {
    public native void Native1(int x);
    native static public long Native2();
    native synchronized private float Native3(Object o);
    native void Natives(int[] ary) throws Exception;
}
