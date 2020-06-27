package com.moxi.interview.study.LeetCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 翻转链表
 *
 * @author: 陌溪
 * @create: 2020-06-26-17:09
 */
public class ReversePrint {
    public static void reversePrint() {
        List<String> list = new ArrayList<>();
        list.add(0, "a");
        list.add(0, "a");
        list.add(0, "a");
        for (String s : list) {
            System.out.print(s + " ");
        }

    }

    public static void main(String[] args) {
        reversePrint();
    }
}
