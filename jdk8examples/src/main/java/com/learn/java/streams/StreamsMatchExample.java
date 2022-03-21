package com.learn.java.streams;

import com.learn.java.data.StudentDatabase;

public class StreamsMatchExample {


    public static boolean allMatch(){

        boolean result = StudentDatabase.getAllStudents().stream()
                .allMatch(student -> student.getGpa()>=3.9);

        return result;
    }

    public static boolean anyMatch(){

        boolean result = StudentDatabase.getAllStudents().stream()
                .anyMatch(student -> student.getGpa()>=3.9);

        return result;
    }

    public static boolean noneMatch(){

        boolean result = StudentDatabase.getAllStudents().stream()
                .noneMatch(student -> student.getGpa()>=3.9);

        return result;
    }

    public static void main(String[] args) {

        System.out.println("Result of allMatch : " + allMatch());
        System.out.println("Result of anyMatch : " + anyMatch());
        System.out.println("Result of noneMatch : " + noneMatch());
    }
}
