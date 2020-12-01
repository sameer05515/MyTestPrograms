package com.empl.coding.exercise.coding.ex.pojo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

@Entity
@Table(name = "t_employee")
public class Employee {

	@Id
	@GeneratedValue
	int employeeID;
	String employeeName;
	String title;
	String businessUnit;
	String place;
	int supervisorID;
	String competencies;
	int salary;

	
	@Override
	public String toString() {
		JSONObject obJsonObject=new JSONObject();		
		obJsonObject.put("employeeID", employeeID);
		obJsonObject.put("employeeName", employeeName);
		obJsonObject.put("title", title);
		obJsonObject.put("businessUnit", businessUnit);
		obJsonObject.put("place", place);
		obJsonObject.put("supervisorID", supervisorID);
		obJsonObject.put("competencies", competencies);
		obJsonObject.put("salary", salary);
		
		return obJsonObject.toString();
	}


	public int getEmployeeID() {
		return employeeID;
	}


	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}


	public String getEmployeeName() {
		return employeeName;
	}


	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getBusinessUnit() {
		return businessUnit;
	}


	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}


	public String getPlace() {
		return place;
	}


	public void setPlace(String place) {
		this.place = place;
	}


	public int getSupervisorID() {
		return supervisorID;
	}


	public void setSupervisorID(int supervisorID) {
		this.supervisorID = supervisorID;
	}


	public String getCompetencies() {
		return competencies;
	}


	public void setCompetencies(String competencies) {
		this.competencies = competencies;
	}


	public int getSalary() {
		return salary;
	}


	public void setSalary(int salary) {
		this.salary = salary;
	}

}
