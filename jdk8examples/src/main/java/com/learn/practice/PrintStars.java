package com.learn.practice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class PrintStars {

	public static void main(String[] args) {
		printStars(4);
	}

	private static void printStars(int givenInt) {
		List<Integer> list=new ArrayList<>();
		
		IntStream.rangeClosed(1, givenInt-1).forEach(j->{
			list.add(j);
		});
		
		list.add(givenInt);
		
		IntStream.rangeClosed(1, givenInt-1).forEach(j->{
			list.add(givenInt-j);
		});
		
		list.forEach(j->{
			IntStream.rangeClosed(1, j)
			.forEach(k->{
				System.out.print("  *");
			});
			System.out.println();
		});
		
	}
}
