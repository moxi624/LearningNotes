package com.moxi.interview.study.annotation;

import java.lang.annotation.*;
import java.lang.reflect.Field;

/**
 * ORMDemo
 *
 * @author: 陌溪
 * @create: 2020-03-29-15:33
 */
@TableKuang("db_student")
class Student2 {
    @FieldKuang(columnName = "db_id", type="int", length = 10)
    private int id;

    @FieldKuang(columnName = "db_age", type="int", length = 10)
    private int age;

    @FieldKuang(columnName = "db_name", type="varchar", length = 10)
    private String name;

    public Student2() {
    }

    public Student2(int id, int age, String name) {
        this.id = id;
        this.age = age;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student2{" +
                "id=" + id +
                ", age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}

/**
 * 自定义注解：类名的注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface TableKuang {
    String value();
}

/**
 * 自定义注解：属性的注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface FieldKuang {
    String columnName();
    String type();
    int length() default 0;
}
public class ORMDemo {

    public static void main(String[] args) throws Exception{
        // 获取Student 的 Class对象
        Class c1 = Class.forName("com.moxi.interview.study.annotation.Student2");

        // 通过反射，获取到全部注解
        Annotation [] annotations = c1.getAnnotations();

        for (Annotation annotation : annotations) {
            System.out.println(annotation);
        }

        // 获取注解的value值
        TableKuang tableKuang = (TableKuang)c1.getAnnotation(TableKuang.class);
        String value = tableKuang.value();
        System.out.println(value);

        // 获得类指定的注解
        Field f = c1.getDeclaredField("name");
        FieldKuang fieldKuang = f.getAnnotation(FieldKuang.class);
        System.out.println(fieldKuang.columnName());
        System.out.println(fieldKuang.type());
        System.out.println(fieldKuang.length());
    }
}
