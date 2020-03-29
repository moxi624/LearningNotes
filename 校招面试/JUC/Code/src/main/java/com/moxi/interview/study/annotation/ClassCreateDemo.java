package com.moxi.interview.study.annotation;

/**
 * Class类创建的方式
 *
 * @author: 陌溪
 * @create: 2020-03-29-9:56
 */
class Person {
    public String name;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }
}

class Student extends Person{
    public Student() {
        this.name = "学生";
    }
}

class Teacher extends Person {
    public Teacher() {
        this.name = "老师";
    }
}


public class ClassCreateDemo {
    public static void main(String[] args) throws ClassNotFoundException {

        Person person = new Student();
        System.out.println("这个人是：" + person.name);

        // 方式1：通过对象获得
        Class c1 = person.getClass();
        System.out.println("c1:" + c1.hashCode());

        //方式2：通过forName获得
        Class c2 = Class.forName("com.moxi.interview.study.annotation.Student");
        System.out.println("c2:" + c2.hashCode());

        // 方式3：通过类名获取（最为高效）
        Class c3 = Student.class;
        System.out.println("c3:" + c3.hashCode());

        // 方式4：基本内置类型的包装类，都有一个Type属性
        Class c4 = Integer.TYPE;
        System.out.println(c4.getName());

        // 方式5：获取父类类型
        Class c5 = c1.getSuperclass();
    }
}
