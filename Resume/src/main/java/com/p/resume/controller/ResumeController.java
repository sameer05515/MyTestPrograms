package com.p.resume.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.p.resume.exception.InvalidInputSuppliedException;
import com.p.resume.response.ResponseHandler;
import com.p.resume.service.ResumeService;

@RestController
public class ResumeController {
	
	@Autowired
	ResumeService resumeService;

	@GetMapping("/")
	public String healthCheck() {
		return "OK";
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<Object> getUserDetails(@PathVariable("id") int id) {
		ResponseEntity<Object> response=null;
		try {
			Map<String, Object> res=resumeService.getUserDetails(id);
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
