package com.learn.java.functionalInterfaces;

import java.util.List;
import java.util.function.Consumer;

import com.learn.java.data.Student;
import com.learn.java.data.StudentDatabase;

public class ConsumerExample {

    static Consumer<Student>  c1= p -> System.out.println(p);

    static Consumer<Student>  c2= p -> System.out.print(p.getName().toUpperCase());

    static Consumer<Student>  c3= p -> System.out.println(p.getActivities());


    public static void printName(){

        List<Student> personList = StudentDatabase.getAllStudents();

        personList.forEach(c1);

    }

    public static void printNameAndActivities(){
        System.out.println("printNameAndActivities : ");
        List<Student> personList = StudentDatabase.getAllStudents();
        personList.forEach(c2.andThen(c3));
    }

    public static void printNameAndActivitiesUsingCondition(){
        System.out.println("printNameAndActivitiesUsingCondition : ");
        List<Student> personList = StudentDatabase.getAllStudents();
        personList.forEach((s) -> {
            if( s.getGradeLevel()>=3 && s.getGpa()>3.9){
                c2.andThen(c3).accept(s);
            }
        });
    }

    public static void main(String[] args) {

        Consumer<String> c1 = (s) -> System.out.println(s.toUpperCase());

        c1.accept("java8");

        printName();
        printNameAndActivities();
        printNameAndActivitiesUsingCondition();





    }
}
