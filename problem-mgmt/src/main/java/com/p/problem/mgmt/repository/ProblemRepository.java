package com.p.problem.mgmt.repository;

import com.p.problem.mgmt.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long> {}
