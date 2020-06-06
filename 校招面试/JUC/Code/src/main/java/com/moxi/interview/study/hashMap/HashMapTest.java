package com.moxi.interview.study.hashMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JDK7中的HashMap
 *
 * @author: 陌溪
 * @create: 2020-06-05-21:08
 */
public class HashMapTest {
    public static void main(String[] args) {

        // 数组+链表
        Map<String, String> map = new HashMap<>();
        map.put("123", "123");
        System.out.println(map.get("123"));

        List<Integer> arrayList = new ArrayList<>();
        arrayList.add(1, 10);
    }
}
