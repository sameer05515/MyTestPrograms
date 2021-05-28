package com.learn.java.numericStreams;

import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class NumericStreamAggregateExample {

	public static void main(String[] args) {

		int sum=IntStream.rangeClosed(1,20).sum();
		System.out.println("Sum is : "+sum);
		OptionalInt max=IntStream.rangeClosed(1,20).max();
		System.out.println("Max value is : "+(max.isPresent()?max.getAsInt():0));
		
		OptionalLong ol=LongStream.rangeClosed(20,30).min();
		System.out.println("Min value is : "+(ol.isPresent()?ol.getAsLong():0));
		
		OptionalDouble avg=IntStream.rangeClosed(20,23).average();
		System.out.println("Average value is : "+(avg.isPresent()?avg.getAsDouble():0));

	}

}
