package com.learn.java.functionalInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import com.learn.java.data.Student;
import com.learn.java.data.StudentDatabase;

public class BiFunctionExample {

	static BiFunction<List<Student>, Predicate<Student>, Map<String, Double>> biFunction = ((students,
			studentPredicate) -> {
		Map<String, Double> studentGradeMap = new HashMap<>();
		students.forEach((student -> {
			if (studentPredicate.test(student)) {
				studentGradeMap.put(student.getName(), student.getGpa());
			}
		}));
		return studentGradeMap;
	});

	public static void main(String[] args) {

		List<Student> studentList = StudentDatabase.getAllDatabase();
		System.out.println(biFunction.apply(studentList, PredicateStudentExample.p1));
		
		System.out.println(biFunction.apply(studentList, PredicateStudentExample.p2));
		
		System.out.println(biFunction.apply(studentList, PredicateStudentExample.p1.and(PredicateStudentExample.p2)));
	}

}
