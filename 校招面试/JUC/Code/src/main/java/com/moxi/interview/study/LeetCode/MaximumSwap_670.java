package com.moxi.interview.study.LeetCode;

/**
 * 最大交换
 *
 * @author: 陌溪
 * @create: 2020-06-30-8:59
 */
public class MaximumSwap_670 {
    public static int maximumSwap(int num) {
        char[] c = String.valueOf(num).toCharArray();
        int max = Integer.MIN_VALUE;
        int max_index = 0;
        int [] arr = new int[c.length];
        arr[c.length - 1] = c.length - 1;

        for (int i = c.length - 1; i >= 0; i --) {
            if (c[i] - '0' > max) {
                max = c[i] - '0';
                max_index = i;
            }
            arr[i] = max_index;
        }
        for (int i = 0; i < c.length; i ++) {
            if (arr[i] != i && c[arr[i]] != c[i]) {
                char tmp = c[i];
                c[i] = c[arr[i]];
                c[arr[i]] = tmp;
                break;
            }
        }
        return Integer.parseInt(new String(c));

    }

    public static void main(String[] args) {
        maximumSwap(2376);
    }
}
