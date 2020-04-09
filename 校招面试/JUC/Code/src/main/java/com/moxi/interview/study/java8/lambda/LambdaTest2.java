package com.moxi.interview.study.java8.lambda;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Lambda表达式学习
 *
 * @author: 陌溪
 * @create: 2020-04-05-17:56
 */
public class LambdaTest2 {

    /**
     * 调用Collections.sort()方法，通过定制排序比较两个Employee（先比较年龄比，年龄相同比较姓名），使用Lambda表达式
     */
    public static void test() {
        List<Employee> employees = Arrays.asList(
                new Employee("张三", 18, 3333),
                new Employee("李四", 38, 55555),
                new Employee("王五", 50, 6666.66),
                new Employee("赵六", 16, 77777.77),
                new Employee("田七", 8, 8888.88)
        );
        Collections.sort(employees, (e1, e2) -> {
            if(e1.getAge() == e2.getAge()) {
                return e1.getName().compareTo(e2.getName());
            } else {
                return Integer.compare(e1.getAge(), e2.getAge());
            }
        });

        employees.stream().map(Employee::getName).forEach(System.out::println);
    }
    public static void main(String[] args) {
        test();
    }
}
