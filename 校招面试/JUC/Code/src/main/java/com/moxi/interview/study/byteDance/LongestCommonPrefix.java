package com.moxi.interview.study.byteDance;

/**
 * 最长公共前缀
 *
 * 编写一个函数来查找字符串数组中的最长公共前缀。
 * 如果不存在公共前缀，返回空字符串 ""。
 *
 * 示例 1:
 *
 * 输入: ["flower","flow","flight"]
 * 输出: "fl"
 * 示例 2:
 *
 * 输入: ["dog","racecar","car"]
 * 输出: ""
 * 解释: 输入不存在公共前缀。
 * 说明:
 *
 * 所有输入只包含小写字母 a-z 。
 * @author: 陌溪
 * @create: 2020-06-17-18:45
 */
public class LongestCommonPrefix {
    public static String longestCommonPrefix(String[] strs) {
        if(strs.length == 0) {
            return "";
        }
        if(strs.length == 1) {
            return strs[0];
        }
        char [] pred = strs[0].toCharArray();
        if(pred.length == 0) {
            return "";
        }
        int count = 0;
        while(true) {
            int successCount = 0;
            for(int a=0; a<strs.length; a++) {
                char [] str = strs[a].toCharArray();
                if(str.length == count) {
                    break;
                }
                if(str[count] == pred[count]) {
                    successCount++;
                }
            }
            if(successCount == strs.length) {
                count ++;
                continue;
            } else {
                if(count != 0) {
                    String result = "";
                    for(int a=0;a<count;a++) {
                        result += pred[a];
                    }
                    return result;
                } else {
                    return "";
                }
            }
        }
    }

    public static void main(String[] args) {
        String [] strs = new String[]{"c","c"};
        System.out.println(longestCommonPrefix(strs));
    }
}
