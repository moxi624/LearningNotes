package com.moxi.interview.study.annotation;

import java.util.ArrayList;
import java.util.List;

/**
 * 类加载流程
 *
 * @author: 陌溪
 * @create: 2020-03-29-11:02
 */
class SuperA {

    static int superM = 200;

    static {
        System.out.println("父类静态代码块初始化");
    }

    public SuperA() {
        System.out.println("父类构造函数初始化");
    }
}
class A extends SuperA{
    static {
        System.out.println("静态代码块初始化");
        m = 300;
    }

    static int m = 100;

    static final int n = 200;

    public A() {
        System.out.println("A类的无参构造方法");
    }

}
public class ClassLoaderDemo {

    public static void main(String[] args) {

        // 调用父类的 常量成员变量，不会初始化子类相关内容，但是会调用父类的静态代码块
        System.out.println(A.superM);;

        System.out.println("===============");

        // 常量成员变量引用，不会初始化，但是会调用静态代码块
        System.out.println(A.m);;

        System.out.println("===============");

        // final修饰的常量成员变量引用，不会初始化，也不会执行静态代码块
        System.out.println(A.n);;

        System.out.println("===============");

        // 数组类引用，不会初始化
        List<A> list = new ArrayList<A>();
    }
}
