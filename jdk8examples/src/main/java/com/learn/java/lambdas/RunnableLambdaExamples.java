package com.learn.java.lambdas;

public class RunnableLambdaExamples {

	public static void main(String[] args) {
		/**
		 * prior java 8
		 */

		Runnable runnable = new Runnable() {
			public void run() {
				System.out.println("Inside Runnable 1");
			}
		};

		new Thread(runnable).start();

		/**
		 * java 8 lambda
		 */

		// () -> {};

		Runnable runnableLambda1 = () -> {
			System.out.println("Inside Runnable 2");
		};

		Runnable runnableLambda2 = () -> System.out.println("Inside Runnable 3");

		new Thread(runnableLambda1).start();
		new Thread(runnableLambda2).start();

	}

}
