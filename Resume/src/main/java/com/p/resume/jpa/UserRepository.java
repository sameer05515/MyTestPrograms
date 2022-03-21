package com.p.resume.jpa;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.p.resume.pojo.User;



public interface UserRepository extends JpaRepository<User, Integer> {

	//@Query()
	//List<User> findByPlace(String place);

	@Query(value = "SELECT * FROM t_user WHERE id= :id", nativeQuery = true)
	Map<String, Object> getUserDetails(@Param("id") int id);
	
	@Query(value = "SELECT id,mobile_no as mobileNumber  FROM t_user_mobile_no WHERE user_id= :user_id", nativeQuery = true)
	List<Map<String, Object>> getUserMobileDetails(@Param("user_id") int id);

//	@Query(value = "SELECT MIN(Salary) AS MINSALARY ,MAX(Salary) AS MAXSALARY FROM t_employee WHERE UPPER(Competencies)= :competency", nativeQuery = true)
//	Map<String, Integer> getRangeOfSalaryHavingCompetency(String competency);
//	
	

}
