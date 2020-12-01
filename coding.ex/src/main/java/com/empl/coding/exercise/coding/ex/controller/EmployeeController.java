package com.empl.coding.exercise.coding.ex.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {
	
	@GetMapping("/")
	public String healthCheck() {
		return "OK";
	}
	
	@PutMapping("/employee/place/{place}/salary/{percentage}")
	public List<Empl>
	

}
