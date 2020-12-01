package com.empl.coding.exercise.coding.ex.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.empl.coding.exercise.coding.ex.pojo.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
