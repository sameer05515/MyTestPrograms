package com.learn.java.streams_terminal;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.learn.java.data.Student;
import com.learn.java.data.StudentDatabase;

public class StreamsGroupingByExample {

	public static void groupStudentByGender() {
		Map<String, List<Student>> studentMap = StudentDatabase.getAllDatabase().stream()
				.collect(Collectors.groupingBy(Student::getGender));
		System.out.println(studentMap);
	}

	public static void customizedGroupingStudentByGender() {
		Map<String, List<Student>> studentMap = StudentDatabase.getAllDatabase().stream()
				.collect(Collectors.groupingBy(student -> student.getGpa() >= 3.9 ? "outstanding" : "average"));
		System.out.println(studentMap);
	}

	public static void twoLevelGrouping_1() {
		Map<Integer, Map<Object, List<Student>>> studentMap = StudentDatabase.getAllDatabase().stream()
				.collect(Collectors.groupingBy(Student::getGradeLevel,
						Collectors.groupingBy(student -> student.getGpa() >= 3.9 ? "outstanding" : "average")));
		System.out.println(studentMap);
	}

	public static void twoLevelGrouping_2() {
		Map<Integer, Integer> studentMap = StudentDatabase.getAllDatabase().stream()
				.collect(Collectors.groupingBy(Student::getGradeLevel, Collectors.summingInt(Student::getNotebooks)));
		System.out.println(studentMap);
	}

	public static void twoLevelGrouping_3() {
		Map<String, Integer> studentMap = StudentDatabase.getAllDatabase().stream()
				.collect(Collectors.groupingBy(Student::getName, Collectors.summingInt(Student::getNotebooks)));
		System.out.println(studentMap);
	}

	public static void threeArgumentGroupBy() {
		LinkedHashMap<String, Set<Student>> studentMap = StudentDatabase.getAllDatabase().stream()
				.collect(Collectors.groupingBy(Student::getName, LinkedHashMap::new, Collectors.toSet()));
		System.out.println(studentMap);
	}

	public static void calculateTopGpa() {
		Map<Integer, Student> studentMapOptional = StudentDatabase.getAllDatabase().stream()
				.collect(Collectors.groupingBy(Student::getGradeLevel, Collectors
						.collectingAndThen(Collectors.maxBy(Comparator.comparing(Student::getGpa)), Optional::get)));
		System.out.println(studentMapOptional);

		Map<Integer, Optional<Student>> studentMapOptional1 = StudentDatabase.getAllDatabase().stream().collect(
				Collectors.groupingBy(Student::getGradeLevel, Collectors.maxBy(Comparator.comparing(Student::getGpa))));
		System.out.println(studentMapOptional1);
	}
	
	public static void calculateLeastGpa() {
		Map<Integer, Student> studentMapOptional = StudentDatabase.getAllDatabase().stream()
				.collect(Collectors.groupingBy(Student::getGradeLevel, Collectors
						.collectingAndThen(Collectors.minBy(Comparator.comparing(Student::getGpa)), Optional::get)));
		System.out.println(studentMapOptional);

		Map<Integer, Optional<Student>> studentMapOptional1 = StudentDatabase.getAllDatabase().stream().collect(
				Collectors.groupingBy(Student::getGradeLevel, Collectors.minBy(Comparator.comparing(Student::getGpa))));
		System.out.println(studentMapOptional1);
	}

	public static void main(String[] args) {

//		groupStudentByGender();
//		customizedGroupingStudentByGender();
//		twoLevelGrouping_1();
//		twoLevelGrouping_2();
//		twoLevelGrouping_3();
//		threeArgumentGroupBy();
//		calculateTopGpa();
		calculateLeastGpa();

	}

}
