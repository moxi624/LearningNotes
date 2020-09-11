package com.moxi.interview.study.LeetCode;

import java.util.*;

/**
 * 青牛小学
 *
 * @author: 陌溪
 * @create: 2020-09-08-19:10
 */
public class Demo6 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int count = sc.nextInt();
        List<String> list = new ArrayList<>();
        for (int i = 0; i <= count+1; i++) {
            list.add(sc.nextLine());
        }

        System.out.println(list.get(list.size() -1));
        String result = list.get(list.size() -1);

        result = result.replace(".", "");
        result = result.replace(" ", "");

        char ch [] = result.toCharArray();
        Map<String, Integer> chCountMap = new HashMap<>();
        for (int i = 0; i < ch.length; i++) {

            System.out.println(ch[i]);
        }

    }
}
