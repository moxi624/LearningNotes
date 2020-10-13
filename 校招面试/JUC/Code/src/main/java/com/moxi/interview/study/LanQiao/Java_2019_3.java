package com.moxi.interview.study.LanQiao;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 【问题描述】

 给定数列 1, 1, 1, 3, 5, 9, 17, …，从第 4 项开始，每项都是前 3 项的和。求第 20190324 项的最后 4 位数字。

 * @author: 陌溪
 * @create: 2020-10-11-11:14
 */
public class Java_2019_3 {
    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        map.put(1, 1);
        map.put(2, 1);

        for (int i = 3; i < 20190324; i++) {
            map.put(i, map.get(i-1) + map.get(i-2) + map.get(i-3));
        }
        System.out.println(map.get(20190323));
    }
}
