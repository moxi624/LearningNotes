package com.moxi.interview.study.basic;

/**
 * 代码块
 * 普通代码块：在方法或语句中出现的{}，就被称为代码块
 * 普通代码块和一般语句执行顺序由他们在代码中出现的次序决定，先出现先执行
 *
 * @author: 陌溪
 * @create: 2020-04-03-9:51
 */
public class CodeBlock {
    public static void main(String[] args) {
        {
            int x = 11;
            System.out.println("普通代码块中的变量X=" + x);
        }

        {
            int y = 13;
            System.out.println("普通代码块中的变量y=" + y);
        }

        int x = 12;
        System.out.println("主方法中的变量x=" + x);
    }
}
