package com.learn.java.functionalInterfaces;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

import com.learn.java.data.Student;
import com.learn.java.data.StudentDatabase;

public class BiPredicateExample {

    static BiPredicate<Integer,Double> biPredicate = (gradeLevel, gpa) ->  gradeLevel>=3 && gpa >=3.9 ;

    static Consumer<Student> consumer = (student) -> {
            if(biPredicate.test(student.getGradeLevel(),student.getGpa())){
                System.out.println(student);
            }
    };

    public static  void filterStudents(){

        List<Student> studentList = StudentDatabase.getAllStudents();

        studentList.forEach(consumer);
    }

    public static void main(String[] args) {
        filterStudents();
    }

}
