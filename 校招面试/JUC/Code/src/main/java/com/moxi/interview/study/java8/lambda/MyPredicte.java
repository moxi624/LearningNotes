package com.moxi.interview.study.java8.lambda;

/**
 * 接口，用于解决重复条件
 * @param <T>
 */
@FunctionalInterface
public interface MyPredicte<T> {
    public boolean test(T t);
}
