package com.learn.java.streams_terminal;

import java.util.stream.Collectors;

import com.learn.java.data.Student;
import com.learn.java.data.StudentDatabase;

public class StreamsSumAvgExample {

	public static int sum() {
		return StudentDatabase.getAllDatabase()
		.stream()
		.collect(Collectors.summingInt(Student::getNotebooks));
	}
	
	public static double average() {
		return StudentDatabase.getAllDatabase()
		.stream()
		.collect(Collectors.averagingInt(Student::getNotebooks));
	}
	public static void main(String[] args) {
		
		System.out.println("Total no of notebooks : "+sum());
		System.out.println("Average no of notebooks : "+average());
		
	}

}
