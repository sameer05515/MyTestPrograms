package com.learn.java.streams;

import java.util.Optional;

import com.learn.java.data.Student;
import com.learn.java.data.StudentDatabase;

public class StreamsFindAnyFirstExample {

	public static Optional<Student> findAnyStudent() {
		return StudentDatabase.getAllDatabase().stream().findAny();
	}

	public static Optional<Student> findFirstStudent() {
		return StudentDatabase.getAllDatabase().stream().findFirst();
	}

	public static void main(String[] args) {

		Optional<Student> studentOptionalFindAny = findAnyStudent();
		if (studentOptionalFindAny.isPresent())
			System.out.println("Found the Student : " + studentOptionalFindAny.get());
		else
			System.out.println("Student not Found");

		Optional<Student> studentOptionalFindFirst = findFirstStudent();
		if (studentOptionalFindFirst.isPresent())
			System.out.println("Found the Student : " + studentOptionalFindFirst.get());
		else
			System.out.println("Student not Found");

	}

}
