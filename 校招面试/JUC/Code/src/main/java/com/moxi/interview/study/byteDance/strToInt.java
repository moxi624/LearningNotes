package com.moxi.interview.study.byteDance;

/**
 * @author: 陌溪
 * @create: 2020-06-24-16:49
 */
public class strToInt {
    public static int StrToInt(String str) {
        if(str == null || str.length() ==0) {
            return 0;
        }
        // 判断是否是负数
        boolean start = str.charAt(0) == '-';
        int ret = 0;
        for(int a=0; a<str.length(); a++) {
            char ch = str.charAt(a);
            if(a == 0 && (ch == '+' || ch == '-')) {
                continue;
            }
            if(ch < '0' || ch > '9') {
                return 0;
            }
            ret = ret * 10 + (ch - '0');
        }
        return ret;
    }

    public static void main(String[] args) {
        System.out.println(StrToInt("123"));
    }
}
