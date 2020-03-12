package com.moxi.interview.study.demo;

public interface Pipe {
    public boolean put(Object obj);
    public Object get() throws InterruptedException;
}
