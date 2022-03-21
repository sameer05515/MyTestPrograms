package com.learn.java.streams;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StreamsMinMaxExample {

	public static int findMaxValue(List<Integer> ontegerList) {
		return ontegerList.stream().reduce(0, (x, y) -> x > y ? x : y);
	}

	public static Optional<Integer> findMaxValueOptional(List<Integer> ontegerList) {
		return ontegerList.stream().reduce((x, y) -> x > y ? x : y);
	}

	public static int findMinValue(List<Integer> ontegerList) {
		return ontegerList.stream().reduce(Integer.MAX_VALUE, (x, y) -> x < y ? x : y);
	}

	public static Optional<Integer> findMinValueOptional(List<Integer> ontegerList) {
		return ontegerList.stream().reduce((x, y) -> x < y ? x : y);
	}

	public static void main(String[] args) {

		System.out.println("max value is : " + findMaxValue(Arrays.asList(5, 6, 7, 8)));

//		Optional<Integer> maxValueOptional=findMaxValueOptional(Arrays.asList(1,2,3,4,5,8));
		Optional<Integer> maxValueOptional = findMaxValueOptional(Arrays.asList());

		if (maxValueOptional.isPresent()) {
			System.out.println("Max value using Optional : " + maxValueOptional.get());
		} else {
			System.out.println("Optional max is empty");
		}

		System.out.println("min value is : " + findMinValue(Arrays.asList(5, 6, 7, 8)));
//		Optional<Integer> minValueOptional = findMinValueOptional(Arrays.asList());
		Optional<Integer> minValueOptional = findMinValueOptional(Arrays.asList(1, 2, 3, 4, 5, 8));

		if (minValueOptional.isPresent()) {
			System.out.println("Min value using Optional : " + minValueOptional.get());
		} else {
			System.out.println("Optional min is empty");
		}
	}

}
