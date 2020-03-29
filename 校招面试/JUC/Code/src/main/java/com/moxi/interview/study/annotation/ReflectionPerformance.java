package com.moxi.interview.study.annotation;

import java.lang.reflect.Method;

/**
 * 反射性能
 *
 * @author: 陌溪
 * @create: 2020-03-29-14:55
 */
public class ReflectionPerformance {

    /**
     * 普通方式调用
     */
    public static void test01() {
        User user = new User();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000000000; i++) {
            user.getName();
        }
        long endTime = System.currentTimeMillis();

        System.out.println("普通方式执行10亿次getName的时间:" + (endTime - startTime) + " ms");
    }

    /**
     * 反射方式调用
     */
    public static void test02() throws Exception {
        Class clazz = Class.forName("com.moxi.interview.study.annotation.User");
        Method getName = clazz.getDeclaredMethod("getName", null);
        User user = (User) clazz.newInstance();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000000000; i++) {
            getName.invoke(user, null);
        }
        long endTime = System.currentTimeMillis();

        System.out.println("反射方式执行10亿次getName的时间:" + (endTime - startTime) + " ms");
    }

    /**
     * 反射方式调用，关闭权限检查
     */
    public static void test03() throws Exception {
        Class clazz = Class.forName("com.moxi.interview.study.annotation.User");
        Method getName = clazz.getDeclaredMethod("getName", null);
        User user = (User) clazz.newInstance();
        long startTime = System.currentTimeMillis();
        getName.setAccessible(true);
        for (int i = 0; i < 1000000000; i++) {
            getName.invoke(user, null);
        }
        long endTime = System.currentTimeMillis();

        System.out.println("反射方式执行10亿次getName的时间:" + (endTime - startTime) + " ms");
    }
    public static void main(String[] args) throws Exception {
        test01();
        test02();
        test03();
    }
}
