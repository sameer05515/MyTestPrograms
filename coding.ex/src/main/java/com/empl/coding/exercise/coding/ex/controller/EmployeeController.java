package com.empl.coding.exercise.coding.ex.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.empl.coding.exercise.coding.ex.exception.InvalidInputSuppliedException;
import com.empl.coding.exercise.coding.ex.pojo.Employee;
import com.empl.coding.exercise.coding.ex.response.ResponseHandler;
import com.empl.coding.exercise.coding.ex.service.EmployeeService;

@RestController
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;

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

	@GetMapping("/employee/place/{place}")
	public ResponseEntity<Object> getEmployeesHavingPlace(@RequestParam(value = "pageNo", defaultValue="0") int pageNo,@PathVariable("place") String place) {
		
		ResponseEntity<Object> response=null;
		try {
			response = null;
			List<Employee> res = new ArrayList<Employee>();
			res=employeeService.findPagedDataByPlace(pageNo,place);			
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

	@GetMapping("/employee/competency/{competency}/salary/range")
	public ResponseEntity<Object> getRangeOfSalaryHavingCompetency(@PathVariable("competency") String competency) {
//		Map<String,Integer> res = new HashMap<>();
//		res.put("MINSALARY", 100);
//		res.put("MAXSALARY", 500);
		ResponseEntity<Object> response=null;
		try {
			Map<String,Integer> res = employeeService.getRangeOfSalaryHavingCompetency(competency);
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
