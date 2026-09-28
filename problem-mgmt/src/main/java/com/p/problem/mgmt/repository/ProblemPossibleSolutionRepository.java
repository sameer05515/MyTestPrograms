package com.p.problem.mgmt.repository;

import com.p.problem.mgmt.entity.ProblemPossibleSolution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemPossibleSolutionRepository
    extends JpaRepository<ProblemPossibleSolution, Long> {}
