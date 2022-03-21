package com.learn.java.streams_terminal;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.learn.java.data.Student;
import com.learn.java.data.StudentDatabase;

public class StreamMappingExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> namesList = StudentDatabase.getAllDatabase().stream()
				.collect(Collectors.mapping(Student::getName, Collectors.toList()));
		System.out.println("namesList : " + namesList);
		
		Set<String> namesSet = StudentDatabase.getAllDatabase().stream()
				.collect(Collectors.mapping(Student::getName, Collectors.toSet()));
		System.out.println("namesList : " + namesList);
		System.out.println("namesList : " + namesSet);
	}

}
