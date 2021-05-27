package com.learn.java.data;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class StudentDatabase {

	public static Supplier<Student> studentSupplier=()->{
		return new Student("Ram", 2, 3.6, "male", Arrays.asList("swimming", "basketbal", "volleyball"));
	};
	/**
	 * Total of 6 students in the database
	 * 
	 * @return
	 */
	public static List<Student> getAllDatabase() {
		// 2nd grade students
		Student s1 = new Student("Ram", 2, 3.6, "male", Arrays.asList("swimming", "basketbal", "volleyball"));
		Student s2 = new Student("Sita", 2, 3.7, "female", Arrays.asList("knitting", "basketbal", "volleyball"));

		// 2nd grade students
		Student s3 = new Student("Shyam", 3, 3.6, "male", Arrays.asList("swimming", "basketbal", "volleyball"));
		Student s4 = new Student("Radha", 3, 3.7, "female", Arrays.asList("knitting", "basketbal", "volleyball"));

		// 2nd grade students
		Student s5 = new Student("Shiv", 4, 3.8, "male", Arrays.asList("swimming", "basketbal", "volleyball"));
		Student s6 = new Student("Shakti", 4, 4.0, "female", Arrays.asList("knitting", "basketbal", "volleyball"));
		List<Student> students = Arrays.asList(s1, s2,s3,s4,s5,s6);
		return students;
	}
}
