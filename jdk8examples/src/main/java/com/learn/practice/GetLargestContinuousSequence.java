package com.learn.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GetLargestContinuousSequence {

	public static void main(String[] args) {
//		List<Integer> list = Arrays.stream(arr)        // IntStream
//                .boxed()          // Stream<Integer>
//                .collect(Collectors.toList());

		int[] given = { 2, 8, 3, 2, 4, 10, 6 };
		int[] finalRes = largestContSeq(given);

	}

	public static int[] largestContSeqJava8Way(int[] given) {

		// List<Integer> givenList=new ArrayList<Integer>();
//		for(int i:given) {
//			givenList.add(i);
//		}
		List<Integer> givenList = Arrays.stream(given) // IntStream
				.boxed() // Stream<Integer>
				.collect(Collectors.toList());
		
		List<Integer> finalRes = new ArrayList<Integer>();

		int sum = 0;

		System.out.println("Given List : " + givenList);
		
//		IntStream.rangeClosed(0,givenList.size())
//		.stre

		for (int i = 2; i <= givenList.size(); i++) {

			for (int j = 0; j < givenList.size() - i; j++) {
				List<Integer> temp = givenList.subList(j, j + i);
				int tempSum = temp.stream().reduce(0, (a, b) -> a + b);

				System.out.println("Previous Largest Sum : " + sum + " tempSum : " + tempSum + " temp : " + temp);

				if (tempSum > sum) {
					finalRes = temp;
					sum = tempSum;
				}

			}

		}

		System.out.println("Largest Sum : " + sum + " finalRes : " + finalRes);
		return finalRes.stream().mapToInt(i -> i).toArray();

	}

	public static int[] largestContSeq(int[] given) {

		List<Integer> givenList = new ArrayList<Integer>();
		for (int i : given) {
			givenList.add(i);
		}
		List<Integer> finalRes = new ArrayList<Integer>();

		int sum = 0;

		System.out.println("Given List : " + givenList);

		for (int i = 2; i <= givenList.size(); i++) {

			for (int j = 0; j < givenList.size() - i; j++) {
				List<Integer> temp = givenList.subList(j, j + i);
				int tempSum = temp.stream().reduce(0, (a, b) -> a + b);

				System.out.println("Previous Largest Sum : " + sum + " tempSum : " + tempSum + " temp : " + temp);

				if (tempSum > sum) {
					finalRes = temp;
					sum = tempSum;
				}

			}

		}

		System.out.println("Largest Sum : " + sum + " finalRes : " + finalRes);
		return finalRes.stream().mapToInt(i -> i).toArray();

	}

}
