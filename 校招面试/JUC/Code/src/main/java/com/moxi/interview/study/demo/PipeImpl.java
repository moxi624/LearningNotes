package com.moxi.interview.study.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * PipeImpl
 *
 * @author: 陌溪
 * @create: 2020-03-11-19:55
 */
public class PipeImpl implements Pipe{
    private List buffer = new ArrayList();

    @Override
    public synchronized  boolean put(Object obj) {
        boolean bAdded = buffer.add(obj);
        notify();
        return bAdded;

    }

    @Override
    public synchronized  Object get() throws InterruptedException {
        while(buffer.isEmpty()) {
            wait(); //pipe empty - wait
        }
        Object obj = buffer.remove(0);
        return obj;
    }
}
