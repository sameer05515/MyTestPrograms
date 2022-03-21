package com.learn.java.streams;

import static java.util.stream.Collectors.toList;

import java.util.Comparator;
import java.util.List;

import com.learn.java.data.Student;
import com.learn.java.data.StudentDatabase;

public class StreamsComparatorExample {

    public static List<Student> sortStudentsByName(){

       return  StudentDatabase.getAllStudents().stream()
                .sorted(Comparator.comparing(Student::getName))
                .collect(toList());
    }

    public static List<Student> sortStudentsByGpa(){

        return  StudentDatabase.getAllStudents().stream()
                .sorted(Comparator.comparing(Student::getGpa))
                .collect(toList());
    }

    public static List<Student> sortStudentsByGpaReversed(){

        return  StudentDatabase.getAllStudents().stream()
                .sorted(Comparator.comparing(Student::getGpa).reversed())
                .collect(toList());
    }

    public static void main(String[] args) {
        System.out.println("Students sorted by NAME");
        sortStudentsByName().forEach(System.out::println);
        System.out.println("Students sorted by GPA");
         sortStudentsByGpa().forEach(System.out::println);

        System.out.println("Students sorted by GPA Higher to Lower");
        sortStudentsByGpaReversed().forEach(System.out::println);

    }
}
