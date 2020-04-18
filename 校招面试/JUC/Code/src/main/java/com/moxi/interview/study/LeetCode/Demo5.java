package com.moxi.interview.study.LeetCode;

/**
 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。

 示例 1：

 输入: "babad"
 输出: "bab"
 注意: "aba" 也是一个有效答案。
 示例 2：

 输入: "cbbd"
 输出: "bb"

 *
 * @author: 陌溪
 * @create: 2020-04-16-23:24
 */
public class Demo5 {

    /**
     * 中心扩展法
     * @param s
     * @return
     */
    public static String longestPalindrome(String s) {
        char [] chars = s.toCharArray();
        String maxResult = "";
        // 一个中心
        for(int a=0; a<chars.length; a++) {
            int increment = a;
            int decrement = a;
            String result = chars[a]+"";
            while ( (decrement - 1) >= 0 && (increment + 1) < chars.length && (chars[decrement-1] == chars[increment + 1]) ) {
                result = chars[decrement - 1] + result + chars[increment + 1];
                decrement--;
                increment++;
            }
            if(result.length() > maxResult.length()) {
                maxResult = result;
            }
        }

        // 当没有找到回文时
        String maxResult2 = "";
        for(int a =0; a<chars.length -1; a++) {
            if(chars[a] == chars[a+1]) {
                int increment = a + 1;
                int decrement = a;
                String result = chars[a] + "" + chars[a+1];
                while ( (decrement - 1) >= 0 && (increment + 1) < chars.length && (chars[decrement-1] == chars[increment + 1]) ) {
                    result = chars[decrement - 1] + "" + result + chars[increment + 1];
                    decrement--;
                    increment++;
                }
                if(result.length() > maxResult2.length()) {
                    maxResult2 = result;
                }
            }
        }
        return maxResult.length() > maxResult2.length()? maxResult: maxResult2;
    }

    public static void main(String[] args) {
        System.out.println(longestPalindrome("babad"));
    }
}
