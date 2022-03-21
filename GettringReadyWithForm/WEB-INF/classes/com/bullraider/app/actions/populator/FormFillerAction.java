package com.bullraider.app.actions.populator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Preparable;

public class FormFillerAction implements Preparable{

	private String username;
	private String password;
	private String email;

	//maps to the Sex , radio buttons possible values.
	private Map<String,String> sexValues;
	// maps to the sex, radios submitted value.
	private String sex;

	public String getSex() {
		return sex;
	}



	public void setSex(String sex) {
		this.sex = sex;
	}
	private List<String> interestValues;
	private String interests;
	public String getInterests() {
		return interests;
	}



	public void setInterests(String interests) {
		this.interests = interests;
	}
	private String countryValues[];
	private String country;
	
	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}



	public String[] getCountryValues() {
		return countryValues;
	}


	public List< String> getInterestValues() {
		return interestValues;
	}


	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public Map<String, String> getSexValues() {
		return sexValues;
	}

	public String getUserRegistrationForm(){

		return "success";
	}
	public String registerUser(){
		return "success";
	}

	@Override
	public void prepare() throws Exception {
		sexValues=new HashMap<String,String>();
		sexValues.put("Male","Male");
		sexValues.put("Female","Female");

		interestValues= new ArrayList<String>();
		interestValues.add("Mobiles");
		interestValues.add("Game Consoles");
		interestValues.add("Laptops");
		interestValues.add("Portable Music Players");

		countryValues= new String[3];
		countryValues[0]="India";
		countryValues[1]="United States";
		countryValues[2]="China";
	}



}
