package com.moxi.interview.study.byteDance;

/**
 * 无重复字符的最长子串
 * lengthOfLongestSubstring
 *
 * 输入: "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 *
 * 输入: "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 *
 * 输入: "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 *
 * @author: 陌溪
 * @create: 2020-06-17-18:35
 */
public class EngthOfLongestSubstring {
    public static  int lengthOfLongestSubstring(String s) {
        char [] chars = s.toCharArray();
        int maxLength = 0;
        for (int a=0; a<chars.length; a++) {
            String tempStr = "";
            for (int b=a; b<chars.length; b++) {
                if(tempStr.indexOf(chars[b]) > -1) {
                    break;
                }
                tempStr += chars[b];
                if(tempStr.length() > maxLength) {
                    maxLength = tempStr.length();
                }
            }
        }
        return maxLength;
    }

    public static void main(String[] args) {
        String str= "abcabcbb";
        System.out.println(lengthOfLongestSubstring(str));
    }
}
