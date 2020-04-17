package com.moxi.interview.study.LeetCode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 *
 * 示例 1:
 *
 * 输入: "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。

 示例 2:

 输入: "bbbbb"
 输出: 1
 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 示例 3:

 输入: "pwwkew"
 输出: 3
 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。

 * @author: 陌溪
 * @create: 2020-04-16-23:05
 */
public class Demo3 {
    /**
     * 暴力法，时间复杂度 O(n^3)
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring(String s) {
        char [] chars = s.toCharArray();
        String maxResult = "";
        for(int a=0; a<chars.length; a++) {
            String result = "";
            for(int b=a; b<chars.length; b++) {
                if(result.indexOf(chars[b]) > -1) {
                    break;
                } else {
                    result += chars[b];
                }
            }
            if(result.length() > maxResult.length()) {
                maxResult = result;
            }
        }
        return maxResult.length();
    }

    /**
     * 引入hashset
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring2(String s) {
        char [] chars = s.toCharArray();
        int maxResult = 0;
        for(int a=0; a<chars.length; a++) {
            Set<Character> result = new HashSet<>();
            for(int b=a; b<chars.length; b++) {
                // 利用hash来获取key
                if(result.contains(chars[b])) {
                    break;
                } else {
                    result.add(chars[b]);
                }
            }
            if(result.size() > maxResult) {
                maxResult = result.size();
            }
        }
        return maxResult;
    }

    /**
     * 官方算法
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring03(String s) {
        int n = s.length(), ans = 0;
        //创建map窗口,i为左区间，j为右区间，右边界移动
        Map<Character, Integer> map = new HashMap<>();
        for (int j = 0, i = 0; j < n; j++) {
            // 如果窗口中包含当前字符，
            if (map.containsKey(s.charAt(j))) {
                //左边界移动到 相同字符的下一个位置和i当前位置中更靠右的位置，这样是为了防止i向左移动
                i = Math.max(map.get(s.charAt(j)), i);
            }
            //比对当前无重复字段长度和储存的长度，选最大值并替换
            //j-i+1是因为此时i,j索引仍处于不重复的位置，j还没有向后移动，取的[i,j]长度
            ans = Math.max(ans, j - i + 1);
            // 将当前字符为key，下一个索引为value放入map中
            // value为j+1是为了当出现重复字符时，i直接跳到上个相同字符的下一个位置，if中取值就不用+1了
            map.put(s.charAt(j), j+1);
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring03("pwwkew"));
    }
}
