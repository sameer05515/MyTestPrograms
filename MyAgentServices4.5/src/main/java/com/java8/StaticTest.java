package com.java8;

public class StaticTest extends StaticClass implements Static{
    public static void main(String[] args) {
        StaticTest staticTest=new StaticTest();
        Static.printNames();
        printMyNames();
        staticTest.printDefault();
    }
}

abstract class StaticClass{
    public static void printMyNames(){
        System.out.println("Premendra Kumar Abstract Class");
    }
}

interface Static{
    static void printNames(){
        System.out.println("Premendra Kumar Interface");
    }

    default void printDefault(){
        System.out.println("I am from default");
    }
}
