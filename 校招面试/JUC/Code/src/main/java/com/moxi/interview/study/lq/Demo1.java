package com.moxi.interview.study.lq;

/**
 * @author: 陌溪
 * @create: 2020-03-13-18:26
 */
public class Demo1 {

    public static void main(String[] args) {
        int count = 0;
        for(int a=1; a<=1200000; a++) {
            if(1200000 % a == 0) {
                count ++;
            }
        }
        System.out.println(count);
    }
}
