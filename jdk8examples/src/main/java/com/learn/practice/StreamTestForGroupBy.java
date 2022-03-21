package com.learn.practice;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

public class StreamTestForGroupBy {

	public static void main(String[] args) {
		List<Employee> empList = Arrays.asList(new Employee("A", 25, "dept1"), new Employee("B", 26, "dept1"),
				new Employee("C", 27, "dept2"), new Employee("D", 40, "dept3"), new Employee("E", 27, "dept2"));

		/**
		 * print average age of employees, department wise
		 */
		printAverageAgeDeptWise(empList);

		/**
		 * Capitalize name, sort list based on age
		 */

		capitalizeNameAndSortByAge(empList);
	}

	private static void printAverageAgeDeptWise(List<Employee> empList) {
		Map<String, Double> map = empList.stream()
				.collect(Collectors.groupingBy(Employee::getDept, Collectors.averagingInt(Employee::getAge)));

//		Map<String, List<Employee>> map2 = empList.stream()
//				.collect(Collectors.groupingBy(Employee::getDept, Collectors.toList()));
		
		Map<String, List<Employee>> map2 = empList.stream()
				.collect(Collectors.groupingBy((e)->e.getDept(), Collectors.toList()));

		System.out.println(map);
		System.out.println(map2);
		// map2.forEach();

	}

	private static void capitalizeNameAndSortByAge(List<Employee> empList) {
		Function<Employee, String> upperCaseFunction = (employee) -> employee.getName().toUpperCase();

		for (Employee e : empList) {
			e.setName(upperCaseFunction.apply(e));
		}

		Collections.sort(empList, new Comparator<Employee>() {

			@Override
			public int compare(Employee o1, Employee o2) {
				if (o1 != null && o2 != null) {
					return o1.getAge() - o2.getAge();
				}
				return 0;
			}
		});
		empList.forEach(e -> System.out.println(e));

	}

}

class Employee {
	String name;
	int age;
	String dept;

	public Employee(String name, int age, String dept) {
		super();
		this.name = name;
		this.age = age;
		this.dept = dept;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + ", age=" + age + ", dept=" + dept + "]";
	}

}
