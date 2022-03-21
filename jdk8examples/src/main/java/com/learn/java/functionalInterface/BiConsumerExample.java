package com.learn.java.functionalInterface;

import java.util.List;
import java.util.function.BiConsumer;

import com.learn.java.data.Student;
import com.learn.java.data.StudentDatabase;

public class BiConsumerExample {

	public static void nameAndActivities() {

		BiConsumer<String, List<String>> biconsumer = (name, activities) -> {
			System.out.println(name + " : " + activities);
		};
		List<Student> studentList = StudentDatabase.getAllDatabase();
		studentList.forEach((student -> biconsumer.accept(student.getName(), student.getActivities())));
	}

	public static void main(String[] args) {

		BiConsumer<String, String> biconsumer = (a, b) -> {
			System.out.println("a : " + a + " b : " + b);
		};
		biconsumer.accept("Premendra", "Kumar");

		BiConsumer<Integer, Integer> multiply = (a, b) -> {
			System.out.printf(" Multiplication of {%s} and {%s} is :- %s \n", a, b, a * b);
		};

		BiConsumer<Integer, Integer> division = (a, b) -> {
			System.out.printf(" Division of {%s} and {%s} is :- %s \n", a, b, a / b);
		};

		multiply.andThen(division).accept(10, 5);

		nameAndActivities();

	}

}
