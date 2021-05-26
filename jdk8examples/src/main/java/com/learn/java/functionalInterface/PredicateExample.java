package com.learn.java.functionalInterface;

import java.util.function.Predicate;

public class PredicateExample {

	private static Predicate<Integer> p=(i)-> {return i%2==0;};
	private static Predicate<Integer> p1=(i)-> i%2==0;
	private static Predicate<Integer> p2=(i)-> i%5==0;
	
	public static void predicateAnd() {
		System.out.printf("Predicate And result is : %s \n",p1.and(p2).test(10));
		System.out.printf("Predicate And result is : %s \n",p1.and(p2).test(19));
	}
	
	public static void predicateOr() {
		System.out.printf("Predicate Or result is : %s \n",p1.or(p2).test(10));
		System.out.printf("Predicate Or result is : %s \n",p1.or(p2).test(19));
	}
	
	public static void predicateNegate() {
		System.out.printf("Predicate Or result is : %s \n",p1.or(p2).negate().test(10));
		
	}
	
	public static void main(String[] args) {
		System.out.println(p.test(4));
		System.out.println(p1.test(4));
		
		predicateAnd();
		predicateOr();
		predicateNegate();
	}

}
