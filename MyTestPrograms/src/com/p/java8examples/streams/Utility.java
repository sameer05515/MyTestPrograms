package com.p.java8examples.streams;

import java.util.List;
import java.util.stream.Stream;

public class Utility {

//	public static void printStringStreams(String[] arr) {
//		// Get the stream
//		Stream<String> stream = Stream.of(arr);
//		// Print the stream
//		stream.forEach(s -> System.out.println(s));
//	}

	public static <T> void printStreams(T[] arr) {
		// Get the stream
		Stream<T> stream = Stream.of(arr);
		// Print the stream
		stream.forEach(s -> System.out.println(s));
	}

	public static Employee[] toEmployeeArrayUsingStream(List<Employee> list) {
		return list.stream().toArray(Employee[]::new);
	}

}
