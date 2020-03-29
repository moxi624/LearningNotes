package com.moxi.interview.study.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 获取运行时类信息
 * @author: 陌溪
 * @create: 2020-03-29-12:13
 */
public class GetClassInfo {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, NoSuchMethodException {
        Class clazz = Class.forName("com.moxi.interview.study.annotation.User");

        // 获取类名字
        System.out.println(clazz.getName()); // 包名 + 类名
        System.out.println(clazz.getSimpleName()); // 类名

        // 获取类属性
        System.out.println("================");
        // 只能找到public属性
        Field [] fields = clazz.getFields();

        // 找到全部的属性
        Field [] fieldAll = clazz.getDeclaredFields();

        for (int i = 0; i < fieldAll.length; i++) {
            System.out.println(fieldAll[i]);
        }

        // 获取指定属性的值
        Field name = clazz.getDeclaredField("name");

        // 获取方法
        Method [] methods = clazz.getDeclaredMethods(); // 获取本类和父类的所有public方法
        Method [] methods2 = clazz.getMethods(); // 获取本类所有方法

        // 获得指定方法
        Method method = clazz.getDeclaredMethod("getName", null);

        // 获取方法的时候，可以把参数也丢进去，这样因为避免方法重载，而造成不知道加载那个方法
        Method method2 = clazz.getDeclaredMethod("setName", String.class);

    }
}
