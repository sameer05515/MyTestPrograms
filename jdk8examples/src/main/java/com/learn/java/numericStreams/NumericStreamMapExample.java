package com.learn.java.numericStreams;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NumericStreamMapExample {

	public static List<Integer> mapToObj() {
		return IntStream.rangeClosed(1,30)
		.mapToObj(i-> new Integer(i))
		.collect(Collectors.toList());
	}
	
	public static long mapToLong() {
		return IntStream.rangeClosed(1,30)
				.mapToLong(i->i)
				.sum();
	}
	
	public static double mapToDouble() {
		return IntStream.rangeClosed(1,30)
				.mapToDouble(i->i)
				.sum();
	}
	
	public static void main(String[] args) {
	
		System.out.println("mapToObj : "+mapToObj());
		System.out.println("mapToLong : "+mapToLong());
		System.out.println("mapToDouble : "+mapToDouble());
	}
}
