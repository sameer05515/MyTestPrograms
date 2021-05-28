package com.learn.java.streams;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.learn.java.data.Student;
import com.learn.java.data.StudentDatabase;

public class StreamsComparatorExample {

	public static List<Student> sortStudentsByName(){
		return StudentDatabase.getAllDatabase()
		.stream()
		.sorted(Comparator.comparing(Student::getName))
		.collect(Collectors.toList());
	}
	
	public static List<Student> sortStudentsByGpa(){
		return StudentDatabase.getAllDatabase()
		.stream()
		.sorted(Comparator.comparing(Student::getGpa))
		.collect(Collectors.toList());
	}
	
	public static List<Student> sortStudentsByGpaDesc(){
		return StudentDatabase.getAllDatabase()
		.stream()
		.sorted(Comparator.comparing(Student::getGpa).reversed())
		.collect(Collectors.toList());
	}
	
	public static void main(String[] args) {
		System.out.println("Students sorted by name : ");
		sortStudentsByName().forEach(System.out::println);
		
		System.out.println("Students sorted by gpa : ");
		sortStudentsByGpa().forEach(System.out::println);
		
		System.out.println("Students sorted by gpa in descending order : ");
		sortStudentsByGpaDesc().forEach(System.out::println);
	}

}
