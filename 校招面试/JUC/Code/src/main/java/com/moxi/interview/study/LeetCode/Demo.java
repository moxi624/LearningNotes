package com.moxi.interview.study.LeetCode;

import java.util.HashMap;
import java.util.Map;

/**
 * 在整数数组中，如果一个整数的出现频次和它的数值大小相等，我们就称这个整数为「幸运数」。
 *
 * 给你一个整数数组 arr，请你从中找出并返回一个幸运数。
 *
 * 如果数组中存在多个幸运数，只需返回 最大 的那个。
 * 如果数组中不含幸运数，则返回 -1 。
 * 示例 1：
 *
 * 输入：arr = [2,2,3,4]
 * 输出：2
 * 解释：数组中唯一的幸运数是 2 ，因为数值 2 的出现频次也是 2 。
 * 示例 2：
 *
 * 输入：arr = [1,2,2,3,3,3]
 * 输出：3
 * 解释：1、2 以及 3 都是幸运数，只需要返回其中最大的 3 。
 * 示例 3：
 *
 * 输入：arr = [2,2,2,3,3]
 * 输出：-1
 * 解释：数组中不存在幸运数。
 * 示例 4：
 *
 * 输入：arr = [5]
 * 输出：-1
 * 示例 5：
 *
 * 输入：arr = [7,7,7,7,7,7,7]
 * 输出：7
 *
 *
 * @author: 陌溪
 * @create: 2020-04-18-19:28
 */
public class Demo {
    public static int findLucky(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            if(map.get(arr[i]) == null) {
                map.put(arr[i], 1);
            } else {
                int a = map.get(arr[i]) + 1;
                map.put(arr[i], a);
            }
        }
        int maxKey = -1;
        for(int key : map.keySet()) {
            if(map.get(key) == key) {
                if(key > maxKey) {
                    maxKey = key;
                }
            }
        }

        return maxKey;
    }
    public static void main(String[] args) {
        System.out.println(findLucky(new int[]{7,7,7,7,7,7,7}));
    }
}

