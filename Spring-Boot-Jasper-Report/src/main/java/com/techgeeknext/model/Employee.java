package com.techgeeknext.model;

public class Employee {

	private int id;
	private String name;
	private String role;
	private String address;

	public Employee(int id, String name, String role, String address) {
		this.id = id;
		this.name = name;
		this.role = role;
		this.address = address;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
