package com.moxi.interview.study.annotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 通过反射获取对象
 *
 * @author: 陌溪
 * @create: 2020-03-29-12:43
 */
public class GetObjectByReflectionDemo {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {

        // 获取Class
        Class clazz = Class.forName("com.moxi.interview.study.annotation.User");

        // 构造一个对象，newInstance调用的是无参构造器，如果没有无参构造器的话，本方法会出错
//        User user = (User)clazz.newInstance();

        // 获取class的有参构造器
        Constructor constructor = clazz.getDeclaredConstructor(String.class, int.class, int.class);
        User user2 = (User) constructor.newInstance("小溪", 10, 10);
        System.out.println(user2);


        // 通过反射调用普通构造方法
        User user3 = (User)clazz.newInstance();
        // 获取setName 方法
        Method setName = clazz.getDeclaredMethod("setName", String.class);
        // 执行setName方法，传入对象 和 参数
        setName.invoke(user3, "小白");
        System.out.println(user3);

        System.out.println("============");
        Field age = clazz.getDeclaredField("age");
        // 关闭权限检测,这样才能直接修改字段，因为 set方法不能直接操作私有变量
        age.setAccessible(true);
        age.set(user3, 10);
        System.out.println(user3);

    }
}
