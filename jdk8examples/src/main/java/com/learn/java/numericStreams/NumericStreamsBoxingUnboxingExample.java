package com.learn.java.numericStreams;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NumericStreamsBoxingUnboxingExample {

	public static List<Integer> boxing() {
		return IntStream.rangeClosed(1, 5)
		.boxed()
		.collect(Collectors.toList());
	}
	
	public static int unBoxing(List<Integer> integers) {
		return integers.stream().mapToInt(Integer::intValue).sum();
	}
	
	public static void main(String[] args) {
		
		System.out.println("Boxing : "+boxing());
		System.out.println("Boxing : "+unBoxing(boxing()));

	}

}
