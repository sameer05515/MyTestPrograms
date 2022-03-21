package com.p.java8examples.streams;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Java code to print the elements of Stream 

class GFG {
	public static void main(String[] args) {
		
		List<String> list = new LinkedList<String>(); 
        list.add("Geeks"); 
        list.add("for"); 
        list.add("Geeks"); 
        list.add("Practice");   
        String[] arr = list.toArray(new String[0]);
		
		Utility.printStreams(arr);
		
		List<Employee> listEmployee = new LinkedList<Employee>(); 
		for(int i=0;i<10;i++) {
			listEmployee.add(new Employee(i+1, "Emp"+(i+1), (i%3)+2, "IT", "FCM", "COMP1",10000+(i%3)*5000));
		}
		
		
		System.out.println("=======================================");
		Utility.printStreams(Utility.toEmployeeArrayUsingStream(listEmployee));
		
		List<Employee> listEmployee1 = Stream.of(Utility.toEmployeeArrayUsingStream(listEmployee)).collect(Collectors.toList());
		System.out.println(listEmployee1);
		
	}
}

class Employee{
	private int id;
	private String name;
	private int supervisorId;
	private String department;
	private String businessUnit;
	private String competency;	
	private int salary;
	
	public Employee() {
		super();
	}
	
	public Employee(int id, String name, int supervisorId, String department, String businessUnit, String competency,int salary) {
		super();
		this.id = id;
		this.name = name;
		this.supervisorId = supervisorId;
		this.department = department;
		this.businessUnit = businessUnit;
		this.competency = competency;
		this.salary = salary;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSupervisorId() {
		return supervisorId;
	}
	public void setSupervisorId(int supervisorId) {
		this.supervisorId = supervisorId;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getBusinessUnit() {
		return businessUnit;
	}
	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}
	public String getCompetency() {
		return competency;
	}
	public void setCompetency(String competency) {
		this.competency = competency;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", supervisorId=" + supervisorId + ", department=" + department
				+ ", businessUnit=" + businessUnit + ", competency=" + competency + ", salary=" + salary + "]";
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	
}
