package com.moxi.interview.study.ref;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * 弱引用
 *
 * @author: 陌溪
 * @create: 2020-03-24-10:18
 */
public class WeakReferenceDemo {
    public static void main(String[] args) {
        Object o1 = new Object();
        WeakReference<Object> weakReference = new WeakReference<>(o1);
        System.out.println(o1);
        System.out.println(weakReference.get());
        o1 = null;
        System.gc();
        System.out.println(o1);
        System.out.println(weakReference.get());
    }
}
