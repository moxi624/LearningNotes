package com.moxi.interview.study.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Spring项目启动
 *
 * @author: 陌溪
 * @create: 2020-04-02-9:07
 */
public class Test {
    public static void main(String[] args) {
        // 初始化
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        System.out.println(annotationConfigApplicationContext.getBean(BeanTest.class));



    }
}
