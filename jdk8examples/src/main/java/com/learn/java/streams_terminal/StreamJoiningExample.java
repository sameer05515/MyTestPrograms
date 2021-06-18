package com.learn.java.streams_terminal;

import static java.util.stream.Collectors.joining;

import com.learn.java.data.Student;
import com.learn.java.data.StudentDatabase;

public class StreamJoiningExample {

	public static String joining_1() {
		return StudentDatabase.getAllDatabase().stream().map(Student::getName).collect(joining());
	}

	public static String joining_2() {
		return StudentDatabase.getAllDatabase().stream().map(Student::getName).collect(joining("-"));
	}

	public static String joining_3() {
		return StudentDatabase.getAllDatabase().stream().map(Student::getName).collect(joining("-", "(", ")"));
	}

	public static void main(String[] args) {

		System.out.println("joining_1" + joining_1());
		System.out.println("joining_2" + joining_2());
		System.out.println("joining_3" + joining_3());
	}
}
