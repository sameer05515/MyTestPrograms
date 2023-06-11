package com.p.printtable;

import java.util.Scanner;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class PrintTable {

	public static void main(String[] args) {
		System.out.println("Welcome to table generation program");
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the table of which number?");
		int tableofwhichnumber = scanner.nextInt();
		System.out.println("Enter the till which multiple?");
		int myInt = scanner.nextInt();
		scanner.close();
		
		System.out.println("Your table of "+tableofwhichnumber +" till multiple "+myInt+" is - \n"+multiple(tableofwhichnumber,myInt));

	}

	private static String multiple(int tableofwhichnumber, int myInt) {
		StringBuffer sb=new StringBuffer();
		
		
		/**
		 * Commenting iteration of collection using old syntax
		 * */
		
//		for(int i=1;i<=myInt;i++){
//			sb.append((i*tableofwhichnumber)+"\n");
//		}
		
		/**
		 * Using new syntax for iteration using Consumer , and forEach method
		 * */
		IntConsumer tableRowConsumer=i->{
			sb.append((i*tableofwhichnumber)+"\n");
		};
		IntStream.rangeClosed(1, myInt)
		.forEach(tableRowConsumer);
		
		return sb.toString();
	}

}
