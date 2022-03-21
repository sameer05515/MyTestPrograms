package com.learn.java.functionalInterfaces;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import com.learn.java.data.Student;
import com.learn.java.data.StudentDatabase;

public class SupplierExample {

    public static  Supplier<Student> studentSupplier = () -> {
      return  new Student("Adam",2,4.0,"male", Arrays.asList("swimming", "basketball","volleyball"));
    };

    public static  Supplier<List<Student>> studentsSupplier = () -> StudentDatabase.getAllStudents();

    public static void main(String[] args) {

        Student student = studentSupplier.get();

        System.out.println("Student is : " + student);

        System.out.println("Students are : " + studentsSupplier.get());
    }
}
