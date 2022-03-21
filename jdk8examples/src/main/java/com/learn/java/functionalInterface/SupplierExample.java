package com.learn.java.functionalInterface;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import com.learn.java.data.Student;
import com.learn.java.data.StudentDatabase;

public class SupplierExample {

	public static void main(String[] args) {
		
		Supplier<Student> studentSupplier = ()-> {
			return new Student("Ram", 2, 3.6, "male", Arrays.asList("swimming", "basketbal", "volleyball"));
		};
		
		Supplier<List<Student>> listSupplier = () -> StudentDatabase.getAllDatabase();
		
		System.out.println(studentSupplier.get());
		
		System.out.println(listSupplier
				.get());

	}

}
