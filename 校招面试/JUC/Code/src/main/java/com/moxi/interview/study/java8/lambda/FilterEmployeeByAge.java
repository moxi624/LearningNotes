package com.moxi.interview.study.java8.lambda;

/**
 * 按年龄过滤
 *
 * @author: 陌溪
 * @create: 2020-04-05-12:23
 */
public class FilterEmployeeByAge implements MyPredicte<Employee> {

    @Override
    public boolean test(Employee employee) {
        return employee.getAge() > 35;
    }
}
