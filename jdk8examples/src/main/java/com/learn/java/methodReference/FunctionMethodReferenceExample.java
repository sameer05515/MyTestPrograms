package com.learn.java.methodReference;

import java.util.function.Function;

public class FunctionMethodReferenceExample {
	
	static Function<String, String> toUpperCaseLambda= (s) -> s.toUpperCase();
	
	static Function<String, String> toUpperCaseMethodReference= String::toUpperCase;

	public static void main(String[] args) {


		System.out.println(toUpperCaseLambda.apply("Premendra"));
		System.out.println(toUpperCaseMethodReference.apply("java8"));
	}

}
