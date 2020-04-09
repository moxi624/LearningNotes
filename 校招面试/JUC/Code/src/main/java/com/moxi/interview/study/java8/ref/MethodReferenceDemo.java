package com.moxi.interview.study.java8.ref;

import com.moxi.interview.study.java8.lambda.Employee;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 方法引用
 * @author: 陌溪
 * @create: 2020-04-06-9:37
 */
public class MethodReferenceDemo {

    /**
     * 格式： 对象::实例方法名
     */
    public static void test() {
        Consumer<String> consumer = (x) -> System.out.println(x);

        // 使用方法引用完成（也就是上述的方法体中，已经有Lambda实现了，那就可以用方法引用的方式实现）
        // 同时使用方法引用的时候，需要保证：参数列表和返回值类型相同
        PrintStream ps = System.out;
        Consumer<String> consumer2 = ps::println;
    }

    /**
     * 格式： 对象::实例方法名
     */
    public static void test2() {
        Employee employee = new Employee("张三", 12, 5555);
        Supplier<String> supplier = () -> employee.getName();
        System.out.println(supplier.get());

        // 使用方法引用
        Supplier<String> supplier1 = employee::getName;
        System.out.println(supplier1.get());
    }

    /**
     * 类::静态方法名
     */
    public static void test3() {
        Comparator<Integer> comparator = (x, y) -> Integer.compare(x, y);

        // 使用方法引用
        Comparator<Integer> comparator2 = Integer::compare;
    }

    /**
     * 类::实例方法名
     */
    public static void test4() {
        // 比较
        BiPredicate<String, String> bp = (x, y) -> x.equals(y);
        System.out.println(bp.test("abc", "abc"));

        // 使用方法引用
        BiPredicate<String, String> bp2 = String::equals;
        System.out.println(bp2.test("abc", "abc"));

    }

    public static void main(String[] args) {
        test4();
    }

}
