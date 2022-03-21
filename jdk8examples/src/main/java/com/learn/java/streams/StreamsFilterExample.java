package com.learn.java.streams;

import static java.util.stream.Collectors.toList;

import java.util.List;

import com.learn.java.data.Student;
import com.learn.java.data.StudentDatabase;

public class StreamsFilterExample {

    public static List<Student> filterStudents(){

        List<Student> filteredStudentList = StudentDatabase.getAllStudents()
                .stream()
                .filter(student -> student.getGpa()>=3.9)
                .filter(student -> student.getGender().equals("female"))
                .collect(toList());

        return filteredStudentList;
    }

    public static void main(String[] args) {

        System.out.println("Filtered Students : " + filterStudents());

    }
}
