package com.moxi.interview.study.demo;

/**
 * Start
 *
 * @author: 陌溪
 * @create: 2020-03-11-20:00
 */
public class Start {
    public static void main(String[] args) {
        Pipe pipe = new PipeImpl();
        Thread wordGenerator = new WordSortGenerator(pipe);
        Thread wordSortFilter = new WordSortFilter(pipe);
        wordGenerator.start();
        wordSortFilter.start();
    }
}
