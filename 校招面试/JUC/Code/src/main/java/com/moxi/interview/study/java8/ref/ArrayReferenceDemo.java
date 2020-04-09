package com.moxi.interview.study.java8.ref;

import java.util.function.Function;

/**
 * 数组引用
 *
 * @author: 陌溪
 * @create: 2020-04-06-10:18
 */
public class ArrayReferenceDemo {

    public static void test() {
        Function<Integer, String[]> function = (x) -> new String[x];
        function.apply(20);

        // 数组引用
        Function<Integer, String[]> function1 = String[]::new;
        String[] strArray = function1.apply(20);
        System.out.println(strArray.length);
    }
}
