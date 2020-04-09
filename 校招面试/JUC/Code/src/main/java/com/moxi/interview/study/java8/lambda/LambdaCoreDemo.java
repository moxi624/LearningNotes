package com.moxi.interview.study.java8.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Lambda四大核心函数接口
 *
 * @author: 陌溪
 * @create: 2020-04-05-22:03
 */
public class LambdaCoreDemo {
    /**
     * 消费型接口
     */
    public static void test() {
        happy(1000, (m) -> System.out.println("消费成功：" + m + "元"));
    }
    public static void happy(double money, Consumer<Double> consumer) {
        consumer.accept(money);
    }

    /**
     * 供给型接口
     */
    public static void test2() {
        List<Integer> list = getNumList(10, () -> {
          Integer a =   (int)(Math.random() * 10);
          return a;
        });

        list.stream().forEach(System.out::println);
    }

    /**
     * 产生指定个数的整数
     * @param n
     * @return
     */
    public static List<Integer> getNumList(Integer n, Supplier<Integer> supplier) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(supplier.get());
        }
        return list;
    }

    /**
     * 函数型接口
     * Function<T, R>
     */
    public static void test3() {
        String str = strHandler("abcdefg", (x) -> {
            return x.toUpperCase().substring(0, 5);
        });
        System.out.println(str);
    }

    /**
     * 需求：用于处理字符串
     */
    public static String strHandler(String str, Function<String, String> function) {
        // 使用apply方法进行处理，怎么处理需要具体实现
        return function.apply(str);
    }

    /**
     * 断言型接口，用于处理字符串
     */
    public static void test4() {
        List<String> list = Arrays.asList("abc", "abcd", "df", "cgg", "aaab");
        List<String> result = strPredict(list, (x) -> x.length() > 3);
        result.forEach(item -> {
            System.out.println(item);
        });
    }

    /**
     * 将满足条件的字符串，放入到集合中
     */
    public static List<String> strPredict(List<String> list, Predicate<String> predicate) {
        List<String> result = new ArrayList<>();
        list.forEach(item -> {
            if(predicate.test(item)) {
                result.add(item);
            }
        });
        return result;
    }


    public static void main(String[] args) {
        test4();
    }
}
