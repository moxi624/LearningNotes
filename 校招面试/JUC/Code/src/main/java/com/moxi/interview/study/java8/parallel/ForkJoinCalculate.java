package com.moxi.interview.study.java8.parallel;

import java.util.concurrent.RecursiveTask;

/**
 * @author: 陌溪
 * @create: 2020-04-06-12:27
 */
public class ForkJoinCalculate extends RecursiveTask<Long> {

    private long start;
    private long end;

    private  static final long THRESHOLD = 10000;

    @Override
    protected Long compute() {
        long length = end - start;
        if(length <= THRESHOLD) {

        }
        return null;
    }
}
