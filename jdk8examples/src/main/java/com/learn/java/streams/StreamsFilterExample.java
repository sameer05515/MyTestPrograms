package com.learn.java.streams;

import java.util.List;
import java.util.stream.Collectors;

import com.learn.java.data.Student;
import com.learn.java.data.StudentDatabase;

public class StreamsFilterExample {

	public static List<Student> filterStudents() {
		return StudentDatabase.getAllDatabase().stream().filter((student -> student.getGender().equals("female")))
				.filter(student -> student.getGpa() >= 3.9).collect(Collectors.toList());
	}

	public static void main(String[] args) {
		filterStudents().forEach(System.out::println);
	}

}
