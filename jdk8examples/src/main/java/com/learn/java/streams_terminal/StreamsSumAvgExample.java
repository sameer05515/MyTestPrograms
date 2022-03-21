package com.learn.java.streams_terminal;

import java.util.stream.Collectors;

import com.learn.java.data.Student;
import com.learn.java.data.StudentDatabase;

public class StreamsSumAvgExample {

    public static int sum(){

        int totalNoOfNotebooks = StudentDatabase.getAllStudents()
                .stream()
                .collect(Collectors.summingInt(Student::getNoteBooks));

        return totalNoOfNotebooks;
    }

    public static double average(){

        double totalNoOfNotebooks = StudentDatabase.getAllStudents()
                .stream()
                .collect(Collectors.averagingInt(Student::getNoteBooks));

        return totalNoOfNotebooks;
    }


    public static void main(String[] args) {


        System.out.println("Total no of Notebooks : " + sum());

        System.out.println("Average no of Notebooks : " + average());
    }
}
