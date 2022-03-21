package com.p.resume.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

//import org.json.JSONObject;

@Entity
@Table(name = "t_user")
public class User {

	@Id
	@Column(name="id")
	@GeneratedValue
	int id;
	
	@Column(name="name")
	String name;
	
	@Column(name="address")
	String address;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
