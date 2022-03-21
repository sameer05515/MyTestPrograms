package com.learn.java.functionalInterface;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.learn.java.data.Student;
import com.learn.java.data.StudentDatabase;

public class PredicateAndConsumerExample {
	
	Predicate<Student> p1=(s) -> s.getGradeLevel()>=3;
	Predicate<Student> p2=(s) -> s.getGpa()>=3.9;
	
	BiPredicate<Integer, Double> biPredicate = (gradeLevel,gpa) -> gradeLevel>=3&&gpa>=3.9;
	
	BiConsumer<String, List<String>> studentBiConsumer = (name,activities) -> System.out.println(name +" : "+activities);
	
	Consumer<Student> studentConsumer= (student -> {
//		if(p1.and(p2).test(student)) {
//			studentBiConsumer.accept(student.getName(), student.getActivities());
//		}
		
		if(biPredicate.test(student.getGradeLevel(),student.getGpa())) {
			studentBiConsumer.accept(student.getName(), student.getActivities());
		}
	});

	public static void main(String[] args) {
		
		List<Student> studentList = StudentDatabase.getAllDatabase();
		new PredicateAndConsumerExample().printNameAndActivities(studentList);
	}

	private void printNameAndActivities(List<Student> studentList) {
		studentList.forEach(studentConsumer);
	}

}
