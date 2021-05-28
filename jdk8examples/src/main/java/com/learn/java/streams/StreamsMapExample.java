package com.learn.java.streams;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.learn.java.data.Student;
import com.learn.java.data.StudentDatabase;

public class StreamsMapExample {

	public static List<String> namesList() {
		return StudentDatabase.getAllDatabase().stream().map(Student::getName).map(String::toUpperCase)
				.collect(Collectors.toList());
	}
	
	public static Set<String> namesSet() {
		return StudentDatabase.getAllDatabase().stream().map(Student::getName).map(String::toUpperCase)
				.collect(Collectors.toSet());
	}

	public static void main(String[] args) {
		System.out.println(namesList());
		System.out.println(namesSet());
	}

}
