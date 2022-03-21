package com.learn.java.streams_terminal;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.minBy;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toSet;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.learn.java.data.Student;
import com.learn.java.data.StudentDatabase;

public class StreamsGroupingByExample {

    public static void groupingByGender(){

        Map<String,List<Student>> studentMap =  StudentDatabase.getAllStudents()
                .stream()
                .collect(Collectors.groupingBy(Student::getGender));

        Stream.of(studentMap).forEach(System.out::println);
    }

    public  static void customizedGroupingBy(){

        Map<String,List<Student>> studentMap =  StudentDatabase.getAllStudents()
                .stream()
                .collect(Collectors.groupingBy(student -> student.getGpa()>= 3.8 ?  "OUTSTANDING" : "AVERAGE"));

        Stream.of(studentMap).forEach(System.out::println);
    }

    /**
     * Grouping by Two parameters
     */
    public  static void twoLevelGrouping(){

        Map<Integer,  Map<String,List<Student>>> studentMap =  StudentDatabase.getAllStudents().stream()
                .collect(groupingBy(Student::getGradeLevel,
                        groupingBy(student -> student.getGpa()>= 3.8 ?  "OUTSTANDING" : "AVERAGE")));

        Stream.of(studentMap).forEach(System.out::println);

    }

    /**
     * Grouping by Two parameters
     */
    public  static void twoLevelGrouping_2(){

        Map<String,Integer> nameNoteBooksMap = StudentDatabase.getAllStudents().stream()
                .collect(groupingBy(Student::getName,
                        summingInt(Student::getNoteBooks)));// second argument can be of any type of collector

        System.out.println(nameNoteBooksMap);
    }

    /**
     * Grouping by Two parameters
     */
    public  static void twoLevelGrouping_3(){

        Map<String,Set<Student>> nameNoteBooksMap = StudentDatabase.getAllStudents().stream()
                .collect(groupingBy(Student::getName,
                        toSet()));// second argument can be of any type of collector

        System.out.println(nameNoteBooksMap);
    }


    public static void threeArgumentGroupingBy(){

        LinkedHashMap<String,Set<Student>> studentMap = StudentDatabase.getAllStudents().stream()
                .collect(groupingBy(Student::getName,LinkedHashMap::new,
                        toSet()));

        System.out.println(" studentMap : " + studentMap);
    }


    public  static void calculteTopGpaStudentinEachGrade(){

        Map<Integer, Optional<Student>> studentMapOptional =  StudentDatabase.getAllStudents().stream()
                .collect(groupingBy(Student::getGradeLevel,maxBy(Comparator.comparingDouble(Student::getGpa))
                ));

        Stream.of(studentMapOptional).forEach(System.out::println);


        Map<Integer, Student> studentMap =  StudentDatabase.getAllStudents().stream()
                .collect(groupingBy(Student::getGradeLevel,
                        collectingAndThen(maxBy(Comparator.comparingDouble(Student::getGpa))
                        ,Optional::get
                )));

        Stream.of(studentMap).forEach(System.out::println);
    }

    public  static void calculteleastGpaStudentinEachGrade(){

        Map<Integer, Optional<Student>> studentMapOptional =  StudentDatabase.getAllStudents().stream()
                .collect(groupingBy(Student::getGradeLevel,minBy(Comparator.comparingDouble(Student::getGpa))
                ));

        Stream.of(studentMapOptional).forEach(System.out::println);


        Map<Integer, Student> studentMap =  StudentDatabase.getAllStudents().stream()
                .collect(groupingBy(Student::getGradeLevel,
                        collectingAndThen(minBy(Comparator.comparingDouble(Student::getGpa))
                                ,Optional::get
                        )));

        Stream.of(studentMap).forEach(System.out::println);
    }

    public static void main(String[] args) {

        //groupingByGender();
        //groupByGrade();
        twoLevelGrouping();
        //twoLevelGrouping_2();
        //twoLevelGrouping_3();
        //calculteTopGpaStudentinEachGrade();
        //calculteleastGpaStudentinEachGrade();
       // threeArgumentGroupingBy();
    }
}
