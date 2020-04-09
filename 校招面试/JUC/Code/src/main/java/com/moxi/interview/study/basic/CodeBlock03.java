package com.moxi.interview.study.basic;

/**
 * 代码块
 *
 * @author: 陌溪
 * @create: 2020-04-03-9:51
 */

/**
 * 随从类
 */
class Code {
    public Code() {
        System.out.println("Code的构造方法1111");
    }

    {
        System.out.println("Code的构造代码块22222");
    }

    static {
        System.out.println("Code的静态代码块33333");
    }
}
public class CodeBlock03 {

    {
        System.out.println("CodeBlock03的构造代码块22222");
    }

    static {
        System.out.println("CodeBlock03的静态代码块33333");
    }

    public CodeBlock03() {
        System.out.println("CodeBlock03的构造方法33333");
    }

    public static void main(String[] args) {

        System.out.println("我是主类======");
        new Code();
        System.out.println("======");
        new Code();
        System.out.println("======");
        new CodeBlock03();
    }
}
