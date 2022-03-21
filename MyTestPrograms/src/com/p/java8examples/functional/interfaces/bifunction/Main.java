package com.p.java8examples.functional.interfaces.bifunction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import com.p.java8examples.streams.Utility;

public class Main {
	public static void main(String[] args) {
		List<Employee> employees = Arrays.asList(new Employee("John", 20_000), new Employee("Max", 45_000),
				new Employee("Jane", 11_000));
		BiFunction<Employee, Double, Double> raiser = (e, increase) -> e.salary
				+ ((e.salary < 30_000) ? (e.salary * increase / 100) : 0);
		
		for (Employee candidate : employees) {
			candidate.salary=raiser.apply(candidate, 10D);
		}
		 
		System.out.println("==============================================");
		Utility.printStreams(employees.stream().toArray(Employee[]::new));
		System.out.println("==============================================");
		
		System.out.println(bimapIt(employees, 10D, raiser));
	}

	public static Map<String, Double> bimapIt(List<Employee> candidates, Double raise,
			BiFunction<Employee, Double, Double> mapper) {
		Map<String, Double> applied = new HashMap<>();

		for (Employee candidate : candidates) {
			applied.put(candidate.name, mapper.apply(candidate, raise));
		}
		return applied;
	}
}

class Employee {
	public String name;
	public double salary;

	public Employee(String name, double salary) {
		this.name = name;
		this.salary = salary;
	}
	
	@Override
	public String toString() {
		return "Employee [name=" + name + ", salary=" + salary + "]";
	}
}