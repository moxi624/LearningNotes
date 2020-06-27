package com.moxi.interview.study.LeetCode;

/**
 * 替换空格
 *
 * @author: 陌溪
 * @create: 2020-06-26-16:13
 */
public class ReplaceSpace_05 {
    public String replaceSpace(String s) {
        char [] ch = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(int a=0; a<ch.length; a++) {
            if(ch[a] == ' ') {
                sb.append("%20");
            } else {
                sb.append(ch[a]);
            }
        }
        return sb.toString();
    }

    public String replaceSpace(StringBuffer str) {
        int len1 = str.length() - 1;
        for(int i = 0; i <= len1; i++){
            if(str.charAt(i) == ' '){
                // 遇到空格，在后面添加两个空格
                str.append("  ");
            }
        }

        int len2 = str.length() - 1;
        while(len2 > len1 && len1 >= 0){
            char c = str.charAt(len1--);
            if(c == ' '){
                str.setCharAt(len2--, '0');
                str.setCharAt(len2--, '2');
                str.setCharAt(len2--, '%');
            }else{
                str.setCharAt(len2--, c);
            }
        }
        return str.toString();
    }
}
