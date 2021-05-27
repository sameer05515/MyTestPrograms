package com.learn.java.functionalInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.learn.java.data.Student;
import com.learn.java.data.StudentDatabase;

public class FunctionStudentExample {

	static Function<List<Student>, Map<String, Double>> studentFunction = (students -> {
		Map<String, Double> studentGradeMap = new HashMap<>();
		students.forEach((student -> {
			if (PredicateStudentExample.p1.test(student)) {
				studentGradeMap.put(student.getName(), student.getGpa());
			}
		}));
		return studentGradeMap;
	});

	public static void main(String[] args) {

		List<Student> studentList = StudentDatabase.getAllDatabase();
		System.out.println(studentFunction.apply(studentList));

	}

}
