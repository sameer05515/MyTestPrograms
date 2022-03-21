package com.learn.java.streams;

import java.util.Optional;

import com.learn.java.data.Student;
import com.learn.java.data.StudentDatabase;

public class StreamsFindAnyFirstExample {

    public static Optional<Student> findAny(){

        return StudentDatabase.getAllStudents().stream()
                .filter(student -> student.getGpa()>=3.8)
                .findAny();
    }

    public static Optional<Student> findFirst(){

        return StudentDatabase.getAllStudents().stream()
                .filter(student -> student.getGpa()>=3.8)
                .findFirst();
    }

    public static void main(String[] args) {

        Optional<Student> findAnyStudent = findAny();
        if(findAnyStudent.isPresent()){
            System.out.println("Student is :" + findAnyStudent.get());
        }else{
            System.out.println("No Student Found");
        }

        Optional<Student> findFirst = findFirst();
        if(findFirst.isPresent()){
            System.out.println("Student is :" + findFirst.get());
        }else{
            System.out.println("No Student Found");
        }
        //it will make a lot of different during parallel stream

    }
}
