package com.moxi.interview.study.annotation;

/**
 * 什么是注解
 *
 * @author: 陌溪
 * @create: 2020-03-28-22:32
 */
public class AnnotationDemo extends Object{

    // @Override 表示 重写的注解
    @Override
    public String toString() {
        return super.toString();
    }

    // Deprecated：表示不推荐
    @Deprecated
    public static void test() {

    }

    public static void main(String[] args) {

        test();
    }
}
