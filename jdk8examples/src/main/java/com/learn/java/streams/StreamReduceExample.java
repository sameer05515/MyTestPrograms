package com.learn.java.streams;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.learn.java.data.Student;
import com.learn.java.data.StudentDatabase;

public class StreamReduceExample {

    public static int performMultiplication(List<Integer> integerList){

        return integerList.stream()
                .reduce(1, (a,b) -> a*b);

    }

    public static Optional<Integer> performMultiplicationWithNoInitialValue(List<Integer> integerList){

        return integerList.stream()
                .reduce( (a,b) -> a*b); // performs multiplication for each element in the stream and returns a new result fo.
    }

    public static String combineStudentNames(){

        return StudentDatabase.getAllStudents().stream()
                .map(Student::getName)
                .distinct()
                .reduce("",(a,b) -> a.concat(b));  // performs multiplication for each element in the stream.
    }

    public static Optional<Student> getHighestGradeStudent(){

        Optional<Student> studentOptional =  StudentDatabase.getAllStudents().stream()
                .reduce((s1,s2)->(s1.getGpa()>s2.getGpa()) ? s1 : s2);
        return studentOptional;
    }




    public static void main(String[] args) {

        List<Integer> integerList = Arrays.asList(1,3,5,7);
        //List<Integer> integerList = Arrays.asList();

        System.out.println("Result is : " + performMultiplication(integerList));
        Optional<Integer> result = performMultiplicationWithNoInitialValue(integerList);

        if(result.isPresent()){
            System.out.println("Result is : " +result.get());
        }else{
            System.out.println("Result is : " +0);
        }

        System.out.println(combineStudentNames());
        System.out.println(getHighestGradeStudent().get());
    }
}
