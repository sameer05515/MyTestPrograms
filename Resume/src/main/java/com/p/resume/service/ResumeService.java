package com.p.resume.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.p.resume.exception.InvalidInputSuppliedException;
import com.p.resume.jpa.UserRepository;



@Service
public class ResumeService {
	@Autowired
	UserRepository employeeRepository;
	
	public Map<String, Object> getUserDetails(int id) throws InvalidInputSuppliedException{
		if(id<=0) {
			throw new InvalidInputSuppliedException("Invalid id provided : "+id);
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		Map<String,Object> user=employeeRepository.getUserDetails(id);
		ret.putAll(user);
		List<Map<String, Object>> retMobileNo=employeeRepository.getUserMobileDetails(id);
		ret.put("mobileNumbers", retMobileNo);
		return ret;
	}

	public String getDummyUserResume() throws InvalidInputSuppliedException{
		String name = "John";
		int age = 30;
		String address = "123 Main St, City";

		String multiLineString = """
				<!DOCTYPE html>
				<html lang="en">
				<head>
				    <meta charset="UTF-8">
				    <meta name="viewport" content="width=device-width, initial-scale=1.0">
				    <title>Resume</title>
				</head>
				<body>
				   \s
				</body>
				</html>
				                             """;

		String formattedString = String.format(multiLineString, name, age, address);
		return formattedString;
	}
}
