package com.moxi.interview.study.demo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * WordSortFilter
 *
 * @author: 陌溪
 * @create: 2020-03-11-19:58
 */
public class WordSortFilter extends Thread{
    private Pipe pipe = null;
    private List<String> wordList = new ArrayList<String>();
    public WordSortFilter(Pipe _pipe) {
        pipe = _pipe;
    }

    @Override
    public void run() {
        String word = null;
        try {
            while ((word = (String) pipe.get()) != null) {
                wordList.add(word);
            }
        } catch (InterruptedException intrtex) {}
        //now sort the word list
        Collections.sort(wordList);
        //print the sorted word list and write it to a file
        try {
            FileWriter fw = new FileWriter("sortedwords.txt");
            for (String s : wordList) {
                System.out.println(s);
                fw.write(s + "\n");
            }
            fw.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

}
