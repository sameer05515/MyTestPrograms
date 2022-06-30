package com.test.spring.examples.pojo;

public class Student {


    public Student(String name){
        this.name=name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void displayInfo() {
        System.out.println("Hello: " + name);
    }
}  