package com.learn.java.lambdas;

import java.util.Comparator;

public class ComparatorLambdaExample {

	public static void main(String[] args) {
		/**
		 * prior java 8
		 * */
		Comparator<Integer> comparator=new Comparator<Integer>() {
			
			@Override
			public int compare(Integer o1, Integer o2) {				
				return o1.compareTo(o2);
			}
		};
		
		System.out.println("Result of the comparator is : "+comparator.compare(21, 2));
		
		Comparator<Integer> comparatorLambda1=(Integer o1,Integer o2)->{return o1.compareTo(o2);};

		System.out.println("Result of the comparator Lambda 1 is : "+comparatorLambda1.compare(21, 2));
		
		Comparator<Integer> comparatorLambda2=(o1,o2)->{return o1.compareTo(o2);};
		
		System.out.println("Result of the comparator Lambda 2 is : "+comparatorLambda2.compare(21, 2));

	}

}
