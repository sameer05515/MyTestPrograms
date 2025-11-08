package com.prem.java8.streams;// Java code to print the elements of Stream

import java.util.stream.*;

class PrintStreamsWithFilterAndPeek {
	public static void main(String[] args) {

		// Get the stream
		Stream<String> stream = Stream.of("Geeks", "For",
				"GeeksForGeeks", "A",
				"Computer", "Portal");

		// Using peek() to inspect stream elements during processing
		// peek() is an intermediate operation that doesn't consume the stream
		// .count() is used as a terminal operation to trigger stream processing
		// This is useful for debugging/monitoring stream transformations

		// Print the stream elements as they flow through the pipeline
		long count = stream.filter(s -> s.startsWith("G"))
				.peek(s -> System.out.println("Filtered value: " + s))
				.map(String::toUpperCase)
				.peek(s -> System.out.println("Uppercase value :" + s))
				.count();

		// Print the total count of elements that passed the filter
		System.out.println("Total elements matching filter: " + count);
	}
}
