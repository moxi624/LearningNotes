package com.moxi.interview.study.annotation;

/**
 * 反射Demo
 *
 * @author: 陌溪
 * @create: 2020-03-29-8:21
 */
public class ReflectionDemo {
    public static void main(String[] args) throws ClassNotFoundException {
        // 通过反射获取类的Class对象
        Class c1 = Class.forName("com.moxi.interview.study.annotation.User");
        Class c2 = Class.forName("com.moxi.interview.study.annotation.User");
        Class c3 = Class.forName("com.moxi.interview.study.annotation.User");
        System.out.println(c1.hashCode());
        System.out.println(c2.hashCode());
        System.out.println(c3.hashCode());


    }
}

/**
 * 实体类：pojo，entity
 */
class User {
    private String name;
    private int id;
    private int age;

    public User() {

    }

    public User(String name, int id, int age) {
        this.name = name;
        this.id = id;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", age=" + age +
                '}';
    }
}