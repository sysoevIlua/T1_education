package org.sysoev.task2.entity;

public class Employee {
    private String name;
    private int age;
    private String post;

    public Employee(String name, int age, String post) {
        this.name = name;
        this.age = age;
        this.post = post;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getPost() {
        return post;
    }
}
