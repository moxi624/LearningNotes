package com.moxi.interview.study.java8.optional;

import com.moxi.interview.study.java8.lambda.Employee;

import java.util.Optional;

/**
 * @author: 陌溪
 * @create: 2020-04-06-15:27
 */
public class OptionalDemo {
    public static void test() {
        Optional<Employee> optionalEmployee = Optional.of(new Employee());
        Employee employee = optionalEmployee.get();
        System.out.println(employee.getAge());
    }

    public static void test2() {
        Optional<Employee> employeeOptional = Optional.empty();
        System.out.println(employeeOptional.get());
    }

    /**
     * 有值就获取值，没值就使用默认值
     */
    public static void test3() {
        Optional<Employee> optionalEmployee = Optional.of(new Employee());

    }
    public static void main(String[] args) {
        test2();
    }
}
