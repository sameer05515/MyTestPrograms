package com.p.problem.mgmt.repository;

import com.p.problem.mgmt.entity.ProblemDescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemDescriptionRepository extends JpaRepository<ProblemDescription, Long> {}
