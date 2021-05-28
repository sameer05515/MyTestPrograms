package com.learn.java.numericStreams;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class NumericStreamRangesExample {

	public static void main(String[] args) {
		
		IntStream intStream=IntStream.range(1, 100);
		System.out.println("Int Range Count"+intStream.count());
		
		IntStream.range(1, 100).forEach(value->System.out.print(value+","));
		
		System.out.println("\nInt Range Closed Count"+IntStream.rangeClosed(1, 100).count());
		IntStream.rangeClosed(1, 100).forEach(value->System.out.print(value+","));
		
		System.out.println("\nLong Range Closed Count"+LongStream.rangeClosed(1, 100).count());
		LongStream.rangeClosed(1, 100).forEach(value->System.out.print(value+","));

		System.out.println();
		IntStream.range(1, 100).asDoubleStream().forEach(value->System.out.print(value+","));
	}

}
