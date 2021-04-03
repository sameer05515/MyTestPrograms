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
}
