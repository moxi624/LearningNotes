package com.moxi.interview.study.lq;

/**
 * @author: 陌溪
 * @create: 2020-03-13-18:33
 */
public class Demo2 {
    public static void main(String[] args) {
        int count = 0;
        for(int a=1; a<=2019; a++) {
            String str = a + "";
            if(str.indexOf("9") > -1) {
                count ++;
            }
        }
        System.out.println(count);
    }
}
