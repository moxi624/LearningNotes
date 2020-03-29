package com.moxi.interview.study.annotation;

import java.lang.annotation.*;

/**
 * 元注解
 *
 * @author: 陌溪
 * @create: 2020-03-28-22:57
 */
public class MateAnnotationDemo {

    // 注解可以显示赋值，如果没有默认值，我们就必须给注解赋值
    @MyAnnotation(schools = {"大学"})
    public void test(){

    }

}

/**
 * 定义一个注解
 */
@Target(value={ElementType.METHOD, ElementType.TYPE})  // target表示我们注解应用的范围，在方法上，和类上有效
@Retention(RetentionPolicy.RUNTIME)   // Retention：表示我们的注解在什么时候还有效，运行时候有效
@Documented   // 表示说我们的注解是否生成在java doc中
@Inherited   // 表示子类可以继承父类的注解
@interface MyAnnotation {

    // 注解的参数：参数类型 + 参数名()
    String name() default "";

    int age() default 0;

    // 如果默认值为-1，代表不存在
    int id() default -1;

    String[] schools();
}
