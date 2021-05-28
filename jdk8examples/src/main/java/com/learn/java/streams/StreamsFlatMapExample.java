package com.learn.java.streams;

import java.util.List;
import java.util.stream.Collectors;

import com.learn.java.data.Student;
import com.learn.java.data.StudentDatabase;

public class StreamsFlatMapExample {

	public static List<String> printStudentActivities() {
		return StudentDatabase.getAllDatabase().stream() // Stream<Student
				.map(Student::getActivities) // Stream<List<String>>
				.flatMap(List::stream) // Stream<String>
				.distinct() // Stream<String> -> with distinct function performed
				.sorted()
				.collect(Collectors.toList());
	}
	
	public static long getStudentActivitiesCount() {
		return StudentDatabase.getAllDatabase().stream() // Stream<Student
				.map(Student::getActivities) // Stream<List<String>>
				.flatMap(List::stream) // Stream<String>
				.distinct() // Stream<String> -> with distinct function performed
				.count();
	}

	public static void main(String[] args) {
		System.out.println("printStudentActivities : " + printStudentActivities());
		System.out.println("getStudentActivitiesCount : "+getStudentActivitiesCount());
	}

}
