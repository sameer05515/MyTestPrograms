package com.p.resume.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.p.resume.exception.InvalidInputSuppliedException;
import com.p.resume.response.ResponseHandler;
import com.p.resume.service.ResumeService;

public class ResumeController {
	
	@Autowired
	ResumeService employeeService;

	@GetMapping("/")
	public String healthCheck() {
		return "OK";
	}
	
	@PutMapping("/employee/place/{place}/salary/{percentage}")
	public ResponseEntity<Object> incrementSalaryOfGivenPlaceEmployees(@PathVariable("place") String place,
			@PathVariable("percentage") int percentage) {
		ResponseEntity<Object> response=null;
		try {
			int res=employeeService.incrementSalaryOfGivenPlaceEmployees(place,percentage);
			response = ResponseHandler.generateResponse(HttpStatus.OK, false, "Success", res);
		} catch (InvalidInputSuppliedException e) {
			response = ResponseHandler.generateResponse(HttpStatus.PRECONDITION_FAILED, true, "Fail",
					e.getCustomMessage());
			e.printStackTrace();
		}catch (Exception e) {
			response = ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "Fail",
					"Please contact administrator!");
			e.printStackTrace();
		}
		return response;
	}
}
