package com.moxi.interview.study.java8.stream;

import com.moxi.interview.study.java8.lambda.Employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Stream流
 *
 * @author: 陌溪
 * @create: 2020-04-06-11:03
 */
public class StreamAPiDemo {

    private static void test1() {
        // 创建Stream
        // 1.通过Collection系列集合的 stream() 或者 parallelStream() 获取流
        List<String> list = new ArrayList<>();
        Stream<String> stream =  list.stream();

        // 2. 通过Arrays 中的静态方法，获取数组流
        Employee[] employees = new Employee[10];
        Stream<Employee> stream1 = Arrays.stream(employees);

        // 3.通过Stream中的静态方法of()，获取流
        Stream<String> stream3 = Stream.of("aa", "bb", "cc");

        // 4.创建无限流
        Stream<Integer> stream4 = Stream.iterate(0, (x) -> x +2 );
        stream4.limit(10).forEach(System.out::println);
    }

    /**
     * 筛选与切片
     */
    public static void test() {
        List<Employee> employees = Arrays.asList(
                new Employee("张三", 18, 3333),
                new Employee("李四", 38, 55555),
                new Employee("王五", 50, 6666.66),
                new Employee("赵六", 16, 77777.77),
                new Employee("田七", 8, 8888.88)
        );
        Stream<Employee> stream = employees.stream();
        stream.filter((x) -> x.getAge() > 30)
                .limit(2)
                .forEach(System.out::println);
    }

    public static void test2() {
        List<String> list = Arrays.asList("ccc", "aaa" , "ddd", "bbb");
        list.stream().map((x) -> x.toUpperCase()).forEach(System.out::println);
    }

    public static void test3() {
        List<String> list = Arrays.asList("aaa", "bbb", "ccc", "ddd");
        // 自然排序，按照字典进行排序
        list.stream().sorted().forEach(System.out::println);

        // 定制排序
        List<Employee> employees = Arrays.asList(
                new Employee("张三", 18, 3333),
                new Employee("李四", 38, 55555),
                new Employee("王五", 50, 6666.66),
                new Employee("赵六", 16, 77777.77),
                new Employee("田七", 8, 8888.88)
        );
        employees.stream().sorted((e1, e2) -> {
            if(e1.getAge() == e2.getAge()) {
                return e1.getName().compareTo(e2.getName());
            } else {
                return Integer.compare(e1.getAge(), e2.getAge());
            }
        }).forEach(System.out::println);
    }

    /**
     * 终止操作
     */
    public static void test4() {
        // 定制排序
        List<Employee> employees = Arrays.asList(
                new Employee("张三", 18, 3333),
                new Employee("李四", 38, 55555),
                new Employee("王五", 50, 6666.66),
                new Employee("赵六", 16, 77777.77),
                new Employee("田七", 8, 8888.88)
        );
        Boolean isAllMatch = employees.stream().allMatch((x) -> x.getAge() > 10);
        System.out.println("是否匹配所有元素:" + isAllMatch);

        Boolean isAnyMatch = employees.stream().anyMatch((x) -> x.getAge() > 10);
        System.out.println("是否匹配至少一个元素:" + isAnyMatch);
    }

    /**
     * 规约
     */
    public static void test5() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5 ,6 ,7 ,8 , 9, 10);
        // 按照下面的规则进行累加操作
        // reduce的规约，就是把前面定义的起始值，作为了x
        Integer num = list.stream().reduce(0, (x, y) -> x + y);
        System.out.println(num);

        String text = "i love you";
        String [] textList = text.split(" ");
        System.out.println(Arrays.stream(textList).count());
    }

    /**
     * 收集器
     */
    public static void test6() {
        List<Employee> employees = Arrays.asList(
                new Employee("张三", 18, 3333),
                new Employee("李四", 38, 55555),
                new Employee("王五", 50, 6666.66),
                new Employee("赵六", 16, 77777.77),
                new Employee("田七", 8, 8888.88)
        );
        // 收集放入list中
        List<String> list = employees.stream().map(Employee::getName).collect(Collectors.toList());
        list.forEach(System.out::println);
    }


    public static void main(String[] args) {
        test6();
    }

}
