package com.learn.java.functionalInterface;

import java.util.Comparator;
import java.util.function.BinaryOperator;

public class BinaryOperatorExample {

	private static Comparator<Integer> comparator = (o1, o2) -> o1.compareTo(o2);

	public static void main(String[] args) {

		BinaryOperator<Integer> binaryOperator = (a, b) -> a * b;

		System.out.println(binaryOperator.apply(10, 12));

		BinaryOperator<Integer> maxBy = BinaryOperator.maxBy(comparator);
		System.out.printf("\nResult of MaxBy is : " + maxBy.apply(44, 55));

		BinaryOperator<Integer> minBy = BinaryOperator.minBy(comparator);
		System.out.printf("\nResult of MinBy is : " + minBy.apply(44, 55));
	}

}
