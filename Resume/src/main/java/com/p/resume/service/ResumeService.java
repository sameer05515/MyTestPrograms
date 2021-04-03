package com.p.resume.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.p.resume.jpa.EmployeeRepository;



@Service
public class ResumeService {
	@Autowired
	EmployeeRepository employeeRepository;
}
