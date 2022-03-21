package com.learn.java.methodReference;

import java.util.function.Predicate;

import com.learn.java.data.Student;
import com.learn.java.data.StudentDatabase;

public class RefactorMethodReferenceExample {

	static Predicate<Student> p1=RefactorMethodReferenceExample::greaterThanGradeLevel;
	
	private static boolean greaterThanGradeLevel(Student s) {
		return s.getGradeLevel()>=3;
	}
	public static void main(String[] args) {
		
		System.out.println(p1.test(StudentDatabase.studentSupplier.get()));

	}

}
