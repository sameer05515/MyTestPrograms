package com.empl.coding.exercise.coding.ex.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SmokeTest {
	
	@Autowired
	EmployeeController employeeController;
	
	@Test
	void contextLoads() {
		System.out.println("Checking whether EmployeeController injected correctly!");
		assertThat(employeeController).isNotNull();
	}
}
