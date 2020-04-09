package com.moxi.interview.study.java8.ref;

import com.moxi.interview.study.java8.lambda.Employee;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 构造器引用
 *
 * @author: 陌溪
 * @create: 2020-04-06-10:08
 */
public class ConstructorReferenceDemo {

    /**
     * 构造器引用
     */
    public static void test() {
        Supplier<Employee> supplier = () -> new Employee("张三", 18, 13);

        // 构造器引用（调用的无参构造器）
        Supplier<Employee> supplier1 = Employee::new;
        Employee employee = supplier1.get();

        // 构造器引用（调用有参构造器，一个参数的）
        Function<Integer, Employee> function = Employee::new;
        Employee employee1 = function.apply(10);
        System.out.println(employee1.getAge());
    }


}
