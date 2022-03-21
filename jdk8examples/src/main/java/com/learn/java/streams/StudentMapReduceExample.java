package com.learn.java.streams;

import com.learn.java.data.Student;
import com.learn.java.data.StudentDatabase;

public class StudentMapReduceExample {

	private static int noOfNotebooks() {
		return StudentDatabase.getAllDatabase().stream().filter((student -> student.getGradeLevel() >= 3))
				.map(Student::getNoteBooks)
//				.reduce(0, (a, b) -> a + b);
				.reduce(0, Integer::sum);
	}

	public static void main(String[] args) {
		System.out.println("Number of notebooks : " + noOfNotebooks());
	}

}
