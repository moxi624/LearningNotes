package com.moxi.interview.study.demo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * WordSortGenerator
 *
 * @author: 陌溪
 * @create: 2020-03-11-19:57
 */
public class WordSortGenerator extends Thread{
    private Pipe pipe = null;
    public WordSortGenerator(Pipe _pipe) {
        pipe = _pipe;
    }

    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("words.txt"));
            String word = null;
            while((word = br.readLine()) != null) {
                pipe.put(word);
            }
            pipe.put(null);
            br.close();
        }catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }
}
