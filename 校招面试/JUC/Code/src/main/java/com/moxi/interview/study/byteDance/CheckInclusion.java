package com.moxi.interview.study.byteDance;

import java.util.HashSet;
import java.util.Set;

/**
 * 字符串的排列
 *
 * 给定两个字符串 s1 和 s2，写一个函数来判断 s2 是否包含 s1 的排列。
 *
 * 换句话说，第一个字符串的排列之一是第二个字符串的子串。
 *
 * 示例1:
 *
 * 输入: s1 = "ab" s2 = "eidbaooo"
 * 输出: True
 * 解释: s2 包含 s1 的排列之一 ("ba").
 *
 *
 * 示例2:
 *
 * 输入: s1= "ab" s2 = "eidboaoo"
 * 输出: False
 *
 *
 * 注意：
 *
 * 输入的字符串只包含小写字母
 * 两个字符串的长度都在 [1, 10,000] 之间
 *
 * @author: 陌溪
 * @create: 2020-06-17-19:10
 */
public class CheckInclusion {

    public static Set<String> RollList(String str, int i){
        Set<String> set = new HashSet<>();
        if (i>=(str.length()-1)) {
            set.add(str);
            return set;
        }
        char[] charArray = str.toCharArray();
        String now = "";
        //将第i个元素开始,与后面所有元素都换一次,就能得到所有组合
        //例如,abc,i=0,表示,第一个元素从0开始换,得到abc,bac,cba的组合,然后再去算bc,ac,ba的所有组合
        for (int k=i; k<str.length(); k++){
            char temp = charArray[i];
            charArray[i] = charArray[k];
            charArray[k] = temp;
            //交换完后,处理后续,处理完后,再将两数还原位置,使得每次都是以abc的顺序进行交换
            now = String.valueOf(charArray);
            set.addAll(RollList(now,(i+1)));
            temp = charArray[i];
            charArray[i] = charArray[k];
            charArray[k] = temp;
        }
        return set;
    }

    public static boolean checkInclusion(String s1, String s2) {
        Set<String> set = RollList(s1, 0);
        for (String str : set) {
         if(s2.indexOf(str) > -1) {
             return true;
         }
        }
        return false;
    }


    public static void main(String[] args) {
//        System.out.println(checkInclusion("ab", "eidbaooo"));
        Set<String> set = RollList("abc", 0);
        for (String str: set){
            System.out.println(str);
        }
    }
}
