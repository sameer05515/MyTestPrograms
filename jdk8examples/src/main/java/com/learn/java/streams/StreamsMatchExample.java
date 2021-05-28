package com.learn.java.streams;

import com.learn.java.data.StudentDatabase;

public class StreamsMatchExample {

	public static boolean allMatch() {
		return StudentDatabase.getAllDatabase()
				.stream()
				.allMatch(student -> student.getGpa()>=3.0);
	}
	
	public static boolean anyMatch() {
		return StudentDatabase.getAllDatabase()
				.stream()
				.anyMatch(student -> student.getGpa()>=4.0);
	}
	
	public static boolean noneMatch() {
		return StudentDatabase.getAllDatabase()
				.stream()
				.noneMatch(student -> student.getGpa()>=4.1);
	}
	
	public static void main(String[] args) {
		
		System.out.println("Result of all match : "+ allMatch());
		System.out.println("Result of any match : "+ anyMatch());
		System.out.println("Result of none match : "+ noneMatch());
	}

}
