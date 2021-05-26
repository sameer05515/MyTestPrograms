package com.learn.java.functionalInterface;

import java.util.List;
import java.util.function.Consumer;

import com.learn.java.data.Student;
import com.learn.java.data.StudentDatabase;

public class ConsumerExample {

	private static Consumer<Student> c3=(student)-> System.out.print(student.getName()+"\t");
	private static Consumer<Student> c4=(student)-> System.out.println(student.getActivities());
	
	public static void printName() {
		Consumer<Student> c2=(student)-> System.out.println(student);
		List<Student> studentList=StudentDatabase.getAllDatabase();
		studentList.forEach(c2);
	}
	
	public static void printNameAndActivities() {
		
		System.out.println("printNameAndActivities");
		List<Student> studentList=StudentDatabase.getAllDatabase();
		studentList.forEach(c3.andThen(c4));//consumer chaining
		
	}
	
	public static void printNameAndActivitiesUsingCondition() {
		
		System.out.println("printNameAndActivitiesUsingCondition");
		List<Student> studentList=StudentDatabase.getAllDatabase();
		studentList.forEach((student->{
			if(student.getGradeLevel()>=3 && student.getGpa()>=4.0) {
				c3.andThen(c4).accept(student);
			}
		}));//consumer chaining
		
	}
	
	public static void main(String[] args) {

		Consumer<String> c1=(String s)-> System.out.println(s.toUpperCase());
		c1.accept("premendra kumar");
		
		printName();
		printNameAndActivities();
		printNameAndActivitiesUsingCondition();

	}

}
