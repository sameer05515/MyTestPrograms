package com.example.jsptut.service;

import com.example.jsptut.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    public List<Employee> getSampleEmployees() {
        return List.of(
                new Employee(1, "Premendra", "Manager"),
                new Employee(2, "Meghna", "Developer")
        );
    }
}
