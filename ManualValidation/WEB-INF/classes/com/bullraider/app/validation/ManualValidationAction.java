package com.bullraider.app.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.ValidationAware;

public class ManualValidationAction extends ActionSupport implements Preparable {

	private String username;
	private String password;
	private String email;

	//maps to the Sex , radio buttons possible values.
	private Map<String,String> sexValues;
	// maps to the sex, radios submitted value.
	private String sex;
	private List<String> interestValues;
	private String interests;
	private String countryValues[];
	private String country;
	
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getInterests() {
		return interests;
	}
	public void setInterests(String interests) {
		this.interests = interests;
	}
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
	public void validate(){
		//Username can't be blank
		if(username.equals("")){
			addFieldError("username", "The Username can't be empty");
		}
		//password must not be blank , and it should be more than 6 characters
		if(password.equals("")){
			addFieldError("password","The Password can't be empty");
		}else if(password.length()<6){
			addFieldError("password","Password must be minimum of 6 characters");
		}
		// Email must not be blank , and it should be follow the pattern of email address
		if(email.equals("")){
			addFieldError("email","The Email can't be empty");
		}else{
			String  expression="^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
			   CharSequence inputStr = email;
			   Pattern pattern = Pattern.compile(expression,Pattern.CASE_INSENSITIVE);
			   Matcher matcher = pattern.matcher(inputStr);
			   if(!matcher.matches())
				   addFieldError("email","Invalid email address");
		}
	}
}
