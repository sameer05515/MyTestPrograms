package com.p.xml.bo;

public class UserDetails {

	private String userName;
	/**
	 * @param id
	 * @param name
	 * @param address
	 * @param userName 
	 */
	public UserDetails(String id, String name, String address, String userName) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.userName = userName;
	}
	private String id;
	private String name;
	private String address;
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getAddress() {
		return address;
	}
	@Override
	public String toString() {
		return "UserDetails [id=" + id + ", name=" + name + ", address="
				+ address + "]";
	}
	public String getUserName() {
		return userName;
	}
	
	
}
