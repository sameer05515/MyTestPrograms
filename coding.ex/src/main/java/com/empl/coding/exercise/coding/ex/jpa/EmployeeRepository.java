package com.empl.coding.exercise.coding.ex.jpa;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.empl.coding.exercise.coding.ex.pojo.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	//@Query()
	List<Employee> findByPlace(String place);

	@Query(value = "SELECT * FROM t_employee WHERE UPPER(place)= :place", nativeQuery = true)
	List<Employee> findPagedDataByPlace(Pageable pageable, @Param("place") String place);

	@Query(value = "SELECT MIN(Salary) AS MINSALARY ,MAX(Salary) AS MAXSALARY FROM t_employee WHERE UPPER(Competencies)= :competency", nativeQuery = true)
	Map<String, Integer> getRangeOfSalaryHavingCompetency(String competency);
	
	

}
