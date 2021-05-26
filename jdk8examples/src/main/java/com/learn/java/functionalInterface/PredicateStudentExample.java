package com.learn.java.functionalInterface;

import java.util.List;
import java.util.function.Predicate;

import com.learn.java.data.Student;
import com.learn.java.data.StudentDatabase;

public class PredicateStudentExample {

	static Predicate<Student> p1 = (student) -> student.getGradeLevel() >= 3;
	static Predicate<Student> p2 = (student) -> student.getGpa() >= 3.9;

	public static void main(String[] args) {

		filterStudentByGradeLevel();
		filterStudentByGpa();
		filterStudents(); 

	}

	private static void filterStudentByGradeLevel() {
		System.out.println("filterStudentByGradeLevel");
		List<Student> studentList = StudentDatabase.getAllDatabase();
		studentList.forEach((student -> {
			if (p1.test(student)) {
				System.out.println(student);
			}
		}));

	}
	
	private static void filterStudentByGpa() {
		System.out.println("filterStudentByGpa");
		List<Student> studentList = StudentDatabase.getAllDatabase();
		studentList.forEach((student -> {
			if (p2.test(student)) {
				System.out.println(student);
			}
		}));

	}
	
	private static void filterStudents() {
		System.out.println("filterStudents");
		List<Student> studentList = StudentDatabase.getAllDatabase();
		studentList.forEach((student -> {
			if (p1.and(p2).test(student)) {
				System.out.println(student);
			}
		}));

	}	

}
