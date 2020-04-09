package com.moxi.interview.study.java8.lambda;

import java.util.*;

/**
 * Lambda表达式
 * 和匿名内部类比较
 * @author: 陌溪
 * @create: 2020-04-05-11:59
 */
public class LambdaDemo {



    /**
     * 原来使用匿名内部类
     */
    public static void test() {
        // 使用匿名内部类，重写Intger的 compare方法
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        };

        // 传入比较的方法
        TreeSet<Integer> ts = new TreeSet<>(comparator);
    }

    /**
     * 使用Lambda表达式解决匿名内部类需要编写大量模板语言的问题
     */
    public static void test2() {
        Comparator<Integer> comparator = (x, y) -> Integer.compare(x, y);

        // 传入比较的方法
        TreeSet<Integer> ts = new TreeSet<>(comparator);
    }

    /**
     * 获取当前公司员工年龄大于35
     */
    public static void test3() {
        List<Employee> employees = Arrays.asList(
                new Employee("张三", 18, 3333),
                new Employee("李四", 38, 55555),
                new Employee("王五", 50, 6666.66),
                new Employee("赵六", 16, 77777.77),
                new Employee("田七", 8, 8888.88)
        );

        filterEmployee(employees, new FilterEmployeeByAge());
    }

    /**
     * 获取当前公司员工薪资大于50000
     */
    public static void test4() {
        List<Employee> employees = Arrays.asList(
                new Employee("张三", 18, 3333),
                new Employee("李四", 38, 55555),
                new Employee("王五", 50, 6666.66),
                new Employee("赵六", 16, 77777.77),
                new Employee("田七", 8, 8888.88)
        );

        filterEmployee(employees, new FilterEmployeeBySalary());
    }

    /**
     * 优化方式，采用匿名内部类的方式
     */
    public static void test5() {
        List<Employee> employees = Arrays.asList(
                new Employee("张三", 18, 3333),
                new Employee("李四", 38, 55555),
                new Employee("王五", 50, 6666.66),
                new Employee("赵六", 16, 77777.77),
                new Employee("田七", 8, 8888.88)
        );

        // 匿名内部类
        filterEmployee(employees, new MyPredicte<Employee>() {
            @Override
            public boolean test(Employee employee) {
                return employee.getSalary() <= 5000;
            }
        });
    }

    /**
     * 使用Lambda表达式优化
     */
    public static void test6() {
        List<Employee> employees = Arrays.asList(
                new Employee("张三", 18, 3333),
                new Employee("李四", 38, 55555),
                new Employee("王五", 50, 6666.66),
                new Employee("赵六", 16, 77777.77),
                new Employee("田七", 8, 8888.88)
        );

        List<Employee> list = filterEmployee(employees, (e) -> e.getSalary() <= 5000);
        list.forEach(System.out::println);

    }

    /**
     * 不使用策略模式
     */
    public static void test7() {
        List<Employee> employees = Arrays.asList(
                new Employee("张三", 18, 3333),
                new Employee("李四", 38, 55555),
                new Employee("王五", 50, 6666.66),
                new Employee("赵六", 16, 77777.77),
                new Employee("田七", 8, 8888.88)
        );
        employees.stream().filter(e-> e.getSalary() >= 5000).limit(2).forEach(System.out::println);

        System.out.println("=========");
        employees.stream().map(Employee::getName).forEach(System.out::println);
    }

    /**
     * 策略模式
     * @param list
     * @param mp
     * @return
     */
    public static List<Employee> filterEmployee(List<Employee> list, MyPredicte<Employee> mp) {
        List<Employee> emps = new ArrayList<>();
        for (Employee emp : list) {
            if(mp.test(emp)) {
                emps.add(emp);
            }
        }
        return emps;
    }



    public static void main(String[] args) {
        test7();
    }
}
