package com.moxi.interview.study.basic;

/**
 * 代码块
 * 普通代码块：在方法或语句中出现的{}，就被称为代码块
 * 普通代码块和一般语句执行顺序由他们在代码中出现的次序决定，先出现先执行
 *
 * @author: 陌溪
 * @create: 2020-04-03-9:51
 */
public class CodeBlock02 {
    {
        System.out.println("第二构造块33333");
    }

    public  CodeBlock02() {
        System.out.println("构造方法2222");
    }

    {
        System.out.println("第一构造块33333");
    }

    public static void main(String[] args) {
        new CodeBlock02();
        System.out.println("==========");
        new CodeBlock02();
    }
}
