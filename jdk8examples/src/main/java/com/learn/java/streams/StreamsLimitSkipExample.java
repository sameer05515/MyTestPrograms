package com.learn.java.streams;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StreamsLimitSkipExample {

	public static Optional<Integer> limit(List<Integer> integers) {

		return integers.stream().limit(3).reduce((x, y) -> x + y);
	}
	
	public static Optional<Integer> skip(List<Integer> integers) {

		return integers.stream().skip(4).reduce((x, y) -> x + y);
	}

	public static void main(String[] args) {
		List<Integer> integers = Arrays.asList(5, 6, 8, 9, 1, 11, 15, 7);
		Optional<Integer> limitResult=limit(integers);
		if(limitResult.isPresent()) {
			System.out.println("The limit result is : "+limitResult.get());
		}else {
			System.out.println("No input passed");
		}
		
		Optional<Integer> skipResult=skip(integers);
		if(skipResult.isPresent()) {
			System.out.println("The skip result is : "+skipResult.get());
		}else {
			System.out.println("No input passed");
		}

	}

}
