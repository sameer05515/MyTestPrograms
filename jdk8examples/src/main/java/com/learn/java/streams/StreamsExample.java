package com.learn.java.streams;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.learn.java.data.Student;
import com.learn.java.data.StudentDatabase;

public class StreamsExample {

	public static void main(String[] args) {

		Map<String, List<String>> studentMap = StudentDatabase.getAllDatabase().stream()
				.collect(Collectors.toMap(Student::getName, Student::getActivities));

		Predicate<Student> studentPredicate = (student) -> student.getGradeLevel() >= 3;
		Predicate<Student> studentGPAPredicate = (student) -> student.getGpa() >= 3.9;
		Map<String, List<String>> studentMap1 = StudentDatabase.getAllDatabase().stream()
				.peek((student->{
					System.out.println(student);
				}))
				.filter(studentPredicate)
				.peek((student->{
					System.out.println("============\n after 1st filter \n"+student);
				}))
				.filter(studentGPAPredicate)
				.peek((student->{
					System.out.println("============\n after 2nd filter \n"+student);
				}))
				.collect(Collectors.toMap(Student::getName, Student::getActivities));

		Map<String, List<String>> studentMap2 = StudentDatabase.getAllDatabase().parallelStream()
				.filter(studentPredicate).filter(studentGPAPredicate).filter((student) -> student.getGradeLevel() >= 5)
				.collect(Collectors.toMap(Student::getName, Student::getActivities));

		System.out.println(studentMap);

		System.out.println(studentMap1);

		System.out.println(studentMap2);

	}

}
