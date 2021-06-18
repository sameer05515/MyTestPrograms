package com.learn.java.streams_terminal;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.learn.java.data.Student;
import com.learn.java.data.StudentDatabase;

public class StreamsPartitioningByExample {
	
	public static void partiotioningBy_1() {
		Predicate<Student> gpaPredicate=student-> student.getGpa()>=3.8;
		Map<Boolean, List<Student>> patiotioningMap=
				StudentDatabase.getAllDatabase()
				.stream()
				.collect(Collectors.partitioningBy(gpaPredicate));
		System.out.println(patiotioningMap);
	}
	
	public static void partiotioningBy_2() {
		Predicate<Student> gpaPredicate=student-> student.getGpa()>=3.8;
		Map<Boolean, Set<Student>> patiotioningMap=
				StudentDatabase.getAllDatabase()
				.stream()
				.collect(Collectors.partitioningBy(gpaPredicate,Collectors.toSet()));
		System.out.println(patiotioningMap);
	}

	public static void main(String[] args) {

//		partiotioningBy_1();
		partiotioningBy_2();

	}

}
