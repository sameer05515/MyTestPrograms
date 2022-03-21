package com.learn.practice;

import java.util.StringJoiner;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class PrintTable {

	public static void print(int tableofwhichnumber, int noOfIteration) {
		StringBuffer sb = new StringBuffer();

		IntConsumer tableRowConsumer = i -> {
			sb.append((i * tableofwhichnumber) + "\n");
		};

		IntStream.rangeClosed(1, noOfIteration).forEach(tableRowConsumer);

		System.out.println(sb.toString());
	}

	public static void printMulti(int tableStartofwhichnumber, int tableEndofwhichnumber, int noOfIteration) {

		// StringJoiner printAllLine= new StringJoiner("\n");;		
		IntStream.rangeClosed(1, noOfIteration).forEach(iterCount -> {
			StringJoiner printOneLine = new StringJoiner("\t", "\n\t", "");
			IntStream.rangeClosed(tableStartofwhichnumber, tableEndofwhichnumber).forEach(currTableNumber -> {
				printOneLine.add((currTableNumber * iterCount) + "");
			});
			// printAllLine.add(printOneLine.toString());
			System.out.print(printOneLine.toString());
		});
		// System.out.println(printAllLine.toString());
	}

	public static void printMultiJdk7Approach(int tableStartofwhichnumber, int tableEndofwhichnumber,
			int noOfIteration) {
		for (int iterCount = 1; iterCount <= noOfIteration; iterCount++) {
			System.out.println();
			for (int currTableNumber = tableStartofwhichnumber; currTableNumber <= tableEndofwhichnumber; currTableNumber++) {
				System.out.print("\t" + (currTableNumber * iterCount));
			}
		}
	}

	public static void main(String[] args) {

//		print(15, 20);
		printMulti(15, 20, 10);
		System.out.println();
		printMultiJdk7Approach(15, 20, 10);
	}

}
