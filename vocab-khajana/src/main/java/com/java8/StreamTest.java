package com.java8;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StreamTest {
    public static void main(String[] args) {
        List<String> stringList= Arrays.asList("Ram","Shyam");
        stringList.stream().forEach(s->System.out.println(s));
        System.out.println(stringList.stream().count());

        stringList.stream().filter(s -> s.startsWith("R"))
                .peek(s -> System.out.println("Filtered value: " + s))
                .map(String::toUpperCase)
                .peek(s -> System.out.println("Uppercase value :" + s))
                .count();
        List<Student> studentList=new ArrayList<>();
        for(int i=1;i<=100;i++){
            studentList.add(new Student(i,"name_"+i,"address_"+i));
        }

        studentList.stream().forEach(student -> {
            System.out.println(student);
        });

        Map<Integer,Student> studentMap=studentList.stream().collect(Collectors.toMap(x->x.getId(),x->x));

        System.out.println(studentMap);
        List<String> studentNamesList=studentList.stream().map(student -> student.getName()).collect(Collectors.toList());
        System.out.println(studentNamesList);

        Function<Student,String> getStudentName=student -> {return student.getName();};

        Set<String> studentNamesSet=studentList.stream().map(getStudentName).collect(Collectors.toSet());
        System.out.println(studentNamesSet);
    }
}
