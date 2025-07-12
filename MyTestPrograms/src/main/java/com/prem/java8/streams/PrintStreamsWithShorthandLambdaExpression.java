package com.prem.java8.streams;// Java code to print the elements of Stream

import java.util.stream.*;

class PrintStreamsWithShorthandLambdaExpression {
	public static void main(String[] args)
	{

		// Get the stream
		Stream<String> stream = Stream.of("Geeks", "For",
										"Geeks", "A",
										"Computer", "Portal");

		// Print the stream
		stream.forEach(System.out::println);
	}
}
