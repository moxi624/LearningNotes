package com.moxi.interview.study.java8.lambda;

import java.util.Comparator;
import java.util.function.Consumer;

/**
 * Lambda表达式学习
 *
 * @author: 陌溪
 * @create: 2020-04-05-17:56
 */
public class LambdaTest {

    /**
     * 没有参数，没有返回值
     */
    public static void test() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("hello");
            }
        };

        System.out.println("=========");

        Runnable runnable = () -> {
            System.out.println("hello lambda");
        };
    }

    /**
     * 一个参数，没有返回值
     */
    public static void test2() {
        Consumer<String> consumer = (x) -> System.out.println(x);
        consumer.accept("我在bilibili");
    }

    /**
     * 多个参数，有返回值
     */
    public static void test3() {
        Comparator<Integer> comparator = (x, y) -> {
            System.out.println("函数式接口");
            return Integer.compare(x, y);
        };
    }

    /**
     * 多个参数，函数体只有一条，并且有返回值时
     */
    public static void test4() {

        Comparator<Integer> comparator = (Integer x, Integer y) -> Integer.compare(x, y);
    }

    /**
     * 需求：对一个数进行运算
     */
    public static void test5() {
        Integer value = operation(100, (x) -> x*x);
        System.out.println(value);
    }

    public static Integer operation(Integer num, MyFun myFun) {
        return myFun.getValue(num);
    }

    public static void main(String[] args) {
        test5();
    }
}
