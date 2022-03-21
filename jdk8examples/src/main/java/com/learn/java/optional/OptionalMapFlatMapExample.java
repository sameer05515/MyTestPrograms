package com.learn.java.optional;

import java.util.Optional;

import com.learn.java.data.Bike;
import com.learn.java.data.Student;
import com.learn.java.data.StudentDatabase;

public class OptionalMapFlatMapExample {

    public static void optionalFlatMap(){
      Optional<Student> studentOptional =  StudentDatabase.getOptionalStudent();
      if(studentOptional.isPresent()){
          Optional<Bike> bikeOptional= studentOptional.
                  flatMap(Student::getBike); //
          System.out.println("bikeOptional : " + bikeOptional);
      }
    }

    public static void optionalMap(){
        Optional<Student> studentOptional =  StudentDatabase.getOptionalStudent();

        if(studentOptional.isPresent()){
            Optional<String> nameOptional= studentOptional.
                    map(Student::getName); //
            System.out.println("nameOptional : " + nameOptional);
        }
    }

    public static void optionalFilter(){

        Optional<Student> studentOptional = StudentDatabase.getOptionalStudent()
                .filter(student -> student.getGpa()>=4.3);

        studentOptional.ifPresent(student -> System.out.println(student));

    }

    public static void main(String[] args) {
        optionalFlatMap();
        optionalMap();
        optionalFilter();
    }
}
