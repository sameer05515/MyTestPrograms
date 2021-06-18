package com.learn.java.streams_terminal;

import java.util.stream.Collectors;

import com.learn.java.data.StudentDatabase;

public class StreamsCountingExample {

	public static long count() {
		return StudentDatabase.getAllDatabase()
				.stream()
				.filter(student->student.getGpa()>=3.1)
				.collect(Collectors.counting());
	}
	
	public static void main(String[] args) {

		System.out.println(count());

	}

}
