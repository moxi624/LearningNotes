package com.moxi.interview.study.java8.lambda;

/**
 * 员工类
 *
 * @author: 陌溪
 * @create: 2020-04-05-12:13
 */
public class Employee {
    private String name;
    private int age;
    private double salary;

    public Employee() {
    }

    public Employee(int age) {
        this.age = age;
    }

    public Employee(String name, int age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                '}';
    }
}