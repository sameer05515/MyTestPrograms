package com.learn.java.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.learn.java.data.Student;
import com.learn.java.data.StudentDatabase;

public class StreamReduceExample {

	public static int performMultiplication(List<Integer> integerList) {
		return integerList.stream().reduce(1, (a, b) -> a * b);
	}

	public static Optional<Integer> performMultiplicationWithoutIdentity(List<Integer> integerList) {
		return integerList.stream().reduce((a, b) -> a * b);
	}

	public static Optional<Student> getHighestGPAStudent() {

//		return StudentDatabase.getAllDatabase().stream()
//		.reduce((s1,s2)->{
//			if(s1.getGpa()>s2.getGpa()) {
//				return s1;
//			}else {
//				return s2;
//			}
//		});

		return StudentDatabase.getAllDatabase().stream().reduce((s1, s2) -> {
			return s1.getGpa() > s2.getGpa() ? s1 : s2;
		});
	}

	public static void main(String[] args) {

		System.out.println(performMultiplication(Arrays.asList(1, 3, 5, 7, 9, 11)));

		Optional<Integer> result = performMultiplicationWithoutIdentity(Arrays.asList(1, 3, 5, 10));

		System.out.println(result.isPresent());
		System.out.println(result.get());

		Optional<Integer> result1 = performMultiplicationWithoutIdentity(new ArrayList<Integer>());

		System.out.println(result1.isPresent());
		if (result1.isPresent()) {
			System.out.println(result1.get());
		}

		Optional<Student> studentOptional = getHighestGPAStudent();
		if(studentOptional.isPresent()) {
			System.out.println(studentOptional.get());
		}

	}

}
