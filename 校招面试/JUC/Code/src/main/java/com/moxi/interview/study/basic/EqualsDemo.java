package com.moxi.interview.study.basic;

import java.util.HashSet;
import java.util.Set;

/**
 * equals和等等的区别
 *
 * @author: 陌溪
 * @create: 2020-04-03-8:50
 */
public class EqualsDemo {

    static class Person {
        private String name;

        public Person(String name) {
            this.name = name;
        }
    }
    public static void main(String[] args) {
        String s1 = new String("abc");
        String s2 = new String("abc");

        System.out.println(s1 == s2);
        System.out.println(s1.equals(s2));
        Set<String> set1 = new HashSet<>();
        set1.add(s1);
        set1.add(s2);
        System.out.println(set1.size());

        System.out.println("==============");

        String s3 = "cbd";
        String s4 = "cbd";

        System.out.println(s3 == s4);
        System.out.println(s3.equals(s4));
        Set<String> set3 = new HashSet<>();
        set3.add(s3);
        set3.add(s3);
        System.out.println(set3.size());

        System.out.println("==============");

        Person person1 = new Person("abc");
        Person person2 = new Person("abc");
        System.out.println(person1 == person2);
        System.out.println(person1.equals(person2));
        Set<Person> set2 = new HashSet<>();
        set2.add(person1);
        set2.add(person2);
        System.out.println(set2.size());

        System.out.println("===================");

        /**
         * 返回字符串对象的规范表示形式
         * 字符串池最初为空，由类字符串私下维护
         * 调用intern方法时，如果池中已包含由equals(Object)方法确定的与此String对象相等的字符串，则返回池中的字符串
         * 否者，此字符串添加到池中，并返回对此字符串对象的引用
         * 因此，对于任意两个字符串s和t，s.intern() == t.intern() 在且仅当 s.equals(t) 为 true时候.
         * 所有文字字符串和字符串值常量表达式都会被插入，String文字在Java语言规范的3.3.5节中定义
         *
         */
        String str1 = "abc";
        String str2 = new String("abc");
        String str3 = "abc";
        String str4 =  "xxx";
        String str5 = "abc" + "xxx";
        String str6 = s3 + s4;

        System.out.println("str1 == str2：" + (str1 == str2));
        System.out.println("str1.equals(str2)：" + (str1.equals(str2)));
        System.out.println("str1 == str5：" + (str1 == str5));
        System.out.println("str1 == str6：" + (str1 == str6));
        System.out.println("str5 == str6：" + (str5 == str6));
        System.out.println("str5.equals(str6)：" + (str5.equals(str6)));
        System.out.println("str1 == str6.intern()：" + (str1 == str6.intern()));
        System.out.println("str1 == str2.intern()：" + (str1 == str2.intern()));
    }
}
