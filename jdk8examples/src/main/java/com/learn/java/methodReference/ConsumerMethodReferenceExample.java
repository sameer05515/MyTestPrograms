package com.learn.java.methodReference;

import java.util.function.Consumer;

import com.learn.java.data.Student;
import com.learn.java.data.StudentDatabase;

public class ConsumerMethodReferenceExample {

	static Consumer<Student> c1=System.out::println;
	
	static Consumer<Student> c2=Student::printListOfActivities;
	
	public static void main(String[] args) {
		
		StudentDatabase.getAllDatabase().forEach(c1);
		StudentDatabase.getAllDatabase().forEach(c2);
	}

}
