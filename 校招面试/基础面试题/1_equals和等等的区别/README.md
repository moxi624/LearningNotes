# 等等与equals的区别

## 前言

我们都知道， == 是比较内存地址，equals是比较值

但是这种非常错误的一种观点

- ==：比较基本数据类型的时候，比较的是值，引用数据类型比较的是地址（new的对象，==比较永远是false）
- equals：属于Object类的方法，如果我们没有重写过equals方法，那么它就是 ==，但是字符串里面的equals被重写过了，比较的是值

## 代码一

```
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
    }
}
```

最后结果

```
false（==：如果是new出来的对象，比较的时候永远是false）
true：（字符串中的equals被重写过，比较的是值）
1：（HashSet底层是HashMap，HashMap内部是调用equals 和 HashCode，但是String内部的HashCode和equals也被复写）
==============
true（我们通过这种方式创建的会放在一个字符串常量池中，相同的字符串，会指向常量池中同一个对象，因此他们的地址是一样的）
true（字符串中的equals被重写过，比较的是值）
1
==============
false（==：如果是new出来的对象，比较的时候永远是false）
false（Person中的equals没有被重写，相当于等等）
2
```



## 代码二

```
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
```

运行结果：

```
str1 == str2：false
str1.equals(str2)：true
str1 == str5：false
str1 == str6：false
str5 == str6：false
str5.equals(str6)：false
str1 == str6.intern()：false
str1 == str2.intern()：true
```

下面解释关于 intern方法

```
一句话，intern方法就是从常量池中获取对象
返回字符串对象的规范表示形式
字符串池最初为空，由类字符串私下维护
调用intern方法时，如果池中已包含由equals(Object)方法确定的与此String对象相等的字符串，则返回池中的字符串
否者，此字符串添加到池中，并返回对此字符串对象的引用
因此，对于任意两个字符串s和t，s.intern() == t.intern() 在且仅当 s.equals(t) 为 true时候,所有文字字符串和字符串值常量表达式都会被插入
```

