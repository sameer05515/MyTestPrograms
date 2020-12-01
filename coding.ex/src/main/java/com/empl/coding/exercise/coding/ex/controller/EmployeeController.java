package com.empl.coding.exercise.coding.ex.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empl.coding.exercise.coding.ex.pojo.Employee;

@RestController
public class EmployeeController {

	@GetMapping("/")
	public String healthCheck() {
		return "OK";
	}

	@PutMapping("/employee/place/{place}/salary/{percentage}")
	public int incrementSalaryOfGivenPlaceEmployees(@PathVariable("place") String place,
			@PathVariable("percentage") int percentage) {

		return 0;
	}

	@GetMapping("/employee/place/{place}")
	public List<Employee> getEmployeesHavingPlace(@PathVariable("place") String place) {
		List<Employee> res = new ArrayList<Employee>();

		return res;
	}

	@GetMapping("/employee/competency/{competency}/salary/range")
	public JSONObject getRangeOfSalaryHavingCompetency(@PathVariable("competency") String competency) {
		JSONObject res = new JSONObject();

		return res;
	}
}
