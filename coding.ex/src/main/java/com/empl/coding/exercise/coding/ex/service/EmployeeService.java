package com.empl.coding.exercise.coding.ex.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.empl.coding.exercise.coding.ex.exception.InvalidInputSuppliedException;
import com.empl.coding.exercise.coding.ex.jpa.EmployeeRepository;
import com.empl.coding.exercise.coding.ex.pojo.Employee;

@Service
public class EmployeeService {
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Value("${coding.ex.contants.pageSize:5}")
	private int PageSize;

	public List<Employee> findPagedDataByPlace(int pageNumber, String place)
			throws InvalidInputSuppliedException {

		if (pageNumber < 0) {
			throw new InvalidInputSuppliedException("Invalid page number ( " + pageNumber + " ) supplied. ");
		}

		//int pageSize=5;

		Pageable pageable = PageRequest.of(pageNumber, PageSize);
		List<Employee> pagedData = employeeRepository.findPagedDataByPlace(pageable, place);

		return pagedData;
	}

	public Map<String, Integer> getRangeOfSalaryHavingCompetency(String competency) throws InvalidInputSuppliedException {
		
		if (competency==null || competency.trim().equals("")) {
			throw new InvalidInputSuppliedException("Invalid competency ( " + competency + " ) supplied. ");
		}
		return employeeRepository.getRangeOfSalaryHavingCompetency(competency);
	}

	public int incrementSalaryOfGivenPlaceEmployees(String place, int percentage) throws InvalidInputSuppliedException {
		if (place==null || place.trim().equals("")) {
			throw new InvalidInputSuppliedException("Invalid place ( " + place + " ) supplied. ");
		}
		if (percentage<=0) {
			throw new InvalidInputSuppliedException("Invalid percentage ( " + percentage + " ) supplied. ");
		}
		if (percentage>55) {
			throw new InvalidInputSuppliedException("Invalid percentage ( " + percentage + " ) supplied. ");
		}
		
		List<Employee> emps=employeeRepository.findByPlace(place);
		if(emps==null||emps.size()==0) {
			throw new InvalidInputSuppliedException("No employees in given place ( " + place + " ) supplied. ");
		}
//		emps.stream().map(Employee emp->{});
		for(Employee emp:emps) {
			emp.setSalary(emp.getSalary()*(100+percentage)/100);
		}
		List<Employee> saved= employeeRepository.saveAll(emps);
		return saved.size();
	}

}
