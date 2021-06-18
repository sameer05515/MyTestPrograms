package com.learn.java.streams_terminal;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

import com.learn.java.data.Student;
import com.learn.java.data.StudentDatabase;

public class StreamsMinByMaxByExample {

	public static Optional<Student> minBy() {
		return StudentDatabase.getAllDatabase().stream()
				.collect(Collectors.minBy(Comparator.comparing(Student::getGpa)));
	}
	
	public static Optional<Student> maxBy() {
		return StudentDatabase.getAllDatabase().stream()
				.collect(Collectors.maxBy(Comparator.comparing(Student::getGpa)));
	}

	public static void main(String[] args) {
		System.out.println(minBy());
		System.out.println(maxBy());
	}

}
